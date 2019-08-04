package yongju.riiidhw.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.base.BaseViewModel

class MainViewModel: BaseViewModel() {
    private val _detailLiveData = MutableLiveData<TypiCodeModel>()
    val detailLiveData: LiveData<TypiCodeModel>
        get() = _detailLiveData

    private val _refreshLiveData = MutableLiveData<Unit>()
    val refreshLiveData: LiveData<Unit>
        get() = _refreshLiveData

    fun moveToDetails(item: TypiCodeModel) {
        _detailLiveData.postValue(item)
    }

    fun setRefresh() {
        _refreshLiveData.postValue(Unit)
    }
}