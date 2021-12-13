package udachin.dantesio.impl.updateHandlers

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.api.telegram.UpdateHandler
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.impl.stateHandlers.FINISH_TRANSLATE_STATE

class TranslateUpdateHandler : UpdateHandler {
    override fun apply(update: Update, bot: TelegramBot) {
        if (update.hasMessage() && TelegramApi.chatStorage.get(update.message.chatId).state.name == "TRANSLATE_STATE") {
            TelegramApi.chatStorage.get(update.message.chatId).changeState(FINISH_TRANSLATE_STATE, update)
        }
    }
}