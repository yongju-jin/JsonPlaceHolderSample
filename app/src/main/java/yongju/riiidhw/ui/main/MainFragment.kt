package yongju.riiidhw.ui.main

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
import yongju.riiidhw.ui.adapter.main.MainAdapter
import yongju.riiidhw.ui.base.BaseFragment
import yongju.riiidhw.ui.base.viewModelFactory

class MainFragment: BaseFragment() {

    private val viewmodel by lazy {
        ViewModelProviders.of(this, viewModelFactory {
            MainViewModel(
                DataManagerImpl.INSTANCE
            )
        }).get(MainViewModel::class.java)
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
                adapter = MainAdapter().apply {
                    compositeDisposable += viewmodel.mainObservable.
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

    companion object {
        fun createMainFragment(): MainFragment {
            return MainFragment()
        }
    }
}