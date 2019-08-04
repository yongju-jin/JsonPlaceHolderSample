package yongju.riiidhw.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.base.BaseViewModel

class MainViewModel: BaseViewModel() {
    private val _editLiveData = MutableLiveData<TypiCodeModel>()
    val editLiveData: LiveData<TypiCodeModel>
        get() = _editLiveData

    private val _detailLiveData = MutableLiveData<TypiCodeModel>()
    val detailLiveData: LiveData<TypiCodeModel>
        get() = _detailLiveData

    private val _postsRefreshLiveData = MutableLiveData<Unit>()
    val postsRefreshLiveData: LiveData<Unit>
        get() = _postsRefreshLiveData

    private val _detailRefreshLiveData = MutableLiveData<Unit>()
    val detailRefreshLiveData: LiveData<Unit>
        get() = _detailRefreshLiveData

    fun moveToDetails(item: TypiCodeModel) {
        _detailLiveData.postValue(item)
    }

    fun setPostsRefresh() {
        _postsRefreshLiveData.postValue(Unit)
    }

    fun setDetailRefresh() {
        _detailRefreshLiveData.postValue(Unit)
    }

    fun editPost(typiCode: TypiCodeModel) {
        _editLiveData.postValue(typiCode)
    }
}