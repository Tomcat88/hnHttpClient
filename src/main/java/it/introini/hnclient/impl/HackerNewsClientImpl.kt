package it.introini.hnclient.impl

import it.introini.hnclient.HackerNewsClient
import it.introini.hnclient.httpclientwrapper.*
import it.introini.hnclient.model.Item
import it.introini.hnclient.model.ItemId
import it.introini.hnclient.model.User
import it.introini.hnclient.model.UserId

class HackerNewsClientImpl : HackerNewsClient {

    private val API_ENDPOINT = "https://hacker-news.firebaseio.com/v0/"
    private val ITEM_PATH    = "item"
    private val USER_PATH    = "user"
    private val TOP_PATH     = "topstories"
    private val NEW_PATH     = "newstories"
    private val BEST_PATH    = "beststories"
    private val MAX_PATH     = "maxitem"
    private val UPDATES_PATH = "updates"

    override fun items(itemIds: Collection<ItemId>): Collection<Item> = itemIds.map { httpGet("$API_ENDPOINT$ITEM_PATH/${it.value}.json", ::itemMapper) }.filter { it != null }.map { it!! }
    override fun users(userIds: Collection<UserId>): Collection<User> = userIds.map { httpGet("$API_ENDPOINT$USER_PATH/${it.value}.json", ::userMapper) }.filter { it != null }.map { it!! }

    override fun maxItemId(): ItemId? = httpGet("$API_ENDPOINT$MAX_PATH.json",::itemIdMapper)

    override fun topstories():  Collection<ItemId> = httpGet("$API_ENDPOINT$TOP_PATH.json", ::arrayItemIdMapper) ?: emptyList()
    override fun newstories():  Collection<ItemId> = httpGet("$API_ENDPOINT$NEW_PATH.json", ::arrayItemIdMapper) ?: emptyList()
    override fun beststories(): Collection<ItemId> = httpGet("$API_ENDPOINT$BEST_PATH.json", ::arrayItemIdMapper) ?: emptyList()

    override fun updates(): Pair<Collection<ItemId>, Collection<UserId>> = httpGet("$API_ENDPOINT$UPDATES_PATH.json", ::updateMapper) ?: Pair<Collection<ItemId>, Collection<UserId>>(emptyList(), emptyList())

}