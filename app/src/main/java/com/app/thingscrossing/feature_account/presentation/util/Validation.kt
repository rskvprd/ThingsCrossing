package com.app.thingscrossing.feature_account.presentation.util

fun String.isValidPassword(): Boolean {
    return this.length >= 8
}

fun String.isValidUsername(): Boolean {
    return this.matches("^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])".toRegex())
}

fun String.isValidFirstName(): Boolean {
    return this.matches("([a-zA-Z\\u0401\\u0451\\u0410-\\u044f]{1,10})\\w+".toRegex())
}

fun String.isValidLastName(): Boolean {
    return this.matches("([a-zA-Z\\u0401\\u0451\\u0410-\\u044f]{1,10})\\w+".toRegex())
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return this.matches(emailRegex.toRegex())
}