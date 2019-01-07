package ch.stephgit.memory.persistence.service

import ch.stephgit.memory.persistence.entity.Player
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface APIClient {

    @POST("/login")
    fun postLogin(player: Player): Call<Int>

    @POST("/register")
    fun postRegister(player: Player): Call<Int>

    @GET("/user")
    fun getUsers(): Call<List<Player>>


    companion object {
        val BASE_URL = "http://167.99.143.163:8080"

        fun build(): APIClient {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create<APIClient>(APIClient::class.java)
        }
    }
}