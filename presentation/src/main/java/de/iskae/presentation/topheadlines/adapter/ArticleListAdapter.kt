package de.iskae.presentation.topheadlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.iskae.presentation.databinding.ItemArticleBinding
import de.iskae.presentation.model.ArticleView
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapterConstants.FULL_WIDTH_ITEM_POSITION_COUNT
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapterConstants.VIEW_TYPE_FULL_WIDTH
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapterConstants.VIEW_TYPE_REGULAR

class ArticleListAdapter(
    diffCallback: ArticleDiffCallback,
    private val onArticleListAdapterInteraction: OnArticleListAdapterInteraction
) : PagedListAdapter<ArticleView, ArticleListAdapter.ArticleViewHolder>(diffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, position: Int): ArticleViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemArticleBinding.inflate(inflater)
    return ArticleViewHolder(binding)
  }

  override fun onBindViewHolder(viewHolder: ArticleViewHolder, position: Int) {
    viewHolder.bind(getItem(position) as ArticleView)
  }

  override fun getItemViewType(position: Int): Int {
    return if (position % FULL_WIDTH_ITEM_POSITION_COUNT == 0) {
      VIEW_TYPE_FULL_WIDTH
    } else {
      VIEW_TYPE_REGULAR
    }
  }

  inner class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(articleView: ArticleView) {
      binding.data = articleView
      binding.isFullWidth = itemViewType == VIEW_TYPE_FULL_WIDTH
      binding.root.setOnClickListener { onArticleListAdapterInteraction.onItemClick(articleView.directUrl) }
    }
  }
}