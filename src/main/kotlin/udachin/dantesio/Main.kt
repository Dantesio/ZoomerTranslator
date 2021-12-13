package udachin.dantesio

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import udachin.dantesio.api.telegram.TelegramApi
import udachin.dantesio.bot.TelegramBot
import udachin.dantesio.impl.managers.StateManagerImpl
import udachin.dantesio.impl.managers.UpdateManagerImpl
import udachin.dantesio.impl.stateHandlers.*
import udachin.dantesio.impl.storage.ChatStorageImpl
import udachin.dantesio.impl.updateHandlers.StartUpdateHandler
import udachin.dantesio.impl.updateHandlers.TrainUpdateHandler
import udachin.dantesio.impl.updateHandlers.TranslateUpdateHandler

fun main(args: Array<String>) {
    try {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        val bot = TelegramBot()

        TelegramApi.updateManager = UpdateManagerImpl(bot).apply {
            // Connecting update handlers
            connect(StartUpdateHandler())
            connect(TranslateUpdateHandler())
            connect(TrainUpdateHandler())
        }

        TelegramApi.stateManager = StateManagerImpl(bot).apply {
            // Connecting state handlers
            connect(StartStateHandler())
            connect(TranslateStateHandler())
            connect(FinishTranslateStateHandler())
            connect(TrainStateHandler())
            connect(FinishCorrectTrainStateHandler())
            connect(FinishIncorrectTrainStateHandler())
            connect(AboutStateHandler())
        }

        TelegramApi.chatStorage = ChatStorageImpl()

        botsApi.registerBot(bot)
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}
