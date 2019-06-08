package de.iskae.data.mapper

interface EntityMapper<E, D> {
  fun mapToEntity(domain: D): E
  fun mapFromEntity(entity: E): D
}