package indigo.player

import indigo.Card
import indigo.Table

/**
 * Strategy followed by a ComputerPLayer
 *
 * @property table Game board, since it needs to have access to the state of game to plan accordingly/
 */
class ComputerStrategy(private val table: Table) {

    /**
     * Execute the strategy to choose a card
     *
     * @param hand current hand of the player
     * @return Card to be played
     */
    fun execute(hand: List<Card>): Card {
        return applyStrategy(hand)
    }


    /**
     * Choose a card according the following rules:
     * - if the player has only one card, they plays it.
     * - if the player has only one card matching the top card, they play it.
     * - if there is no card on the table or none of the player's card match the top card,
     * they use the empty sub-strategy
     * - else (they have multiply cards matching the top card), they use the candidate sub-strategy
     * @see emptyStrategy
     * @see candidateStrategy
     *
     * @param hand Player's current hand
     * @return Card to be played
     */
    private fun applyStrategy(hand: List<Card>): Card {
        val onTable = table.stackSize
        val topCard = if (onTable > 0) table.topCard else Card("", "")
        val candidates = listOf(
            hand.filter { card -> card.suit == topCard.suit},
            hand.filter { card -> card.rank == topCard.rank}
        )
        val flatCandidates = candidates.flatten()

        return when {
            hand.size == 1 -> hand.last()
            flatCandidates.size == 1 -> flatCandidates.last()
            onTable == 0 || flatCandidates.isEmpty()
                -> emptyStrategy(hand)
            else -> candidateStrategy(candidates)
        }
    }

    /**
     * Choose a card according the following rules:
     * - if the player has multiple cards of the same suit, they play a random card from that suit
     * - if the player has multiple cards of the same ranks, they play a random card from that rank
     * - else play a random card
     *
     * @param hand Player's current hand
     * @return Card to be played
     */
    private fun emptyStrategy(hand: List<Card>): Card {
        val suits = hand.groupBy { it.suit }.filter { (_, v) -> v.size > 1 }
        val ranks = hand.groupBy { it.rank }.filter { (_, v) -> v.size > 1 }

        return when {
            suits.isNotEmpty()  -> suits.flatMap { (_, v) -> v }.shuffled().first()
            ranks.isNotEmpty()  -> ranks.flatMap { (_, v) -> v }.shuffled().first()
            else                -> hand.shuffled().first()
        }
    }

    /**
     * Choose a card according the following rules:
     * - if the player has multiple cards with the suit of top card, they play a random card from
     * that suit
     * - if the player has multiple cards with the suit of top card,, they play a random card from
     * that rank
     * - else play a random candidate
     *
     * @param candidates Cards in the player's hand matching the top card
     * @return Card to be played
     */
    private fun candidateStrategy(candidates: List<List<Card>>): Card {
        val suits = candidates[0]
        val ranks = candidates[1]

        return when {
            suits.size > 1  -> suits.shuffled().first()
            ranks.size > 1  -> ranks.shuffled().first()
            else            -> candidates.flatten().shuffled().first()
        }
    }
}