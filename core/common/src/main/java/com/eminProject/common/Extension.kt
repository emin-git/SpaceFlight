package com.eminProject.common

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { it.uppercase() }
}