package com.example.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import 	androidx.viewpager2.widget.ViewPager2
import org.jetbrains.annotations.NotNull
import java.util.*
import kotlin.collections.ArrayList

class CrimePagerActivity: AppCompatActivity() {

    lateinit var viewPager:ViewPager
    lateinit var mCrimes: ArrayList<Crime>
     var mAdapter: ViewPagerAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)


        var uid :UUID = intent.getSerializableExtra("EXTRA_CRIME_ID") as UUID
        viewPager = findViewById(R.id.crime_view_pager) as ViewPager
        mCrimes = CrimeLab.mCrimes

        
        var fragmentManager1:FragmentManager = supportFragmentManager

        if(mAdapter == null){
            Log.i("CrimePagerActivity","First adapter called")
            mAdapter = ViewPagerAdapter(fragmentManager1,mCrimes)
            viewPager.adapter = mAdapter
        }
        else{
            Log.i("CrimePagerActivity","OnNotifyCalled")
            mAdapter?.notifyDataSetChanged()
        }

        for(i in (0..mCrimes.size-1)){
            if(mCrimes[i].mId.equals(uid)) {
                viewPager.currentItem = i
                break;
            }
         }

        }


    class ViewPagerAdapter (var fm:FragmentManager,var mCrimes:ArrayList<Crime>):FragmentStatePagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            var mCrime :Crime = mCrimes[position]
            return CrimeFragment.newInstance(mCrime.mId)
        }

        override fun getCount(): Int {
           return mCrimes.size
        }
    }
}
