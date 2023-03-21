package com.example.ptl_fbase


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserHandle
import com.example.ptl_fbase.adapter.ViewPagerAdapter
import com.example.ptl_fbase.databinding.ActivityMainBinding
import com.example.ptl_fbase.fragment.HomeFragment
import com.example.ptl_fbase.fragment.UserFragment


class MainActivity : AppCompatActivity() {


    lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    setupTab()
    }

    private fun setupTab() {
        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(),"Home")
        adapter.addFragment(UserFragment(),"User")
        binding.viewPager.adapter=adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.ic_home)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.ic_persons)
    }


}