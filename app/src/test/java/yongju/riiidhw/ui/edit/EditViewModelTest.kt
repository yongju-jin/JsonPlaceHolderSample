package yongju.riiidhw.ui.edit

import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import yongju.riiidhw.data.DataManager

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class EditViewModelTest {

    @Mock
    lateinit var dataManager: DataManager
    @Mock
    lateinit var editUseCase: EditUseCase

    private val testScheduler = Schedulers.trampoline()

    private val editViewModel by lazy {
        EditViewModel(editUseCase, dataManager)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }
    }

    @Test
    fun editPost_success() {
        val postId = 1L
        val title = "test_title"
        val body = "test_body"

        `when`(dataManager.updatePost(postId, mapOf("title" to title, "body" to body)))
            .thenReturn(Completable.complete())

        editViewModel.editPost(postId, title, body)

        verify(editUseCase, times(1)).onSuccessEdit()
        verify(editUseCase, never()).onFailEdit()
    }

    @Test
    fun editPost_fail() {
        val postId = 1L
        val title = "test_title"
        val body = "test_body"

        `when`(dataManager.updatePost(postId, mapOf("title" to title, "body" to body)))
            .thenReturn(Completable.error(Exception()))

        editViewModel.editPost(postId, title, body)

        verify(editUseCase, times(1)).onFailEdit()
    }
}