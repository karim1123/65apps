package com.karimgabbasov.a65apps
//функция для проверки на весоконый год
fun Int.isLeapYear(): Boolean{
    var leap = false
    if (this % 4 == 0){
        if (this % 100 == 0){
            leap = this % 400 == 0
        } else
            leap = true
    } else
        leap = false
    return leap
}