package yongju.riiidhw.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.base.BaseViewModel

class DetailViewModel(private val dataManager: DataManager): BaseViewModel() {
    private val _post = MutableLiveData<TypiCodeModel>()
    val post: LiveData<TypiCodeModel>
        get() = _post

    fun getPost(postId: Long) {
        compositeDisposable += dataManager.getPost(postId)
            .observeOn(Schedulers.io())
            .subscribe(_post::postValue, {
                Log.e("detailViewModel", it.toString(), it)
            })
    }
}