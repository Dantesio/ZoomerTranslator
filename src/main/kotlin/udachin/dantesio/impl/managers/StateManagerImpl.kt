package udachin.dantesio.impl.managers

import org.telegram.telegrambots.meta.api.objects.Update
import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.model.State
import udachin.dantesio.api.telegram.StateHandler
import udachin.dantesio.api.telegram.StateManager
import udachin.dantesio.bot.TelegramBot
import java.util.concurrent.ConcurrentHashMap

class StateManagerImpl(private val telegramBot: TelegramBot) : StateManager {

    private val stateHandlers = ConcurrentHashMap<State, MutableList<StateHandler>>();

    override fun update(chat: Chat, state: State, update: Update) {
        stateHandlers[state]?.forEach { it.bump(telegramBot, chat, state, update) }
    }

    override fun connect(stateHandler: StateHandler) {
        val state = stateHandler.state
        if (!stateHandlers.contains(state)) {
            stateHandlers[state] = ArrayList()
        }

        stateHandlers[state]?.add(stateHandler)
    }

    override fun disconnect(stateHandler: StateHandler) {
        stateHandlers[stateHandler.state]?.remove(stateHandler)
    }
}