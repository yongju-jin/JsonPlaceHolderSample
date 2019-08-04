package yongju.riiidhw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import yongju.riiidhw.R
import yongju.riiidhw.ui.base.BaseActivity
import yongju.riiidhw.ui.base.viewModelFactory
import yongju.riiidhw.ui.detail.DetailFragment
import yongju.riiidhw.ui.edit.EditFragment
import yongju.riiidhw.ui.posts.PostsFragment

class MainActivity: BaseActivity() {

    private val mainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory {
            MainViewModel()
        }).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.detailLiveData.observe(this, Observer {
            addBackStackFragment(
                DetailFragment.createDetailFragment(it)
            )
        })

        mainViewModel.editLiveData.observe(this, Observer {
            addBackStackFragment(
                EditFragment.createEditFragment(it)
            )
        })

        addBackStackFragment(PostsFragment.createMainFragment())
    }

    private fun addBackStackFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_main_contents, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }
}