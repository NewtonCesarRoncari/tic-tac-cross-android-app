package com.nroncari.tictaccrossandroidapp.utils

interface Mapper<S, T> {
    fun map(source: S): T
}
