package udachin.dantesio.helper

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

data class InlineButtonData(val title: String, val callbackData: String)

fun buildInlineKeyboard(data: List<InlineButtonData>) : InlineKeyboardMarkup {
    val inlineKeyboardMarkup = InlineKeyboardMarkup()

    val rowList: ArrayList<MutableList<InlineKeyboardButton>> = ArrayList()

    data.forEach {
        val row = ArrayList<InlineKeyboardButton>()

        val inlineKeyboardButton = InlineKeyboardButton()
        inlineKeyboardButton.text = it.title
        inlineKeyboardButton.callbackData = it.callbackData

        row.add(inlineKeyboardButton)
        rowList.add(row)
    }

    inlineKeyboardMarkup.keyboard = rowList

    return inlineKeyboardMarkup

}
