package it.introini.hnclient.httpclientwrapper

import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import it.introini.hnclient.model.*
import org.apache.http.HttpStatus
import org.json.JSONArray
import java.time.Instant

fun itemMapper(jsonNode: JsonNode): Item {
    val o = jsonNode.`object`
    return Item(
            o.getLong("id").let(::ItemId),
            o.optBoolean("deleted"),
            o.optString("type").let{ ItemType.valueOf(it.toUpperCase()) },
            o.optString("by").let(::UserId),
            o.optLong("time").let { Instant.ofEpochSecond(it) },
            o.optString("text"),
            o.optBoolean("dead"),
            o.optLong("parent").let(::ItemId),
            o.optJSONArray("kids")?.let { arrayMapper(it,::ItemId) } ?: emptyList(),
            o.optString("url"),
            o.optInt("score"),
            o.optString("title"),
            o.optJSONArray("parts")?.let { arrayMapper(it,::ItemId) } ?: emptyList(),
            o.optInt("descendants")
        )
}

fun userMapper(jsonNode: JsonNode): User {
    val o = jsonNode.`object`
    return User(
            o.getString("id").let(::UserId),
            o.getInt("delay"),
            o.getLong("created").let { Instant.ofEpochSecond(it) },
            o.getInt("karma"),
            o.getString("about"),
            o.getJSONArray("submitted").let { arrayMapper(it,::ItemId) }
    )
}

fun updateMapper(jsonNode: JsonNode): Pair<Collection<ItemId>, Collection<UserId>> {
    val o = jsonNode.`object`
    val items = o.getJSONArray("items").let { arrayMapper(it,::ItemId) }
    val profiles = o.getJSONArray("profiles").let { arrayMapper(it, ::UserId) }
    return Pair(items, profiles)
}

fun itemIdMapper(jsonNode: JsonNode): ItemId {
    return jsonNode.`object`.getLong("").let(::ItemId)
}

fun arrayItemIdMapper(jsonNode: JsonNode): Collection<ItemId> = arrayMapper(jsonNode.array,::ItemId)

fun <T,R> arrayMapper(jsonArray: JSONArray, mapper: (T) -> R): Collection<R> {
    return (0..jsonArray.length() -1 ).map { (jsonArray[it] as T).let(mapper) }
}

fun <T> httpGet(url: String, mapper: ((JsonNode) -> T)): T? {
    val resp = Unirest.get(url).asJson()
    if (resp.status == HttpStatus.SC_OK) {
        return resp.body.let(mapper)
    } else {
        return null
    }
}