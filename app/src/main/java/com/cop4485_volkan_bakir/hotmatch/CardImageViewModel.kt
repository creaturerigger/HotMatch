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

    val cardImageListLevelTwo: MutableList<CardImage> = mutableListOf(
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_04, UUID.randomUUID()),
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_04, UUID.randomUUID())
    )

    val cardImageListLevelThree: MutableList<CardImage> = mutableListOf(
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_04, UUID.randomUUID()),
        CardImage(R.mipmap.monster_05, UUID.randomUUID()),
        CardImage(R.mipmap.monster_06, UUID.randomUUID()),
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_04, UUID.randomUUID()),
        CardImage(R.mipmap.monster_05, UUID.randomUUID()),
        CardImage(R.mipmap.monster_06, UUID.randomUUID())
    )

    val cardImageListLevelFour: MutableList<CardImage> = mutableListOf(
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_04, UUID.randomUUID()),
        CardImage(R.mipmap.monster_05, UUID.randomUUID()),
        CardImage(R.mipmap.monster_06, UUID.randomUUID()),
        CardImage(R.mipmap.monster_07, UUID.randomUUID()),
        CardImage(R.mipmap.monster_08, UUID.randomUUID()),
        CardImage(R.mipmap.monster_01, UUID.randomUUID()),
        CardImage(R.mipmap.monster_02, UUID.randomUUID()),
        CardImage(R.mipmap.monster_03, UUID.randomUUID()),
        CardImage(R.mipmap.monster_04, UUID.randomUUID()),
        CardImage(R.mipmap.monster_05, UUID.randomUUID()),
        CardImage(R.mipmap.monster_06, UUID.randomUUID()),
        CardImage(R.mipmap.monster_07, UUID.randomUUID()),
        CardImage(R.mipmap.monster_08, UUID.randomUUID())
    )

}
