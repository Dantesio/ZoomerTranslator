package udachin.dantesio.api.telegram

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.api.model.State

interface StateHandler {
    val state: State

    fun bump(bot: TelegramBot, chat: udachin.dantesio.api.model.Chat, state: State, update: Update)
}