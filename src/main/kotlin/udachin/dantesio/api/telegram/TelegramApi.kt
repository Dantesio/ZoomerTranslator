package udachin.dantesio.api.telegram

class TelegramApi {
    companion object {
        lateinit var stateManager: StateManager
        lateinit var updateManager: UpdateManager
        lateinit var chatStorage: ChatStorage
    }
}