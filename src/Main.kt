import entities.Genres
import workers.InstrumentGetter

fun main(args: Array<String>){
    if (args.size != 1) {
        System.err.println("Parameters: [genre]")
        return
    }

    if (!Genres.isValid(args[0])) {
        System.err.println("Valid genres: ")
        for (s in Genres.validGenres()) {
            println(s)
        }
        return
    }

    InstrumentGetter.start(args[0])
}