package udachin.dantesio.api.telegram

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateManager {
    fun connect(handler: UpdateHandler)
    fun disconnect(handler: UpdateHandler)
    fun handle(update: Update)
}