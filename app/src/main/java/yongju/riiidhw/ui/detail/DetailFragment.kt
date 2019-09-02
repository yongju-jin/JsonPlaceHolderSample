package yongju.riiidhw.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManagerImpl
import yongju.riiidhw.databinding.FragmentDetailBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.MainViewModel
import yongju.riiidhw.ui.adapter.CommentsAdapter
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory
import yongju.riiidhw.ui.custom.VerticalMarginItemDecoration
import yongju.riiidhw.ui.ext.toDP
import yongju.riiidhw.ui.ext.toast

class DetailFragment : BaseFragment(), DetailUseCase {
    private val typiCode by lazy {
        arguments?.getParcelable<TypiCodeModel>(TYPI_CODE)
    }

    private val mainViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(it, viewModelFactory {
                MainViewModel()
            }).get(MainViewModel::class.java)
        }
    }

    private val detailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory {
            DetailViewModel(
                this@DetailFragment,
                DataManagerImpl.INSTANCE
            )
        }).get(DetailViewModel::class.java)
    }

    private val postId by lazy {
        typiCode?.id ?: -1
    }

    private lateinit var fragmentDetailBinding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailBinding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = detailViewModel

//            mainViewModel?.let {
//                it.detailRefreshSubject
//                    .subscribeOn(Schedulers.io())
//                    .subscribe({getPost()}) { e ->
//                        Log.e("editViewModel", e.toString(), e)
//                    }
//            }

            detailViewModel.errorMsg.observe(viewLifecycleOwner, Observer {
                context?.toast(getString(it))
            })

            rvDetailComments.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = CommentsAdapter().apply {
                    launch {
                        submitList(
                            detailViewModel.getCommentsByCoroutines(postId)
                        )
                    }
                }

                addItemDecoration(
                    VerticalMarginItemDecoration(context.toDP(6))
                )
            }

            launch {
                getPost()
            }
        }
        return fragmentDetailBinding.root
    }

    private suspend fun getPost() {
        detailViewModel.getPostByCoroutines(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_detail_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detail_edit -> {
                typiCode?.let {
                    mainViewModel?.editPost(it)
                }
                true
            }
            R.id.menu_detail_delete ->  {
                detailViewModel.deletePost(postId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSuccesDelete() {
        mainViewModel?.setPostsRefresh()
        finish()
    }

    companion object {
        const val TYPI_CODE = "typicode"

        fun createDetailFragment(typiCodeModel: TypiCodeModel): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TYPI_CODE, typiCodeModel)
                }
            }
        }
    }
}