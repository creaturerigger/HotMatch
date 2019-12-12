package com.cop4485_volkan_bakir.hotmatch

import androidx.lifecycle.ViewModel
import java.util.*

class GameCardViewModel: ViewModel() {

    val levelOneCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID())
    )

    val levelTwoCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID())
    )

    val levelThreeCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID())
    )

    val levelFourCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID()),
        GameCard(R.mipmap.default_image, UUID.randomUUID())
    )

}