package udachin.dantesio.impl.stateHandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.bot.TelegramBot

val FINISH_INCORRECT_TRAIN_STATE: State = State("FINISH_INCORRECT_TRAIN_STATE")

class FinishIncorrectTrainStateHandler(override val state: State = FINISH_INCORRECT_TRAIN_STATE) : StateHandler {
    override fun bump(bot: TelegramBot, chat: Chat, state: State, update: Update) {
        // Удаляет предыдущее сообщение
        if (chat.lastMessageId != -1) {
            val deleteMessage = DeleteMessage()
            deleteMessage.chatId = chat.id.toString()
            deleteMessage.messageId = chat.lastMessageId
            bot.execute(deleteMessage)
        }

        val message = SendMessage()
        message.chatId = chat.id.toString()
        message.text = "К сожалению, это неправильный ответ :(\n" +
                "Возможно, вам повезёт в следующий раз...\n"
        bot.execute(message)

        chat.lastMessageId = -1

        chat.oldState = state

        TelegramApi.chatStorage.get(chat.id).changeState(START_STATE, update)
    }
}