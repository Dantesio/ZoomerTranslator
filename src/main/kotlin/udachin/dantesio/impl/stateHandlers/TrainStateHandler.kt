package udachin.dantesio.impl.stateHandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.helper.InlineButtonData
import udachin.dantesio.helper.Util
import udachin.dantesio.helper.buildInlineKeyboard
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.Collections.shuffle
import java.util.stream.Collectors

val TRAIN_STATE: State = State("TRAIN_STATE")

class TrainStateHandler(override val state: State = TRAIN_STATE) : StateHandler {
    override fun bump(bot: TelegramBot, chat: Chat, state: State, update: Update) {

        // Удаляет предыдущее сообщение
        if (chat.lastMessageId != -1) {
            val deleteMessage = DeleteMessage()
            deleteMessage.chatId = chat.id.toString()
            deleteMessage.messageId = chat.lastMessageId
            bot.execute(deleteMessage)
        }

        val input = "\"train\""
        val processBuilder = ProcessBuilder("python", "ZoomerTranslator.py", input)
        processBuilder.redirectErrorStream(true)

        val process = processBuilder.start()

        val text = BufferedReader(
            InputStreamReader(process.inputStream, StandardCharsets.UTF_8)
        )
            .lines()
            .collect(Collectors.joining("\n"))

//        val text = "example|answer|fake1|fake2|fake3|fake4"

        val output = text.split('|')
        val word = output[0]
        val answer = output[1]
        val answers = output.subList(1, output.lastIndex + 1)
        shuffle(answers)

        val inlineButtonData = mutableListOf<InlineButtonData>()
        for (definition in answers) {
            if (definition != answer)
                inlineButtonData.add(InlineButtonData(definition, "incorrect_answer"))
            else
                inlineButtonData.add(InlineButtonData(definition, "correct_answer"))
        }

        val inlineKeyboardMarkup = buildInlineKeyboard(inlineButtonData)

        val message = SendMessage()
        message.chatId = chat.id.toString()
        message.text = "Вам досталось слово $word! Угадайте, какой вариант из приведённых ниже - правильный.\n"
        message.replyMarkup = inlineKeyboardMarkup
        val sentMessage = bot.execute(message)

        chat.lastMessageId = sentMessage.messageId

        chat.oldState = state
    }
}