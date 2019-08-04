package yongju.riiidhw.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.model.TypiCodeModel


class PostsFactory(
    private val compositeDisposable: CompositeDisposable,
    private val dataManager: DataManager,
    private val noResult: MutableLiveData<Boolean>
): DataSource.Factory<Int, TypiCodeModel>() {

    override fun create(): DataSource<Int, TypiCodeModel> {
        return PostsDataSource(
            compositeDisposable,
            dataManager,
            noResult
        )
    }

    companion object {
        const val FIRST_LOAD_SIZE = 30
        const val LOAD_MORE_SIZE = 24
    }
}