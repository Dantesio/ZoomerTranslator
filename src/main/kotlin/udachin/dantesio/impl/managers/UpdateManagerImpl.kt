package udachin.dantesio.impl.managers

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.telegram.UpdateHandler
import udachin.dantesio.api.telegram.UpdateManager
import udachin.dantesio.bot.TelegramBot

class UpdateManagerImpl(private val telegramBot: TelegramBot) : UpdateManager {
    private val updateHandlers = ArrayList<UpdateHandler>()

    override fun connect(handler: UpdateHandler) {
        updateHandlers.add(handler)
    }

    override fun disconnect(handler: UpdateHandler) {
        updateHandlers.remove(handler)
    }

    override fun handle(update: Update) {
        updateHandlers.forEach { it.apply(update, telegramBot) }
    }
}