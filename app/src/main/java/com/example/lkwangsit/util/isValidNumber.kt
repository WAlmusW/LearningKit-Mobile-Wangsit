package com.example.lkwangsit.util

fun String.isValidNumber(): Boolean =
    isEmpty() || matches(Regex("^-?(\\d+)?(\\.\\d*)?$"))