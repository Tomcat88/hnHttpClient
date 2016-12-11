package it.introini.hnclient.model

import java.time.Instant

data class ItemId(val value: Long)
data class UserId(val value: String)

data class Item (val id:          ItemId,
                 val deleted:     Boolean,
                 val type:        ItemType,
                 val by:          UserId,
                 val time:        Instant,
                 val text:        String,
                 val dead:        Boolean,
                 val parent:      ItemId,
                 val kids:        Collection<ItemId>,
                 val url:         String,
                 val score:       Int,
                 val title:       String,
                 val parts:       Collection<ItemId>,
                 val descendants: Int)

data class User (val id:        UserId,
                 val delay:     Int,
                 val created:   Instant,
                 val karma:     Int,
                 val about:     String,
                 val submitted: Collection<ItemId>)

enum class ItemType {
    JOB,
    STORY,
    COMMENT,
    POLL,
    POLLOPT;

    override fun toString() = name.toLowerCase()
}