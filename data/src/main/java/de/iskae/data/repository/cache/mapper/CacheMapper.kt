package de.iskae.data.repository.cache.mapper

interface CacheMapper<C, E> {
  fun mapToEntity(cache: C): E
  fun mapFromEntity(entity: E): C
}