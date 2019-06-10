package de.iskae.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

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
      Picasso.get().load(it).into(iv)
    }
  }
}