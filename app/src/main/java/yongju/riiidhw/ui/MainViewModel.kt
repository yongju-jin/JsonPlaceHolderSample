package yongju.riiidhw.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.base.BaseViewModel

class MainViewModel: BaseViewModel() {
    private val _editLiveData = MutableLiveData<TypiCodeModel>()
    val editLiveData: LiveData<TypiCodeModel>
        get() = _editLiveData

    private val _detailLiveData = MutableLiveData<TypiCodeModel>()
    val detailLiveData: LiveData<TypiCodeModel>
        get() = _detailLiveData

    val postRefreshSubject = PublishSubject.create<Unit>().toSerialized()
    val detailRefreshSubject = PublishSubject.create<Unit>().toSerialized()

    fun moveToDetails(item: TypiCodeModel) {
        _detailLiveData.postValue(item)
    }

    fun setPostsRefresh() {
        postRefreshSubject.onNext(Unit)
    }

    fun setDetailRefresh() {
        detailRefreshSubject.onNext(Unit)
    }

    fun editPost(typiCode: TypiCodeModel) {
        _editLiveData.postValue(typiCode)
    }
}