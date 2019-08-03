package yongju.riiidhw.data

import io.reactivex.Single
import yongju.riiidhw.data.remote.ApiInterface
import yongju.riiidhw.data.remote.ApiProvider
import yongju.riiidhw.model.TypiCodeModel

class DataManagerImpl(
    private val apiInterface: ApiInterface
): DataManager {
    override fun getPosts(_start: Int, _limit: Int): Single<List<TypiCodeModel>> {
        return apiInterface.getPosts(_start, _limit)
    }

    companion object {
        val INSTANCE by lazy {
            DataManagerImpl(
                ApiProvider.INSTANCE
            )
        }
    }
}