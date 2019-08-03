package yongju.riiidhw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import yongju.riiidhw.R
import yongju.riiidhw.ui.base.BaseActivity
import yongju.riiidhw.ui.main.MainFragment

class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBackStackFragment(MainFragment.createMainFragment())
    }

    private fun addBackStackFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_main_contents, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }
}