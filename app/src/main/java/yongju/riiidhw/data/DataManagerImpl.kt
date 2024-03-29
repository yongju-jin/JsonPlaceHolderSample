package yongju.riiidhw.data

import io.reactivex.Completable
import io.reactivex.Single
import yongju.riiidhw.data.remote.ApiInterface
import yongju.riiidhw.data.remote.ApiProvider
import yongju.riiidhw.model.TypiCodeCommentModel
import yongju.riiidhw.model.TypiCodeModel

class DataManagerImpl(
    private val apiInterface: ApiInterface
): DataManager {
    override fun getPosts(_start: Int, _limit: Int): Single<List<TypiCodeModel>> {
        return apiInterface.getPosts(_start, _limit)
    }

    override fun getPost(postId: Long): Single<TypiCodeModel> {
        return apiInterface.getPost(postId)
    }

    override fun getComments(postId: Long): Single<List<TypiCodeCommentModel>> {
        return apiInterface.getComments(postId)
    }

    override fun deletePost(postId: Long): Completable {
        return apiInterface.deletePost(postId)
    }

    override fun updatePost(postId: Long, body: Map<String, String>): Completable {
        return apiInterface.updatePost(postId, body)
    }

    companion object {
        val INSTANCE by lazy {
            DataManagerImpl(
                ApiProvider.INSTANCE
            )
        }
    }
}