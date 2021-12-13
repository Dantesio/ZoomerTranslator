package udachin.dantesio.bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.telegram.TelegramApi


class TelegramBot : TelegramLongPollingBot() {
    override fun getBotToken() = "5048266852:AAFEPqIM3PojdR-DK1YWs5cuZp2jH8OiBT8"
    override fun getBotUsername() = "zoomer_translate_bot"

    override fun onUpdateReceived(update: Update) {
        TelegramApi.updateManager.handle(update)
    }
}