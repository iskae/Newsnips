package de.iskae.core.util

import org.threeten.bp.Instant
import org.threeten.bp.Instant.now
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

object TimeHelpers {

  fun getHourDifferenceFromPublishTime(publishTime: String): Int {
    val instant = Instant.parse(publishTime)
    val currentTime = ZonedDateTime.ofInstant(now(), ZoneId.systemDefault())
    val publishTimeLocal = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    return currentTime.hour - publishTimeLocal.hour
  }
}