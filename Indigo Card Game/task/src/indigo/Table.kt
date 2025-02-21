package indigo

import indigo.player.ComputerPlayer
import indigo.player.HumanPlayer
import indigo.player.Player

/**
 * Game board that manage the state of the game
 *
 * @param playerFirst indicate if the human player play first
 * @property human Human player
 * @property computer AI player
 * @property deck Deck of card
 * @property first first player to play
 * @property lastWinner last player to have won cards
 * @property current Current player
 * @property onTable Cards on the game board
 * @property topCard Card on top of the stack on the board
 * @property stackSize Number of cards on the board
 */
class Table(
    private val human: HumanPlayer,
    private val computer: ComputerPlayer,
    playerFirst: Boolean,
    private val deck: Deck
) {
    private val first: Player
    private var lastWinner: Player? = null
    private var current: Player
    private val onTable = deck.get(4).toMutableList()
    val topCard
        get() = onTable.last()
    val stackSize: Int
        get() = onTable.size

    init {
        println("Initial cards on the table: %s".format(onTable.joinToString(" ")))
        current = if (playerFirst) human else computer
        first = current
        dealCards(human)
        dealCards(computer)
        computer.initStrategy(this)
    }

    /**
     * One game turn
     * - display the card of the board
     * - the current player plays
     * - if their card matches either the suit or the rank of the top card, they won every card
     * on the table
     * - if the player's hand is empty, they receive new cards
     * - end the turn by switching the current player
     *
     * @return Should the game continue
     */
    fun playTurn(): Boolean {
        if (onTable.isNotEmpty()) {
            println(
                "\n%d cards on the table, and the top card is %s".format(
                    stackSize, topCard.toString()
                )
            )
        } else {
            println("\nNo cards on the table")
        }

        if (human.hasEmptyHand() && computer.hasEmptyHand() && !deck.isNotEmpty()) {
            return gameOver()
        }

        val card = current.play()
        if (card.rank.isEmpty()) {
            println("Game Over")
            return false
        }

        when (onTable.isNotEmpty() && (card.rank == topCard.rank || card.suit == topCard.suit)) {
            true -> {
                winCard(card, current)
                println("%s wins cards".format(current.name))
                printScore()
            }
            false -> onTable.add(card)
        }

        if (current.hasEmptyHand() && deck.isNotEmpty()) { dealCards(current) }
        switchPlayer()
        return true
    }

    /**
     * Give 6 cards to a player
     *
     * @param player PLayer to give cards to
     */
    private fun dealCards(player: Player) {
        player.getCards(deck.get(6))
    }

    /**
     * Switch the current player between the human and the AI one
     */
    private fun switchPlayer() {
        current = when (current) {
            human -> computer
            computer -> human
            else -> throw Exception("Wrong player")
        }
    }

    /**
     * Calculate the score increment for a player.
     *
     * For each figures or 10 in the cards won, the score is incremented by one.
     *
     * @param card Card played
     * @param player PLayer to increment the score
     */
    private fun winCard(card: Card, player: Player) {
        onTable.run {
            add(card)
            val score = count { listOf("A", "K", "Q", "J", "10").contains(it.rank) }
            player.winCard(size, score)
            lastWinner = player
            clear()
        }
    }

    /**
     * Display the score and card won for both players
     */
    private fun printScore() {
        println("Score: Player %d - Computer %d".format(human.score, computer.score))
        println("Cards: Player %d - Computer %d".format(human.cardsWon, computer.cardsWon))
    }

    /**
     * Set the final score.
     * - the last cards are won by the last player to have won cards (or the player to have played,
     * if none won a card during the game)
     * - give 3 additional points to the player with the most card
     * - display the final score
     */
    private fun gameOver(): Boolean {
        val player = lastWinner ?: first

        onTable.run {
            val score = count { listOf("A", "K", "Q", "J", "10").contains(it.rank) }
            player.winCard(size, score)
        }
        when (human.cardsWon > computer.cardsWon) {
            true -> human.winCard(0,3)
            false -> computer.winCard(0, 3)
        }
        printScore()
        println("Game Over")
        return false
    }
}