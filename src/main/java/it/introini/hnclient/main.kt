package it.introini.hnclient

fun main(args: Array<String>) {
    val hnClient = create()
    hnClient.topstories().take(10).map { hnClient.item(it) }.forEach(::println)
}