package yongju.riiidhw.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.reactivex.schedulers.Schedulers
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManagerImpl
import yongju.riiidhw.databinding.FragmentDetailBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory

class DetailFragment: BaseFragment() {

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
            detailViewModel.post.observe(viewLifecycleOwner, Observer {
                model = it
            })

            rvDetailComments.apply {

            }

            typiCode?.let {
                detailViewModel.getPost(it.id)
            }

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