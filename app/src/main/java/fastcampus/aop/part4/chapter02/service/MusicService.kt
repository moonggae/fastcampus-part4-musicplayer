package fastcampus.aop.part4.chapter02.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/v3/9e73fcfe-798d-4187-8213-dc465b3f72d1")
    fun listMusics() : Call<MusicDto>
}