package indigo.player

import indigo.Card

/**
 * Player base class
 *
 * @property name name used in the game to design it
 * @property hand Card that can be played by the player
 * @property _cardsWon number of cards won by the player
 * @property _score Player's score
 * @property cardsWon public immutable value of _cardWon
 * @property score public immutable value of _score
 */
abstract class Player {
    abstract val name: String
    protected val hand: MutableList<Card> = mutableListOf()
    private var _cardsWon = 0
    private var _score = 0
    val cardsWon
        get() = _cardsWon
    val score
        get() = _score

    /**
     * Action to play.
     */
    abstract fun play(): Card

    /**
     * Add card to the hand
     */
    fun getCards(cards: List<Card>) {
        hand.addAll(cards)
    }

    /**
     * Check if the hand is empty
     */
    fun hasEmptyHand() = hand.isEmpty()

    /**
     * Increment the CardWon and the score
     *
     * @param cards Number of card to add to _cardWon
     * @param points Point to add to the score
     */
    fun winCard(cards: Int, points: Int) {
        _cardsWon += cards
        _score += points
    }
}