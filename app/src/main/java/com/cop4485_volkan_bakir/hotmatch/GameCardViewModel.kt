package com.cop4485_volkan_bakir.hotmatch

import androidx.lifecycle.ViewModel
import java.util.*

class GameCardViewModel: ViewModel() {

    val leveOneCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID())
    )

    val levelTwoCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_04, UUID.randomUUID()),
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_04, UUID.randomUUID())
    )

    val levelThreeCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_04, UUID.randomUUID()),
        GameCard(R.mipmap.monster_05, UUID.randomUUID()),
        GameCard(R.mipmap.monster_06, UUID.randomUUID()),
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_04, UUID.randomUUID()),
        GameCard(R.mipmap.monster_05, UUID.randomUUID()),
        GameCard(R.mipmap.monster_06, UUID.randomUUID())
    )

    val levelFourCardList: List<GameCard> = listOf(
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_04, UUID.randomUUID()),
        GameCard(R.mipmap.monster_05, UUID.randomUUID()),
        GameCard(R.mipmap.monster_06, UUID.randomUUID()),
        GameCard(R.mipmap.monster_07, UUID.randomUUID()),
        GameCard(R.mipmap.monster_08, UUID.randomUUID()),
        GameCard(R.mipmap.monster_01, UUID.randomUUID()),
        GameCard(R.mipmap.monster_02, UUID.randomUUID()),
        GameCard(R.mipmap.monster_03, UUID.randomUUID()),
        GameCard(R.mipmap.monster_04, UUID.randomUUID()),
        GameCard(R.mipmap.monster_05, UUID.randomUUID()),
        GameCard(R.mipmap.monster_06, UUID.randomUUID()),
        GameCard(R.mipmap.monster_07, UUID.randomUUID()),
        GameCard(R.mipmap.monster_08, UUID.randomUUID())
    )

}