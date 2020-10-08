package com.example.android.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class  SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun CreateFragment():Fragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        var fm: FragmentManager = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = CreateFragment()

            //fm.beginTransaction return FragmentTransaction and then .add also retrun FragmentTransaction
            //that why we can chain it it is called "fluent interface"
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }


    }
}