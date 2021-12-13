package udachin.dantesio.impl.storage

import udachin.dantesio.api.model.Chat
import udachin.dantesio.api.telegram.ChatStorage

class ChatStorageImpl : ChatStorage {
    private val map: MutableMap<Long, Chat> = HashMap()

    override fun get(chatId: Long): Chat {
        if (!map.containsKey(chatId)) {
            map[chatId] = SimpleChat(chatId)
        }

        return map[chatId]!!
    }
}