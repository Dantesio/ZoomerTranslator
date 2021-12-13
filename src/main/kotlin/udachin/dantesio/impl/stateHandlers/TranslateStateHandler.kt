package udachin.dantesio.impl.stateHandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.bot.TelegramBot

val TRANSLATE_STATE: State = State("TRANSLATE_STATE")

class TranslateStateHandler(override val state: State = TRANSLATE_STATE) : StateHandler {
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
        message.text = "Введите текст или слово на русском языке\n" +
                "ПРИМЕЧАНИЕ: К сожалению, бот не поддерживает знаки препинания и латиницу. Следите за раскладкой клавиатуры, " +
                "а также за знаками препинания, иначе бот выведет ответ с ошибками."
        bot.execute(message)

        chat.lastMessageId = -1

        chat.oldState = state
    }
}