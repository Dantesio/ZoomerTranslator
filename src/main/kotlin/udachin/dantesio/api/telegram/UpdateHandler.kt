package udachin.dantesio.api.telegram

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.bot.TelegramBot

interface UpdateHandler {
    fun apply(update: Update, bot: TelegramBot)
}