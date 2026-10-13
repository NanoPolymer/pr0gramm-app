package com.pr0gramm.app.util

import com.pr0gramm.app.Settings
import com.pr0gramm.app.services.MediaDomain
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class UseSecondaryServerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()

        if (Settings.useSecondaryServers) {
            req = req.newBuilder()
                .url(injectSecondaryServer(req.url))
                .build()
        }

        return chain.proceed(req)
    }

    private fun injectSecondaryServer(url: HttpUrl): HttpUrl {
        return when (url.host) {
            "img.$MediaDomain" -> url.newBuilder().host("images.$MediaDomain").build()
            "vid.$MediaDomain" -> url.newBuilder().host("videos.$MediaDomain").build()
            "full.$MediaDomain" -> url.newBuilder().host("fullsize.$MediaDomain").build()
            else -> url
        }
    }
}
