package com.stone.starwarsstore.extension

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.stone.starwarsstore.R

val Context.picasso: Picasso
    get() = Picasso.with(this)

fun ImageView.load(path: String?, request: (RequestCreator) -> RequestCreator) {
    request(getContext().picasso.load(path)).error(R.drawable.product_placeholder).placeholder(R.drawable.product_placeholder).into(this)
}