package pl.canx.myapp3.api

import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import pl.canx.myapp3.api.dto.PostDTO
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Logger

object ApiService {
    private val log = Logger.getLogger(ApiService::class.java.name)

    private val loggingInterceptor = Interceptor { it ->
        val request = it.request()
        log.info("${request.method()} ${request.url()}")
        it.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    private val gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    fun getPosts(): Observable<List<PostDTO>> {
        return api.getPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun addPost(title: String, text: String): Observable<PostDTO> {
        return api.addPost(PostDTO(title = title, text = text))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun deletePost(id: Int): Completable {
        return api.deletePost(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}