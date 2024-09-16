package com.github.ebrahimi16153.cinemahub.utils

import com.github.ebrahimi16153.cinemahub.data.model.Credits
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

fun List<Credits.Crew>.findDirector():String?{
    var result :String? = null
    this.forEach {
       if (it.job == "Director"){
          result= it.name + " "
        }
    }
    return result
}


fun List<Credits.Crew>.findProducer():String?{
    var result :String? = null
    this.forEach {
        if (it.job == "Producer"){
           result = it.name+" "
        }
    }
    return result
}