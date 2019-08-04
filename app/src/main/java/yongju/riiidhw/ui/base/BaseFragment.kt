package yongju.riiidhw.ui.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment: Fragment() {
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun finish() {
        if (isAdded) {
            activity?.let {
                it.supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }
    }
}