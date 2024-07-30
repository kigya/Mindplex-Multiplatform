package dev.kigya.mindplex.feature.login.data.repository.interceptor

import dev.kigya.mindplex.core.util.dsl.ifPresentOrElseReturn
import dev.kigya.mindplex.feature.login.domain.contract.ProfileImageInterceptorContract

class ProfileImageInterceptor : ProfileImageInterceptorContract {
    override fun intercept(url: String?): String = url.ifPresentOrElseReturn(
        ifPresent = { it },
        ifAbsent = { images.random() },
    )

    private companion object {
        val images = listOf(
            "https://i.postimg.cc/NLRgsrHB/ic-profile-first.png",
            "https://i.postimg.cc/BjzWcqKm/ic-profile-second.png",
            "https://i.postimg.cc/2qLs0Pdr/ic-profile-third.png",
            "https://i.postimg.cc/9D0574r4/ic-profile-fourth.png",
            "https://i.postimg.cc/ppqMrQXb/ic-profile-fifth.png",
            "https://i.postimg.cc/vcQdG2yt/ic-profile-sixth.png",
            "https://i.postimg.cc/vD7sFFJm/ic-profile-seventh.png",
            "https://i.postimg.cc/ZvmzBVdZ/ic-profile-ninth.png",
            "https://i.postimg.cc/dkrKVzD4/ic-profile-tenth.png",
            "https://i.postimg.cc/wRk8hLXN/ic-profile-eighth.png",
            "https://i.postimg.cc/hJHBgS4X/ic-profile-eleventh.png",
            "https://i.postimg.cc/zLLr9LNm/ic-profile-twelfth.png",
            "https://i.postimg.cc/w1jHQfbj/ic-profile-thirteenth.png",
            "https://i.postimg.cc/SJqmJN68/ic-profile-fourteenth.png",
            "https://i.postimg.cc/SJrk8FdP/ic-profile-fifteenth.png",
            "https://i.postimg.cc/fVqs2c6j/ic-profile-sixteenth.png",
        )
    }
}
