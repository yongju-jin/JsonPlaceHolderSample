package yongju.riiidhw.ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseFragment: Fragment(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        compositeDisposable.clear()
        cancel()
        super.onDestroyView()
    }

    fun finish() {
        if (isAdded) {
            activity?.supportFragmentManager?.let {
                it.beginTransaction()?.remove(this)?.commit()
                it.popBackStack()
            }
        }
    }

    fun hideSoftInput() {
        if (isAdded) {
            activity?.let { activity ->
                val view = activity.currentFocus
                view?.let { v ->
                    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
    }
}