package com.cop4485_volkan_bakir.hotmatch

import androidx.lifecycle.ViewModel
import java.util.*

class CardImageViewModel: ViewModel() {

    val cardImageListLevelOne: MutableList<CardImage> = mutableListOf(
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID())
    )

}
