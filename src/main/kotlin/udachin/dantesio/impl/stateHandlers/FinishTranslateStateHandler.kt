package udachin.dantesio.impl.stateHandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.bot.TelegramBot
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

val FINISH_TRANSLATE_STATE: State = State("FINISH_TRANSLATE_STATE")

class FinishTranslateStateHandler(override val state: State = FINISH_TRANSLATE_STATE) : StateHandler {
    override fun bump(bot: TelegramBot, chat: Chat, state: State, update: Update) {
        if (!update.hasMessage() || !update.message.hasText()) {
            val wrongMessage = SendMessage()
            wrongMessage.chatId = chat.id.toString()
            wrongMessage.text = "Пожалуйста, отправьте корректное сообщение после повторного нажатия нужной кнопки сверху"
            bot.execute(wrongMessage)
            return
        }

        val input = "translate " + update.message.text
        val processBuilder = ProcessBuilder("python", "ZoomerTranslator.py", input)
        processBuilder.redirectErrorStream(true)

        val process = processBuilder.start()

        val text = BufferedReader(
            InputStreamReader(process.inputStream, StandardCharsets.UTF_8)
        )
            .lines()
            .collect(Collectors.joining("\n"))

//        val text = "text"

        val message = SendMessage()
        message.chatId = chat.id.toString()
        message.text = "Перевод данного слова: $text"
        bot.execute(message)

        chat.lastMessageId = -1

        chat.oldState = state

        TelegramApi.chatStorage.get(chat.id).changeState(START_STATE, update)
    }
}