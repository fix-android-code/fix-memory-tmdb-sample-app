package com.example.suki.common.util

import android.graphics.Color

object Constant {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "a3fa04090541f2bd7df49068a6259c18"
    const val DATABASE_NAME = "suki_app_database"

    //List Status
    const val STATUS_ALL = "all"
    const val STATUS_WATCHING = "watching"
    const val STATUS_COMPLETED = "completed"
    const val STATUS_ON_HOLD = "on_hold"
    const val STATUS_DROPPED = "dropped"
    const val STATUS_PTW = "plan_to_watch"
    const val STATUS_PTR = "plan_to_read"
    const val STATUS_READING = "reading"

    const val STATUS_AIRING = "currently_airing"

    //Responses
    const val RESPONSE_OK = "ok"
    const val RESPONSE_ERROR = "error"
    const val RESPONSE_NONE = ""

    //Errors
    const val ERROR_SERVER = "error_server"


    // current, planning, completed, dropped, paused
    val STATUS_COLOR_LIST = arrayListOf(
        Color.parseColor("#3BAEEA"),
        Color.parseColor("#F79A63"),
        Color.parseColor("#7BD555"),
        Color.parseColor("#E85D75"),
        Color.parseColor("#F17575")
    )

//    val STATUS_COLOR_MAP = hashMapOf(
//        Pair(MediaListStatus.CURRENT, STATUS_COLOR_LIST[0]),
//        Pair(MediaListStatus.PLANNING, STATUS_COLOR_LIST[1]),
//        Pair(MediaListStatus.COMPLETED, STATUS_COLOR_LIST[2]),
//        Pair(MediaListStatus.DROPPED, STATUS_COLOR_LIST[3]),
//        Pair(MediaListStatus.PAUSED, STATUS_COLOR_LIST[4])
//    )

    val DEFAULT_GENRE_COLOR = "#727272"

    val GENRE_COLOR = hashMapOf(
        Pair("Action", "#24687B"),
        Pair("Adventure", "#014037"),
        Pair("Comedy", "#E6977E"),
        Pair("Drama", "#7E1416"),
        Pair("Ecchi", "#7E174A"),
        Pair("Fantasy", "#989D60"),
        Pair("Hentai", "#37286B"),
        Pair("Horror", "#5B1765"),
        Pair("Mahou Shoujo", "#BF5264"),
        Pair("Mecha", "#542437"),
        Pair("Music", "#329669"),
        Pair("Mystery", "#3D3251"),
        Pair("Psychological", "#D85C43"),
        Pair("Romance", "#C02944"),
        Pair("Sci-Fi", "#85B14B"),
        Pair("Slice of Life", "#D3B042"),
        Pair("Sports", "#6B9145"),
        Pair("Supernatural", "#338074"),
        Pair("Thriller", "#224C80")
    )
}
