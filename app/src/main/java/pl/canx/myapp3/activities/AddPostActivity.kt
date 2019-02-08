package pl.canx.myapp3.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_post.*
import pl.canx.myapp3.R
import pl.canx.myapp3.api.ApiService
import pl.canx.myapp3.api.dto.PostDTO

class AddPostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        init()
    }

    private fun init() {
        hideProgressBar()
        btnAPAaddPost.setOnClickListener { addPost() }
    }

    private fun addPost() {
        showProgressBar()
        val title = etAPAtitle.text.toString()
        val text = etAPAtext.text.toString()
        ApiService.addPost(title, text)
            .subscribe(this::onPostAdded, this::onAddPostError, this::hideProgressBar)
            .run { addDisposable(this) }
    }

    private fun onPostAdded(post: PostDTO) {
        Toast.makeText(this, "Post added. Id: ${post.id}", Toast.LENGTH_LONG).show()
        Intent(this, MainActivity::class.java).run { startActivity(this) }
    }

    private fun onAddPostError(error: Throwable) {
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        pbAPA.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pbAPA.visibility = View.GONE
    }
}
