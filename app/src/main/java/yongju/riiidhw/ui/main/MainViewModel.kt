package yongju.riiidhw.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.ui.base.BaseViewModel
import yongju.riiidhw.ui.paging.MainFactory

class MainViewModel(
    private val dataManager: DataManager
): BaseViewModel() {
    private val _noResult = MutableLiveData<Boolean>()
    val noResult: LiveData<Boolean>
        get() = _noResult

    val mainObservable = RxPagedListBuilder(
        MainFactory(
            compositeDisposable,
            dataManager,
            _noResult
        ),
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(MainFactory.FIRST_LOAD_SIZE)
            .setPageSize(MainFactory.LOAD_MORE_SIZE)
            .setEnablePlaceholders(false)
            .build()

    ).buildObservable()
}