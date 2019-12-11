package com.cop4485_volkan_bakir.hotmatch

import androidx.lifecycle.ViewModel
import java.util.*

class AvatarViewModel: ViewModel() {

    val avatarList: List<Avatar> = listOf(
        Avatar(R.mipmap.woman_01, UUID.randomUUID()),
        Avatar(R.mipmap.woman_02, UUID.randomUUID()),
        Avatar(R.mipmap.woman_03, UUID.randomUUID()),
        Avatar(R.mipmap.man_01, UUID.randomUUID()),
        Avatar(R.mipmap.man_02, UUID.randomUUID()),
        Avatar(R.mipmap.man_03, UUID.randomUUID())
    )

}