package com.github.motassemja.moauth.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

/**
 * Created by moja on 12.06.2017.
 */

class MoAuthTokenResult(
        @SerializedName("access_token")
        var accessToken: String?,
        @SerializedName("refresh_token")
        var refreshToken: String?, expiresIn: Int) {

    var expiresIn: Double = 0.toDouble()

    var timestamp: Int = 0

    init {
        this.expiresIn = expiresIn.toDouble()
        timestamp = (System.currentTimeMillis() / 1000).toInt()
    }

    override fun equals(@Nullable other: Any?): Boolean {
        return (other is MoAuthTokenResult
                && other.accessToken == this.accessToken
                && other.expiresIn == this.expiresIn
                && other.refreshToken == this.refreshToken
                && other.timestamp == this.timestamp)
    }

    override fun hashCode(): Int {
        var result = accessToken?.hashCode() ?: 0
        result = 31 * result + (refreshToken?.hashCode() ?: 0)
        result = 31 * result + expiresIn.hashCode()
        result = 31 * result + timestamp
        return result
    }
}
