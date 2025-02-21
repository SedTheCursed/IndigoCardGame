package indigo

/**
 * Represent a deck of cards
 *
 * @property cards Card of the deck
 * @property ranks Ranks of card (from Ace to King) (Class property)
 * @property suits Suits of card (Class property)
 */
class Deck {
    private lateinit var cards: List<Card>

    init {
        reset()
    }

    /**
     * Create a set of 52 cards and shuffle it
     */
    fun reset() {
        cards = suits
            .flatMap { suit -> ranks.map { rank -> Card(suit, rank) } }
            .shuffled()
    }

    /**
     * Splît the deck between cards to get and the remaining ones
     *
     * @param cardsToGet Number of cards to be removed
     * @return The cards removed
     */
    fun get(cardsToGet: Int): List<Card> {
        if (cardsToGet > cards.size) {
            println("The remaining cards are insufficient to meet the request.")
            return emptyList()
        }

        val gotten = cards.subList(0, cardsToGet)
        cards = cards.run { subList(cardsToGet, size) }
        return gotten
    }

    /**
     * Check if the deck is not empty
     */
    fun isNotEmpty() = cards.isNotEmpty()

    override fun toString(): String  = cards.joinToString(" ")


    companion object {
        val ranks = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val suits = arrayOf("♦", "♥", "♠", "♣")
    }
}