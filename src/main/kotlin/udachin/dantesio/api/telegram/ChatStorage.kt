package udachin.dantesio.api.telegram

import udachin.dantesio.api.model.Chat

interface ChatStorage {
    fun get(chatId: Long): Chat
}