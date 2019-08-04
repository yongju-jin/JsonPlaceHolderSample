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

class DetailFragment : BaseFragment(), DetailUseCase {
    private val typiCode by lazy {
        arguments?.let {
            it.getParcelable<TypiCodeModel>(TYPI_CODE)
        }
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

            detailViewModel.post.observe(viewLifecycleOwner, Observer {
                model = it
            })

            rvDetailComments.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = CommentsAdapter().apply {
                    compositeDisposable += detailViewModel.getComments(postId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::submitList)
                        {
                            Log.e("detailViewModel", it.toString(), it)
                        }

                }

                addItemDecoration(
                    VerticalMarginItemDecoration(context.toDP(6))
                )
            }

            detailViewModel.getPost(postId)
        }
        return fragmentDetailBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail_actionbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_detail_delete ->  {
                detailViewModel.deletePost(postId)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSuccesDelete() {
        mainViewModel?.setRefresh()
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