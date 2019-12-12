package com.cop4485_volkan_bakir.hotmatch

import androidx.lifecycle.ViewModel

class CardImageViewModel: ViewModel() {

    val cardImageListLeveOne: List<CardImage> = listOf(
        CardImage(R.mipmap.monster_01),
        CardImage(R.mipmap.monster_02),
        CardImage(R.mipmap.monster_03),
        CardImage(R.mipmap.monster_04),
        CardImage(R.mipmap.monster_05),
        CardImage(R.mipmap.monster_06)
    )

}
