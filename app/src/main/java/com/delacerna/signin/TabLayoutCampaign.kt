package com.delacerna.signin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tab_layout_campaign.*

class TabLayoutCampaign : AppCompatActivity() {

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout_campaign)


        pagerAdapter = PagerAdapter(supportFragmentManager)

        pagerAdapter.addFragments(ProductFragment(),"Product")
        pagerAdapter.addFragments(FragmentSample(),"Company")

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)



    }
}
