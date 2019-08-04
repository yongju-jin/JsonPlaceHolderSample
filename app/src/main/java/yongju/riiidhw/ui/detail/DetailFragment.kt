package yongju.riiidhw.ui.detail

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManagerImpl
import yongju.riiidhw.databinding.FragmentDetailBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.adapter.CommentsAdapter
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory
import yongju.riiidhw.ui.custom.VerticalMarginItemDecoration
import yongju.riiidhw.ui.ext.toDP

class DetailFragment : BaseFragment() {
    private val typiCode by lazy {
        arguments?.let {
            it.getParcelable<TypiCodeModel>(TYPI_CODE)
        }
    }

    private val detailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory {
            DetailViewModel(
                DataManagerImpl.INSTANCE
            )
        }).get(DetailViewModel::class.java)
    }

    private lateinit var fragmentDetailBinding: FragmentDetailBinding
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
            val postId = typiCode?.id ?: -1

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