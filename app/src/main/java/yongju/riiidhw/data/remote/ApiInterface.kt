package yongju.riiidhw.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import yongju.riiidhw.model.TypiCodeModel

interface ApiInterface {
   @GET("/posts")
   fun getPosts(@Query("_start")start: Int, @Query("_limit") limit: Int): Single<List<TypiCodeModel>>

   @GET("/posts/{postId}")
   fun getPost(@Path("postId") postId: Long): Single<TypiCodeModel>
}