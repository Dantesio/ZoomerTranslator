package udachin.dantesio.impl.stateHandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.bot.TelegramBot

val ABOUT_STATE: State = State("ABOUT_STATE")

class AboutStateHandler(override val state: State = ABOUT_STATE) : StateHandler {
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
        message.text = "Данный бот-переводчик создан в рамках итогового проекта 3 курса НИУ ВШЭ в 2021-2022 учебном году" +
                "в рамках дисциплины \"Современная поэзия и новые медиа\".\n" +
                "Создатели:\n" +
                "- Удачин Даниил\n" +
                "- Артём Присяжнюк\n" +
                "- Александра Тополянская"
        bot.execute(message)

        chat.lastMessageId = -1

        chat.oldState = state

        TelegramApi.chatStorage.get(chat.id).changeState(START_STATE, update)
    }
}