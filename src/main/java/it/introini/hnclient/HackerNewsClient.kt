package it.introini.hnclient

import it.introini.hnclient.impl.HackerNewsClientImpl
import it.introini.hnclient.model.Item
import it.introini.hnclient.model.ItemId
import it.introini.hnclient.model.User
import it.introini.hnclient.model.UserId

fun create() = HackerNewsClientImpl()

interface HackerNewsClient {

    fun items (itemIds: Collection<ItemId>) : Collection<Item>
    fun item  (itemId: ItemId) : Item? = items(listOf(itemId)).firstOrNull()

    fun users (userIds: Collection<UserId>) : Collection<User>
    fun user  (userId: UserId) : User? = users(listOf(userId)).firstOrNull()

    fun maxItemId() : ItemId?

    fun topstories()  : Collection<ItemId>
    fun newstories()  : Collection<ItemId>
    fun beststories() : Collection<ItemId>

    fun updates() : Pair<Collection<ItemId>, Collection<UserId>>
}