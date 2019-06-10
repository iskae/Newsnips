package de.iskae.presentation.topheadlines

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import dagger.android.support.AndroidSupportInjection
import de.iskae.presentation.R
import de.iskae.presentation.databinding.FragmentTopHeadlinesBinding
import de.iskae.presentation.di.modules.ViewModelFactory
import de.iskae.presentation.state.Resource
import de.iskae.presentation.topheadlines.adapter.ArticleDiffCallback
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapter
import de.iskae.presentation.topheadlines.adapter.ArticleListAdapterConstants.VIEW_TYPE_FULL_WIDTH
import de.iskae.presentation.topheadlines.adapter.OnArticleListAdapterInteraction
import timber.log.Timber
import javax.inject.Inject

class TopHeadlinesFragment : Fragment(), OnArticleListAdapterInteraction {

  @Inject lateinit var viewModelFactory: ViewModelFactory

  private lateinit var binding: FragmentTopHeadlinesBinding
  private lateinit var articleListAdapter: ArticleListAdapter

  private val topHeadlinesViewModel: TopHeadlinesViewModel by navGraphViewModels(R.id.navGraphMain) { viewModelFactory }

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_headlines, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
    topHeadlinesViewModel.getResource().observe(viewLifecycleOwner, Observer {
      when (it) {
        is Resource.Loading -> binding.isLoading = true
        is Resource.Error -> {
          binding.isLoading = false
          Timber.e(it.throwable)
        }
        is Resource.Success -> {
          binding.isLoading = false
        }
      }
    })
    topHeadlinesViewModel.articlesData.observe(viewLifecycleOwner, Observer { articles ->
      articleListAdapter.submitList(articles)
    })
  }

  private fun initRecyclerView() {
    val numberOfColumns = resources.getInteger(R.integer.article_list_columns)
    val manager = GridLayoutManager(context, numberOfColumns)
    //https://stackoverflow.com/questions/30808225/dynamically-change-the-number-of-columns-of-a-gridlayoutmanager
    val onSpanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int {
        return if (articleListAdapter.getItemViewType(position) == VIEW_TYPE_FULL_WIDTH) {
          numberOfColumns
        } else {
          1
        }
      }
    }
    manager.spanSizeLookup = onSpanSizeLookup

    articleListAdapter = ArticleListAdapter(ArticleDiffCallback(), this@TopHeadlinesFragment)
    binding.uiTopHeadlinesRecycler.apply {
      layoutManager = manager
      adapter = articleListAdapter
      addItemDecoration(DividerItemDecoration(context, manager.orientation))
    }
  }

  override fun onItemClick(position: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}