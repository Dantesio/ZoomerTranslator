package udachin.dantesio.impl.updateHandlers

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.api.telegram.UpdateHandler
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.impl.stateHandlers.FINISH_CORRECT_TRAIN_STATE
import udachin.dantesio.impl.stateHandlers.FINISH_INCORRECT_TRAIN_STATE

class TrainUpdateHandler : UpdateHandler {
    override fun apply(update: Update, bot: TelegramBot) {
        if (update.hasCallbackQuery() && update.callbackQuery.data == "correct_answer") {
            TelegramApi.chatStorage.get(update.callbackQuery.message.chatId).changeState(FINISH_CORRECT_TRAIN_STATE, update)
        } else if (update.hasCallbackQuery() && update.callbackQuery.data == "incorrect_answer") {
            TelegramApi.chatStorage.get(update.callbackQuery.message.chatId).changeState(FINISH_INCORRECT_TRAIN_STATE, update)
        }
    }
}

