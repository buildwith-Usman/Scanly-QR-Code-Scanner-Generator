package com.infinity.interactive.scanqr.generateqr.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.infinity.interactive.scanqr.generateqr.R
import com.infinity.interactive.scanqr.generateqr.databinding.DashboardActivityBinding

class DashboardActivity : AppCompatActivity() {

    private var mActivity: Activity? = null
    private var mContext: Context? = null


    //View Binding
    private lateinit var dashboardActivityBinding: DashboardActivityBinding

    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardActivityBinding = DashboardActivityBinding.inflate(layoutInflater)
        setContentView(dashboardActivityBinding.root)


        setupNavGraphWithBottomBar()



    }

    private fun setupNavGraphWithBottomBar() {

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragmnet) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(
            dashboardActivityBinding.navigationMenu,navController
        )

        dashboardActivityBinding.navigationMenu.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.nav_scan -> {

                    navController.navigate(R.id.QRScanFragment)
                    true
                }

                R.id.nav_generate -> {
                    navController.navigate(R.id.QRGenerateFragment)
                    true
                }

                R.id.nav_history -> {
                    navController.navigate(R.id.QRHistoryFragment)
                    true
                }

                R.id.nav_setting -> {
                    navController.navigate(R.id.QRSettingFragment)
                    true
                }
                else -> false
            }
        }
    }


    override fun onResume() {
        super.onResume()
//        Constants.selectFromMain = true
//        Constants.isSelectingFile = false
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun initVars() {
        mActivity = this@DashboardActivity
        mContext = (mActivity as DashboardActivity).applicationContext
    }


}

private class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentList = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}
