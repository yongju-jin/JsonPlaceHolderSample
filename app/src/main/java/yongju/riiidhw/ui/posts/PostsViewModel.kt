package yongju.riiidhw.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.ui.base.BaseViewModel
import yongju.riiidhw.ui.paging.PostsFactory
import yongju.riiidhw.ui.usecase.PostsItemUseCase

class PostsViewModel(
    private val dataManager: DataManager,
    postsItemUseCase: PostsItemUseCase
): BaseViewModel(), PostsItemUseCase by postsItemUseCase {
    private val _noResult = MutableLiveData<Boolean>()
    val noResult: LiveData<Boolean>
        get() = _noResult

    val mainObservable = RxPagedListBuilder(
        PostsFactory(
            compositeDisposable,
            dataManager,
            _noResult
        ),
        PagedList.Config.Builder()
            .setInitialLoadSizeHint(PostsFactory.FIRST_LOAD_SIZE)
            .setPageSize(PostsFactory.LOAD_MORE_SIZE)
            .setEnablePlaceholders(false)
            .build()

    ).buildObservable()
}