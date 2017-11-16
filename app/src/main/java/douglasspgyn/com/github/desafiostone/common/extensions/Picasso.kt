package douglasspgyn.com.github.desafiostone.common.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by Douglas on 12/11/17.
 */

fun ImageView.loadUrl(url: String) {
    Picasso.with(context).load(url).into(this)
}

fun ImageView.loadUrl(url: String, placeholder: Int) {
    Picasso.with(context).load(url).placeholder(placeholder).into(this)
}