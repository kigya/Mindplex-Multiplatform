package dev.kigya.mindplex.navigation.navigator.type

import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import kotlin.reflect.KType
import kotlin.reflect.typeOf

inline fun <reified TEnum : Enum<TEnum>> enumNavTypeEntry(
    crossinline valueOf: (String) -> TEnum,
): Pair<KType, NavType<TEnum>> =
    typeOf<TEnum>() to object : NavType<TEnum>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): TEnum? = bundle.getString(key)?.let(::parseValue)

        override fun parseValue(value: String): TEnum = valueOf(value)

        override fun put(
            bundle: Bundle,
            key: String,
            value: TEnum,
        ) = bundle.putString(key, value.name)
    }

inline fun <reified TEnum : Enum<TEnum>> nullableEnumNavType(
    crossinline valueOf: (String) -> TEnum,
): NavType<TEnum?> = object : NavType<TEnum?>(isNullableAllowed = true) {
    override fun get(
        bundle: Bundle,
        key: String,
    ): TEnum? = bundle.getString(key)?.let { valueOf(it) }

    override fun parseValue(value: String): TEnum? = if (value.isBlank()) null else valueOf(value)

    override fun put(
        bundle: Bundle,
        key: String,
        value: TEnum?,
    ) = bundle.putString(key, value?.name ?: "")
}
