package com.example.android.criminalintent

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

object CrimeLab {

     lateinit var mCrimes : ArrayList<Crime>
     lateinit var context1: Context

    init {
      mCrimes = ArrayList()
    }

    fun getCrime(id : UUID):Crime?{
        for(crime in mCrimes){
            if(crime.mId.equals(id)){
                return crime
            }        }
        return null
    }

    fun deleteCrime(id: UUID){
        for(crime in mCrimes){
            if(crime.mId.equals(id)){
                mCrimes.remove(crime)
                break;
            }
        }

    }
}