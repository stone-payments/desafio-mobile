package jb.project.starwarsshoppinglist.model

/**
 * Created by Jb on 12/10/2017.
 */

data class Response<out T>(val type: String, val value: List<T>)