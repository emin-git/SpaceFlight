package com.emin.common

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { it.uppercase() }
}