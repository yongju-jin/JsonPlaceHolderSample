package yongju.riiidhw.ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment: Fragment() {
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        compositeDisposable.clear()
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