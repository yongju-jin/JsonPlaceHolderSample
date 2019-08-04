package yongju.riiidhw.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.model.TypiCodeCommentModel
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.base.BaseViewModel

class DetailViewModel(private val detailUseCase: DetailUseCase,
                      private val dataManager: DataManager): BaseViewModel() {
    private val _post = MutableLiveData<TypiCodeModel>()
    val post: LiveData<TypiCodeModel>
        get() = _post

    fun getPost(postId: Long) {
        compositeDisposable += dataManager.getPost(postId)
            .observeOn(Schedulers.io())
            .subscribe(_post::postValue) {
                Log.e("detailViewModel", it.toString(), it)
            }
    }

    fun getComments(postId: Long): Single<List<TypiCodeCommentModel>> {
        return dataManager.getComments(postId).subscribeOn(Schedulers.io())
    }

    fun deletePost(postId: Long) {
        compositeDisposable += dataManager.deletePost(postId)
            .subscribeOn(Schedulers.io())
            .subscribe(detailUseCase::onSuccesDelete) {
                Log.e("mainViewModel", it.toString(), it)
            }
    }
}