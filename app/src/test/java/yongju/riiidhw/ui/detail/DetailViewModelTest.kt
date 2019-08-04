package yongju.riiidhw.ui.detail

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManager
import yongju.riiidhw.model.TypiCodeModel

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class DetailViewModelTest {

    @Mock
    lateinit var dataManager: DataManager
    @Mock
    lateinit var detailUseCase: DetailUseCase

    private val testScheduler = Schedulers.trampoline()
    private val detailViewModel by lazy {
        DetailViewModel(detailUseCase, dataManager)
    }
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }
    }

    @Test
    fun getPost_success() {
        val postId = 1L
        val typiCodeModel = TypiCodeModel(
            id = 1,
            userId = 1,
            title = "title",
            body = "body"
        )

        `when`(dataManager.getPost(postId)).thenReturn(
            Single.just(typiCodeModel)
        )

        detailViewModel.getPost(postId)

        assertEquals(typiCodeModel, detailViewModel.post.value)
    }

    @Test
    fun getPost_fail() {
        val postId = 1L
        `when`(dataManager.getPost(postId)).thenReturn(
            Single.error(Exception())
        )

        detailViewModel.getPost(postId)

        assertEquals(R.string.fail_get_post, detailViewModel.errorMsg.value)
    }

    @Test
    fun deletePost_success() {
        val postId = 1L

        `when`(dataManager.deletePost(postId)).thenReturn(
            Completable.complete()
        )

        detailViewModel.deletePost(postId)

        verify(detailUseCase, times(1)).onSuccesDelete()
    }

    @Test
    fun deletePost_fail() {
        val postId = 1L

        `when`(dataManager.deletePost(postId)).thenReturn(
            Completable.error(Exception())
        )

        detailViewModel.deletePost(postId)

        assertEquals(R.string.fail_delete_post, detailViewModel.errorMsg.value)
    }
}