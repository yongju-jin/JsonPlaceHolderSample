package yongju.riiidhw.ui.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxkotlin.plusAssign
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManagerImpl
import yongju.riiidhw.databinding.FragmentMainBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.MainViewModel
import yongju.riiidhw.ui.adapter.posts.PostsAdapter
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory
import yongju.riiidhw.ui.usecase.PostsItemUseCase

class PostsFragment: BaseFragment(), PostsItemUseCase {

    private val mainViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(it, viewModelFactory {
                MainViewModel()
            }).get(MainViewModel::class.java)
        }
    }

    private val postsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory {
            PostsViewModel(
                DataManagerImpl.INSTANCE,
                this
            )
        }).get(PostsViewModel::class.java)
    }
    private lateinit var fragmentMainBinding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main, container, false).apply {

            rvMain.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = PostsAdapter(postsViewModel).apply {
                    compositeDisposable += postsViewModel.mainObservable.
                            subscribe({
                                Log.d("mainFragment", "[mainObservable] submitList: $it")
                                Log.d("mainFragment", "[mainObservable] thread: ${Thread.currentThread().name}")
                                submitList(it)
                            }, {
                                Log.e("mainFragment", it.toString(), it)
                            })
                }
            }
        }

        return fragmentMainBinding.root
    }

    override fun onClickItem(item: TypiCodeModel) {
        Log.d("mainFragment", "[onClickItem] item: $item")
        mainViewModel?.moveToDetails(item)
    }

    companion object {
        fun createMainFragment(): PostsFragment {
            return PostsFragment()
        }
    }

}