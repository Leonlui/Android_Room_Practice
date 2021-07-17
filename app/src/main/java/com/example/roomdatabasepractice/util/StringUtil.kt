package com.example.roomdatabasepractice.util

import android.text.Editable
import android.text.TextUtils

class StringUtil {
    companion object {
        fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
            return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
        }
    }
}