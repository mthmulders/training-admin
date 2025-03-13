package it.mulders.talks.trainingadmin.shared

import java.util.Optional

fun <T> Optional<T>.unwrap(): T? = orElse(null)