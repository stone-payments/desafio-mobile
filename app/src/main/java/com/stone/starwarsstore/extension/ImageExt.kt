package com.stone.starwarsstore.extension

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

val Context.picasso: Picasso
    get() = Picasso.with(this)

fun ImageView.load(path: String?, request: (RequestCreator) -> RequestCreator) {
    request(getContext().picasso.load(path)).into(this)
}