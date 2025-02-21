package indigo

data class Card(val suit: String, val rank: String) {
    override fun toString() = "$rank$suit"
}