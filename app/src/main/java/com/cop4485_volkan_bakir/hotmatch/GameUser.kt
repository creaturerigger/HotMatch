package com.cop4485_volkan_bakir.hotmatch

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GameUser(
    var userId: String? = "",
    var score: Int? = null,
    var highScore: Int? = null,
    var level: Int? = null
)