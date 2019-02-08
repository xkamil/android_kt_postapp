package pl.canx.myapp3.api

import io.reactivex.Completable
import io.reactivex.Observable
import pl.canx.myapp3.api.dto.PostDTO
import retrofit2.http.*

const val BASE_URL = "http://oauth2testserver.herokuapp.com"

interface Api {

    @GET("/posts")
    fun getPosts(): Observable<List<PostDTO>>

    @GET("/posts/{id}?_embed=comments")
    fun getPost(@Path("id") id: Int): Observable<PostDTO>

    @POST("/posts")
    fun addPost(@Body post: PostDTO): Observable<PostDTO>

    @PUT("/posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: PostDTO)

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: Int) : Completable

}