package udachin.dantesio.api.telegram

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.State

interface StateManager {
    fun update(chat: udachin.dantesio.api.model.Chat, state: State, update: Update)

    fun connect(stateHandler: StateHandler)
    fun disconnect(stateHandler: StateHandler)
}