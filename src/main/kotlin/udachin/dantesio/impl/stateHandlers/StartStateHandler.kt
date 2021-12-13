package udachin.dantesio.impl.stateHandlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.helper.InlineButtonData
import udachin.dantesio.helper.buildInlineKeyboard

val START_STATE: State = State("START_STATE")

class StartStateHandler(override val state: State = START_STATE) : StateHandler {
    override fun bump(bot: TelegramBot, chat: Chat, state: State, update: Update) {

        // Билдит кнопки
        val inlineButtonData = mutableListOf<InlineButtonData>()
        inlineButtonData.add(InlineButtonData("Перевод текста", "translate"))
        inlineButtonData.add(InlineButtonData("Случайная тренировка", "train"))
        inlineButtonData.add(InlineButtonData("Про нас", "about"))

        val inlineKeyboardMarkup = buildInlineKeyboard(inlineButtonData)

        val message = SendMessage()
        message.chatId = chat.id.toString()
        message.text = "Добро пожаловать в бот-переводчик на \"зумерский\" язык!\n" +
                "Этот бот создан специально для того, чтобы в игровой форме обучить вас словам, " +
                "на котором (в большинстве своём) говорит поколение, родившееся примерно в начале 2000-х годов.\n\n" +
                "На данный момент доступно две функции:\n" +
                "1. Перевод текста\n" +
                "2. Тренировка, где появляется случайное слово с 5 определениями, только 1 из которых верно."
        message.replyMarkup = inlineKeyboardMarkup
        val sentMessage = bot.execute(message)

        chat.lastMessageId = sentMessage.messageId

        chat.oldState = state
    }
}