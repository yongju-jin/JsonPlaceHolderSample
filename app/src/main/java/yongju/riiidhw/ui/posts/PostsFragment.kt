package yongju.riiidhw.ui.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxkotlin.plusAssign
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManagerImpl
import yongju.riiidhw.databinding.FragmentPostsBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.MainViewModel
import yongju.riiidhw.ui.adapter.PostsAdapter
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory
import yongju.riiidhw.ui.custom.VerticalMarginItemDecoration
import yongju.riiidhw.ui.ext.toDP
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

    private lateinit var fragmentMainBinding: FragmentPostsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = DataBindingUtil.inflate<FragmentPostsBinding>(
            inflater,
            R.layout.fragment_posts, container, false).apply {

            rvPosts.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = PostsAdapter(postsViewModel).apply {
                    compositeDisposable += postsViewModel.mainObservable.
                            subscribe({
                                submitList(it)
                            }, {
                                Log.e("mainFragment", it.toString(), it)
                            })
                }
                addItemDecoration(
                    VerticalMarginItemDecoration(context.toDP(8))
                )
            }
        }

        mainViewModel?.postsRefreshLiveData?.observe(viewLifecycleOwner, Observer {
            refresh()
        })

        return fragmentMainBinding.root
    }

    private fun refresh() {
        with(fragmentMainBinding.rvPosts) {
            (adapter as? PostsAdapter)?.currentList?.dataSource?.invalidate()
        }
    }

    override fun onClickItem(item: TypiCodeModel) {
        mainViewModel?.moveToDetails(item)
    }

    companion object {
        fun createMainFragment(): PostsFragment {
            return PostsFragment()
        }
    }
}