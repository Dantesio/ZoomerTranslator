package udachin.dantesio.api.model

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.telegram.TelegramApi

abstract class Chat {
    var state: State = State("START")
    lateinit var oldState: State
    open val id: Long = -1
    lateinit var roomId: String
    lateinit var deviceId: String
    var lastMessageId = -1

    fun changeState(newState: State, update: Update) {
        this.state = newState
        TelegramApi.stateManager.update(this, newState, update)
    }
}