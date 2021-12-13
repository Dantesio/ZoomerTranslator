package udachin.dantesio.helper

import kotlin.random.Random

class Util {
    fun <T> shuffle(list: MutableList<T>) {
        // start from the beginning of the list
        for (i in 0 until list.size - 1) {
            // get a random index `j` such that `i <= j <= n`
            val j = i + Random.nextInt(list.size - i)

            // swap element at i'th position in the list with the element at j'th position
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }
}