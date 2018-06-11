package workers

import java.io.File

/**
 * generates a csv-file containing all instruments that are used in the given midifiles (default=/mnt/network_training/midicsv)
 */
class InstrumentGetter {
    companion object {

        val instrumentPath = "/mnt/network_training/instruments/";
        val midiPath = "/mnt/network_training/midifiles/midis/";

        fun start(genre : String) {
            println("genre: ${genre}")
            val instrumentFile = File(instrumentPath + genre + ".csv")
            instrumentFile.delete()
            instrumentFile.createNewFile()

            File(midiPath + genre.toLowerCase()).walk().forEach {
                if (it != null) {
                    println("---- instrument ${it.name} ---")
                    println("instrument filepath: ${it.name}")
                    val curName = it.name;
                    val csvdata = convertToCsv(it)
                    println("csvdata was created")
                    val instruments = getInstrumentsFromData(csvdata)
                    instruments.forEach{
                        instrumentFile.appendText("$it;$curName\n")
                    }
                }
            }
            deleteAllCSVs()
        }

        private fun getInstrumentsFromData(csvdata: List<String>): List<String> {
            val res = mutableListOf<String>()
            csvdata.forEach{
                if(it.contains("Title_t") && !it.startsWith("1")){
                    res.add(it.split("\"")[1])
                }
            }
            return res
        }

        //converts to csv, returns the text and deletes the csv
        private fun convertToCsv(file: File): List<String> {
            val programPath = "/mnt/network_training/midicsv"
            val targetPath = "/mnt/trainingsdata/" + file.nameWithoutExtension + ".csv"
            val commandArray = arrayOf(programPath, file.absolutePath, targetPath)

            println("executing: $commandArray")
            Runtime.getRuntime().exec(commandArray).waitFor()
            println("done")

            println("reading lines from $targetPath")
            val res = File(targetPath).readLines()
            File(targetPath).delete()
            println("done and deleted")

            return res
        }

        private fun deleteAllCSVs(){
            val folder = File("/mnt/trainingsdata")
            println("Deleting CSV Files...")
            folder.listFiles().filter { it.isFile && it.name.endsWith(".csv") }.forEach { it.delete() }
        }
    }
}
