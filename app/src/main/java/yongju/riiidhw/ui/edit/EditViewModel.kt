package yongju.riiidhw.ui.edit

import android.util.Log
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.ui.base.BaseViewModel

class EditViewModel(private val editUseCase: EditUseCase,
                    private val dataManager: DataManager): BaseViewModel() {
    fun editPost(postId: Long, title: String, body: String) {
        compositeDisposable += dataManager.updatePost(postId, mapOf("title" to title, "body" to body))
            .subscribeOn(Schedulers.io())
            .subscribe(editUseCase::onSuccessEdit) {
                Log.e("editViewModel", it.toString(), it)
            }
    }
}
