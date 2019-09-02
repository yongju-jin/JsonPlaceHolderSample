package yongju.riiidhw.ui.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseActivity: AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Main){
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        cancel()
        super.onDestroy()
    }

}