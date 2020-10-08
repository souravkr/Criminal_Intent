package com.example.android.criminalintent

import android.util.Log
import androidx.fragment.app.Fragment


class CrimeListActivity :SingleFragmentActivity() {

    val TAG ="CrimeListActivity"

    override fun CreateFragment(): Fragment {
        Log.i(TAG,"CrimeList Activity called")
        return  CrimeListFragment()
    }
}