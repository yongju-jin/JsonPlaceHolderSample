package yongju.riiidhw.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.model.TypiCodeModel

class PostsDataSource(private val compositeDisposable: CompositeDisposable,
                      private val dataManager: DataManager,
                      private val noResult: MutableLiveData<Boolean>): PositionalDataSource<TypiCodeModel>() {
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<TypiCodeModel>
    ) {
        val startIndex = 0
        val response = dataManager.getPosts(startIndex, params.requestedLoadSize)
            .blockingGet()

        noResult.postValue(response.isEmpty())
        callback.onResult(response, startIndex, response.size)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TypiCodeModel>) {
        compositeDisposable += dataManager.getPosts(params.startPosition, params.loadSize)
            .subscribe({
                callback.onResult(it)
            }, {
                noResult.value = true
            })
    }
}