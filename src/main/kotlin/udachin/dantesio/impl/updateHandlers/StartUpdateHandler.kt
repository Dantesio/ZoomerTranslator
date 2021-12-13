package udachin.dantesio.impl.updateHandlers

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.api.telegram.UpdateHandler
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.impl.stateHandlers.ABOUT_STATE
import udachin.dantesio.impl.stateHandlers.START_STATE
import udachin.dantesio.impl.stateHandlers.TRAIN_STATE
import udachin.dantesio.impl.stateHandlers.TRANSLATE_STATE

class StartUpdateHandler : UpdateHandler {
    override fun apply(update: Update, bot: TelegramBot) {
        if (update.hasMessage() && update.message.hasText() && update.message.text == "/start") {
            TelegramApi.chatStorage.get(update.message.chatId).changeState(START_STATE, update)
        } else if (update.hasCallbackQuery() && update.callbackQuery.data == "translate") {
            TelegramApi.chatStorage.get(update.callbackQuery.message.chatId).changeState(TRANSLATE_STATE, update)
        } else if (update.hasCallbackQuery() && update.callbackQuery.data == "train") {
            TelegramApi.chatStorage.get(update.callbackQuery.message.chatId).changeState(TRAIN_STATE, update)
        } else if (update.hasCallbackQuery() && update.callbackQuery.data == "about") {
            TelegramApi.chatStorage.get(update.callbackQuery.message.chatId).changeState(ABOUT_STATE, update)
        }
    }
}