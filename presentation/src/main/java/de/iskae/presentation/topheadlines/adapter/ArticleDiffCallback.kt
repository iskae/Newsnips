package de.iskae.presentation.topheadlines.adapter

import androidx.recyclerview.widget.DiffUtil
import de.iskae.presentation.model.ArticleView

class ArticleDiffCallback : DiffUtil.ItemCallback<ArticleView>() {

  override fun areItemsTheSame(oldItem: ArticleView, newItem: ArticleView) = oldItem.directUrl == newItem.directUrl

  override fun areContentsTheSame(oldItem: ArticleView, newItem: ArticleView) = oldItem == newItem
}