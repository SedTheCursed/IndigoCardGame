package indigo

import indigo.player.ComputerPlayer
import indigo.player.HumanPlayer

fun main() {
    println("Indigo Card Game")
    val deck = Deck()
    val table = Table(
        HumanPlayer(),
        ComputerPlayer(),
        chooseFirstPlayer(),
        deck
    )
    var keepPlaying: Boolean

    do {
        keepPlaying = table.playTurn()
    } while (keepPlaying)
}

/**
 * Ask the human player if they want to play first
 */
fun chooseFirstPlayer(): Boolean {
    println("Play first?")
    return when (readln().lowercase()) {
        "yes" -> true
        "no" -> false
        else -> chooseFirstPlayer()
    }
}
