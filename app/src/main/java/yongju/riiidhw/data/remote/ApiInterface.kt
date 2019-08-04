package yongju.riiidhw.data.remote

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import yongju.riiidhw.model.TypiCodeCommentModel
import yongju.riiidhw.model.TypiCodeModel

interface ApiInterface {
   @GET("/posts")
   fun getPosts(@Query("_start")start: Int, @Query("_limit") limit: Int): Single<List<TypiCodeModel>>

   @GET("/posts/{postId}")
   fun getPost(@Path("postId") postId: Long): Single<TypiCodeModel>

   @GET("/posts/{postId}/comments")
   fun getComments(@Path("postId") postId: Long): Single<List<TypiCodeCommentModel>>

   @DELETE("/posts/{postId}")
   fun deletePost(@Path("postId") postId: Long): Completable

   @Headers("Content-Type: application/json; charset=utf-8")
   @PATCH("/posts/{postId}")
   fun updatePost(@Path("postId") postId: Long, @Body body: Map<String, String>): Completable
}