package yongju.riiidhw.ui.edit

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import yongju.riiidhw.R
import yongju.riiidhw.data.DataManagerImpl
import yongju.riiidhw.databinding.FragmentEditBinding
import yongju.riiidhw.model.TypiCodeModel
import yongju.riiidhw.ui.MainViewModel
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory
import yongju.riiidhw.ui.ext.toast

class EditFragment: BaseFragment(), EditUseCase {
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

    private val editViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory {
            EditViewModel(
                this,
                DataManagerImpl.INSTANCE
            )
        }).get(EditViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private lateinit var fragmentEditBinding: FragmentEditBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditBinding = DataBindingUtil.inflate<FragmentEditBinding>(
            inflater,
            R.layout.fragment_edit,
            container,
            false
        ).apply {
            model = typiCode
        }

        return fragmentEditBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_edit_actionbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_edit_send -> {
                editViewModel.editPost(
                    typiCode?.id ?: -1,
                    fragmentEditBinding.etEditTitle.text.toString(),
                    fragmentEditBinding.etEditBody.text.toString()
                )
                hideSoftInput()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSuccessEdit() {
        mainViewModel?.setPostsRefresh()
        mainViewModel?.setDetailRefresh()
        finish()
    }

    override fun onFailEdit() {
        context?.let {
            it.toast(it.getString(R.string.failed_edit))
        }
    }

    companion object {
        const val TYPI_CODE = "typicode"

        fun createEditFragment(typiCodeModel: TypiCodeModel): EditFragment {
            return EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TYPI_CODE, typiCodeModel)
                }
            }
        }

    }
}