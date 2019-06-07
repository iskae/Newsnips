package de.iskae.domain.executor

import io.reactivex.Scheduler

/**
 * @see <a href="https://github.com/hitherejoe/GithubTrending-1">Github Trending</a>
 */
interface PostExecutionThread {
  val scheduler: Scheduler
}