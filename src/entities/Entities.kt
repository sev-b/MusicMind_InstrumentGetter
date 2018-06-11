package entities

class Genres {
    companion object {
        private val genres: List<String> = listOf(
                "jazz",
                "rock",
                "metal",
                "edm",
                "blues",
                "classical")

        fun isValid(genre: String): Boolean {
            return genres!!.contains(genre.toLowerCase())
        }

        fun validGenres() = genres
    }
}