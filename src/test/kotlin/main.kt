import it.introini.hnclient.create

fun main(args: Array<String>) {
    val hnClient = create()
    hnClient.topstories().take(10).map { hnClient.item(it) }.forEach(::println)
}