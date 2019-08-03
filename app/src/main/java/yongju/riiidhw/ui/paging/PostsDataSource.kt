package yongju.riiidhw.ui.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
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
        val startIndex = params.requestedStartPosition
        val response = dataManager.getPosts(startIndex, params.requestedLoadSize)
            .blockingGet()

        Log.d("mainDataSource", "[loadInitial] thread: ${Thread.currentThread().name}")
        noResult.postValue(response.isEmpty())
        callback.onResult(response, startIndex, response.size)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TypiCodeModel>) {
        Log.d("mainDataSource", "[loadRange] thread - 1: ${Thread.currentThread().name}")
        compositeDisposable += dataManager.getPosts(params.startPosition, params.loadSize)
            .subscribe({
                Log.d("mainDataSource", "[loadRange] thread - 2: ${Thread.currentThread().name}")
                callback.onResult(it)
            }, {
                noResult.value = true
            })
    }
}