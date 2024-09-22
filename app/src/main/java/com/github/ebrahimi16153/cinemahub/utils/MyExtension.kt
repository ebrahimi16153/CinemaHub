package com.github.ebrahimi16153.cinemahub.utils

import com.github.ebrahimi16153.cinemahub.data.model.Credits


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