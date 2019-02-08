package pl.canx.myapp3.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pl.canx.myapp3.R
import pl.canx.myapp3.api.ApiService
import pl.canx.myapp3.api.dto.PostDTO

class MainActivity : BaseActivity() {
    private var posts : List<PostDTO> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        btnGetPosts.setOnClickListener { getPosts() }
        btnDeleteLastPost.setOnClickListener { deleteLastPost() }
        btnAddPost.setOnClickListener { openAddPostActivity() }
        hideProgressBar()
        getPosts()
    }

    private fun getPosts() {
        showProgressBar()
        ApiService.getPosts()
            .subscribe(this::displayPosts, this::displayError, this::hideProgressBar)
            .run { addDisposable(this) }
    }

    private fun deleteLastPost() {
        if(!posts.isEmpty()){
            showProgressBar()
            ApiService.deletePost(posts.last().id ?: -1)
                .subscribe(this::getPosts, this::displayError)
                .run { addDisposable(this) }
        }
    }

    private fun displayPosts(posts: List<PostDTO>) {
        this.posts = posts
        posts.joinToString(separator = "\n") { "${it.id} ${it.title}" }.run { textView.text = this }
    }

    private fun displayError(error: Throwable) {
        println(error)
        hideProgressBar()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun openAddPostActivity() {
        Intent(this, AddPostActivity::class.java).run { startActivity(this) }
    }
}