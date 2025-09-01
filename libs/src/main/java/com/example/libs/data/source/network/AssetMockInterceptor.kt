package com.example.libs.data.source.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class AssetMockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        // e.g., only mock when flavor==mock OR when URL has header/param "X-Mock: true"
        val path = req.url.encodedPath
        val assetFile = when {
            path.endsWith("/products") -> "product_list_200.json"
            path.endsWith("/filters")  -> "filters_200.json"
            else -> null
        }
        if (assetFile != null) {
            val body = context.assets.open(assetFile).bufferedReader().readText()
            return Response.Builder()
                .request(req)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(body.toByteArray().toResponseBody("application/json".toMediaType()))
                .build()
        }
        return chain.proceed(req)
    }
}