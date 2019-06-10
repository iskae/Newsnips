package de.iskae.presentation.topheadlines

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.AndroidInjection
import de.iskae.presentation.R
import de.iskae.presentation.databinding.ActivityTopHeadlinesBinding
import de.iskae.presentation.di.modules.ViewModelFactory
import de.iskae.presentation.state.Resource
import de.iskae.presentation.topheadlines.adapter.ArticleDiffCallback
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapter
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapterConstants
import de.iskae.presentation.topheadlines.adapter.OnArticleListAdapterInteraction
import timber.log.Timber
import javax.inject.Inject

class TopHeadlinesActivity : AppCompatActivity(), OnArticleListAdapterInteraction {

  @Inject lateinit var viewModelFactory: ViewModelFactory

  private lateinit var articleListAdapter: ArticleListAdapter

  private lateinit var binding: ActivityTopHeadlinesBinding

  private val topHeadlinesViewModel by lazy {
    ViewModelProviders.of(this, viewModelFactory).get(TopHeadlinesViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_top_headlines)

    initRecyclerView()

    binding.uiRetryButton.setOnClickListener {
      topHeadlinesViewModel.retry()
    }
    topHeadlinesViewModel.getResource().observe(this, Observer {
      when (it) {
        is Resource.Loading -> {
          binding.apply {
            isLoading = true
            uiRetryButton.visibility = View.GONE
          }
        }
        is Resource.Error -> {
          binding.apply {
            isLoading = false
            uiRetryButton.visibility = View.VISIBLE
          }
          Timber.e(it.throwable)
        }
        is Resource.Success -> {
          binding.apply {
            isLoading = false
            uiRetryButton.visibility = View.GONE
          }
        }
      }
    })
    topHeadlinesViewModel.articlesData.observe(this, Observer { articles ->
      articleListAdapter.submitList(articles)
    })
  }

  private fun initRecyclerView() {
    val numberOfColumns = resources.getInteger(R.integer.article_list_columns)
    val manager = GridLayoutManager(this, numberOfColumns)
    //https://stackoverflow.com/questions/30808225/dynamically-change-the-number-of-columns-of-a-gridlayoutmanager
    val onSpanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (articleListAdapter.getItemViewType(position) == ArticleListAdapterConstants.VIEW_TYPE_FULL_WIDTH) {
          numberOfColumns
        } else {
          1
        }
      }
    }
    manager.spanSizeLookup = onSpanSizeLookup

    articleListAdapter = ArticleListAdapter(ArticleDiffCallback(), this@TopHeadlinesActivity)
    binding.uiTopHeadlinesRecycler.apply {
      layoutManager = manager
      adapter = articleListAdapter
    }
  }

  override fun onItemClick(directUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(directUrl))
    if (intent.resolveActivity(packageManager) != null) {
      startActivity(intent)
    } else {
      Toast.makeText(this, getString(R.string.browser_not_found_toast), Toast.LENGTH_SHORT).show()
    }
  }

}