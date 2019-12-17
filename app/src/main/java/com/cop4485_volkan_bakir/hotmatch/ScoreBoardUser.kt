package com.cop4485_volkan_bakir.hotmatch

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ScoreBoardUser(
    val nickname: String,
    val userPhotoUrl: String,
    val highScore: String
)