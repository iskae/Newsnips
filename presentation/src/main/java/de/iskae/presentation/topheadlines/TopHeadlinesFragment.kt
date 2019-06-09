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
import dagger.android.support.AndroidSupportInjection
import de.iskae.core.constants.Country
import de.iskae.presentation.R
import de.iskae.presentation.databinding.FragmentTopHeadlinesBinding
import de.iskae.presentation.di.modules.ViewModelFactory
import de.iskae.presentation.state.Resource
import timber.log.Timber
import javax.inject.Inject

class TopHeadlinesFragment : Fragment() {

  @Inject lateinit var viewModelFactory: ViewModelFactory

  private lateinit var binding: FragmentTopHeadlinesBinding

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
    topHeadlinesViewModel.getArticles().observe(viewLifecycleOwner, Observer {
      when (it) {
        is Resource.Loading -> Timber.d("Loading")
        is Resource.Error -> Timber.e(it.throwable)
        is Resource.Success -> Timber.d("Success")
      }
    })
  }
}