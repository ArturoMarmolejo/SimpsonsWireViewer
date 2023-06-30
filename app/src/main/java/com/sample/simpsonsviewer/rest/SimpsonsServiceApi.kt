package com.sample.simpsonsviewer.rest

import android.os.Build
import androidx.core.os.BuildCompat
import com.sample.simpsonsviewer.BuildConfig
import com.sample.simpsonsviewer.model.apimodel.SimpsonsCharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpsonsServiceApi {

    @GET("/")
    suspend fun getAllSimpsonsCharacters(
        @Query("q") q: String = "$PARAM1 $PARAM2",
        @Query("format") format: String = FORMAT
    ): Response<SimpsonsCharacterResponse>

    companion object {
        //http://api.duckduckgo.com/?q=simpsons+characters&format=json
        const val BASE_URL = "http://api.duckduckgo.com/"
        const val PARAM1 = BuildConfig.CHARACTER
            //const val PARAM1..
        const val PARAM2 = "characters"
        const val FORMAT = "json"
    }

}