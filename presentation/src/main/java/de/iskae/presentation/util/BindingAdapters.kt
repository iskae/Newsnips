package de.iskae.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object BindingAdapters {
  @JvmStatic
  @BindingAdapter("visibleOrGone")
  fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
  }

  @JvmStatic
  @BindingAdapter("loadImageUrl")
  fun loadImageUrl(iv: ImageView, imageUrl: String?) {
    imageUrl?.let {
      Glide.with(iv).load(it).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv)
    }
  }
}