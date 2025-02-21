package indigo.player

import indigo.Card

/**
 * Human player playing manually
 *
 * @property name name used in the game to design it
 * @see Player
 */
class HumanPlayer: Player() {
    override val name = "Player"

    /**
     * Action to play.
     *
     * The card is chosen by a console prompt before to be removed from the hand and played.
     *
     * @return Played card
     */
    override fun play(): Card {
        displayMessage()
        val card = chooseCard()
        hand.remove(card)
        return card
    }

    /**
     * Display a message helping the player to choose their next card
     */
    private fun displayMessage() {
        val message = StringBuilder("Cards in hand: ")
        hand.forEachIndexed {i, card ->
            message.append("%d)%s ".format(i + 1, card))
        }
        println(message)
    }

    /**
     * Player is recursively asked to choose a card in their hand. Alternatively, they can choose
     * to quit the game
     *
     * *@return Card to be played
     */
    private fun chooseCard(): Card {
        println("Choose a card to play (1-${hand.size}):")
        val input = readln()
        if (input.lowercase() == "exit") return Card("", "")

        val answer = input.toIntOrNull() ?: -1

        if (answer < 1 || answer > hand.size) {
            return chooseCard()
        }

        return hand[answer -1]
    }
}