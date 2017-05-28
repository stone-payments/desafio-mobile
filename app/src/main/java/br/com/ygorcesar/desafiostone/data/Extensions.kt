package br.com.ygorcesar.desafiostone.data

import android.app.ProgressDialog
import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.default_progress.*
import kotlinx.android.synthetic.main.empty_view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.replace(fragment: Fragment, @IdRes id: Int) = this.supportFragmentManager.beginTransaction().replace(id, fragment).commit()

fun Fragment.toast(@StringRes idRes: Int, duration: Int = Toast.LENGTH_SHORT) = context?.let { Toast.makeText(it, idRes, duration).show() }

fun Fragment.showEmptyView() {
    try {
        ln_empty_view?.visibility = View.VISIBLE
    } catch(e: NullPointerException) {
        println("View not found")
    }
}

fun Fragment.showProgress() {
    try {
        progress_container?.visibility = View.VISIBLE
    } catch(e: NullPointerException) {
        println("View not found")
    }
}

fun Fragment.hideProgress() {
    try {
        progress_container?.visibility = View.GONE
    } catch(e: NullPointerException) {
        println("View not found")
    }
}

fun Fragment.replace(fragment: Fragment, @IdRes id: Int) = this.fragmentManager.beginTransaction().replace(id, fragment).commit()

fun Fragment.replaceToStack(fragment: Fragment, @IdRes id: Int) = this.fragmentManager.beginTransaction().replace(id, fragment).addToBackStack(null).commit()

fun Fragment.progressDialog(@StringRes msgRes: Int): ProgressDialog {
    val progress = ProgressDialog(context)
    return progress.apply {
        isIndeterminate = true
        setMessage(getString(msgRes))
        show()
    }
}

fun Context.toast(@StringRes idRes: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, idRes, duration).show()

fun Date.formatToBrasil(): String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)

fun Double.toCurrencyBRL(): String = NumberFormat.getCurrencyInstance().format(this)


fun String.lastFourDigits() = if (this.length > 4) this.substring(this.length - 4) else this

fun View.snack(@StringRes msgRes: Int, @StringRes actionRes: Int, duration: Int = Snackbar.LENGTH_SHORT, f: (v: View) -> Unit = {}) {
    Snackbar.make(this, msgRes, duration)
            .setAction(actionRes, f)
            .show()
}

fun RecyclerView.layoutGrid(isFixedSize: Boolean = true, spaceCount: Int = 2, orientation: Int = GridLayoutManager.VERTICAL, isReverse: Boolean = false) {
    this.context?.let {
        this.setHasFixedSize(isFixedSize)
        this.layoutManager = GridLayoutManager(it, spaceCount, orientation, isReverse)
    }
}

fun RecyclerView.layoutLinear(isFixedSize: Boolean = true, orientation: Int = GridLayoutManager.VERTICAL, isReverse: Boolean = false) {
    this.context?.let {
        this.setHasFixedSize(isFixedSize)
        this.layoutManager = LinearLayoutManager(it, orientation, isReverse)
    }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
            .load(url)
            .centerCrop()
            .into(this)
}