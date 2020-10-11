package com.example.android.criminalintent

import android.app.Application

class CustomApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        applicationContext?.let { CrimeLab.buidDatabase(it) }
    }
}