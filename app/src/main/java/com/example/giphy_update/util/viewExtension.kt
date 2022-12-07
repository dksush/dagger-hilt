package com.example.giphy_update.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphy_update.ui.base.BaseRecyclerAdapter
import com.example.giphy_update.ui.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit


fun clicks(view: View, ms: Long): Observable<View> = Observable.create<View> { emitter ->
    view.setOnClickListener {
        emitter.onNext(it)
///commit 1.1.7 2
    }
}.throttleFirst(ms, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())


fun View.clicks(
    ms: Long = 700L,
    disposable: CompositeDisposable,
    onClickListener: ((View) -> Unit)
) = clicks(this, ms)
    .subscribeBy(onError = Timber::e, onNext = { view -> onClickListener.invoke(view) })
    .addTo(disposable)


@Suppress("UNCHECKED_CAST")
@BindingAdapter("setData")
fun RecyclerView.setData(items: List<Any>?) {
    (this.adapter as? BaseRecyclerAdapter<Any, BaseViewHolder<Any>>)?.run {
        items?.let {
            setData(items)
        }
    }
}

@SuppressLint("ShowToast")
fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

}

@BindingAdapter("htmlText")
fun TextView.changeHtmlText(text: String) {
    this.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("bindImage")
fun bindImage(view: ImageView, res: String) {
    Glide.with(view.context)
        .load(res)
        .into(view)
}