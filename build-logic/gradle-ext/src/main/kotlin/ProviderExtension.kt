@file:Suppress("NOTHING_TO_INLINE")

import org.gradle.api.provider.Provider

inline fun Provider<String>.getInt(): Int = get().toInt()
