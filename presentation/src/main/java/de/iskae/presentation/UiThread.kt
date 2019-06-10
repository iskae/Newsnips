package de.iskae.presentation

import de.iskae.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * @see <a href="https://github.com/hitherejoe/GithubTrending-1">Github Trending</a>
 */
class UiThread @Inject constructor() : PostExecutionThread {
  override val scheduler: Scheduler
    get() = AndroidSchedulers.mainThread()
}