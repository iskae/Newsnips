package de.iskae.core.test

import java.util.UUID
import kotlin.random.Random

object DataFactory {
  fun randomString(): String {
    return UUID.randomUUID().toString()
  }

  fun randomDouble(): Double {
    return Random.nextDouble()
  }

  fun randomInt(): Int {
    return Random.nextInt()
  }

  fun randomLong(): Long {
    return Random.nextLong()
  }

  fun randomBoolean(): Boolean {
    return Random.nextBoolean()
  }
}