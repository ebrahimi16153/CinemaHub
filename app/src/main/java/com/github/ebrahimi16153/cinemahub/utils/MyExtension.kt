package com.github.ebrahimi16153.cinemahub.utils

import com.github.ebrahimi16153.cinemahub.data.model.Genre
import kotlin.math.pow

object MyExtension {



fun Int.toGenre(list:List<Genre>):Genre?{

    list.forEach { item ->
        if (item.id == this) return item
    }
    return null

}



}