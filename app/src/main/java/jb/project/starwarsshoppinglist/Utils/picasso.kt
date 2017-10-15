package jb.project.starwarsshoppinglist.Utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by Jb on 13/10/2017.
 */


val Context.picasso: Picasso
    get() = Picasso.with(this)

fun ImageView.load(path: String, request: (RequestCreator) -> RequestCreator) {
    request(context.picasso.load(path)).into(this)
}
