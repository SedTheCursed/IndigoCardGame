package indigo.player

import indigo.Card
import indigo.Table

/**
 * AI player following a given strategy
 *
 * @property name name used in the game to design it
 * @property strategy Strategy that will follow the player
 * @see Player
 */
class ComputerPlayer: Player() {
    override val name = "Computer"
    private var strategy: ComputerStrategy? = null


    /**
     * Action to play.
     *
     * The card is chosen according to the strategy before to be removed from the hand and played.
     *
     * @return Played card
     */
    override fun play(): Card  {
        val card = strategy?.execute(hand) ?: hand.first()
        println("Computer plays %s".format(card.toString()))
        hand.remove(card)
        return card
    }


    /**
     * Initialise the strategy
     *
     * @param table Game board
     */
    fun initStrategy(table: Table) {
        strategy = ComputerStrategy(table)
    }
}