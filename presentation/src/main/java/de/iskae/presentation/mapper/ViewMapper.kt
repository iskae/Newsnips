package de.iskae.presentation.mapper

interface ViewMapper<out V, in D> {
  fun mapToView(domain: D): V
}