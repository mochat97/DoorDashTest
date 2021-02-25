package com.mshaw.doordashtest.util.extensions

import android.content.SharedPreferences
import androidx.core.content.edit

@Suppress("NAME_SHADOWING")
inline operator fun <reified T> SharedPreferences.get(key: String, defValue: T? = null): T? {
    return when (T::class) {
        Set::class -> {
            val defValue = defValue as? Set<String>
            getStringSet(key, defValue ?: setOf()) as? T
        }
        String::class -> {
            val defValue = defValue as? String
            getString(key, defValue) as? T
        }
        Int::class -> {
            val defValue = defValue as? Int
            getInt(key, defValue ?: -1) as? T
        }
        Float::class -> {
            val defValue = defValue as? Float
            getFloat(key, defValue ?: -1f) as? T
        }
        Long::class -> {
            val defValue = defValue as? Long
            getLong(key, defValue ?: -1L) as? T
        }
        Boolean::class -> {
            val defValue = defValue as? Boolean
            getBoolean(key, defValue ?: false) as? T
        }
        else -> defValue
    }
}

@Suppress("NAME_SHADOWING")
inline operator fun <reified T> SharedPreferences.set(key: String, value: T?) {
    edit {
        when (T::class) {
            Set::class -> {
                putStringSet(key, value as? Set<String>)
            }
            String::class -> {
                putString(key, value as? String)
            }
            Int::class -> {
                putInt(key, value as Int)
            }
            Float::class -> {
                putFloat(key, value as Float)
            }
            Long::class -> {
                putLong(key, value as Long)
            }
            Boolean::class -> {
                putBoolean(key, value as Boolean)
            }
        }
    }
}