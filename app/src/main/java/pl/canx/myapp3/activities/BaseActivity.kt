package pl.canx.myapp3.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private var disposables = mutableListOf<Disposable>()

    protected fun addDisposable(disposable: Disposable){
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.filter { !it.isDisposed }.forEach { it.dispose() }
        disposables.clear()
    }
}