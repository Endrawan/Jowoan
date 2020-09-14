package com.example.jowoan.internal

object PhoneUtils {
    fun toInternationalStandard(phone: String): String {
        return phone.takeLast(phone.length - 1)
    }

    fun toLocalStandard(phone: String): String {
        return "0$phone"
    }
}