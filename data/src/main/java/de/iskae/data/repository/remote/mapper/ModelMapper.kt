package de.iskae.data.repository.remote.mapper

interface ModelMapper<M, E> {
    fun mapFromModel(model: M): E
}