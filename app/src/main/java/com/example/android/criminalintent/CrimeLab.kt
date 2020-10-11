package com.example.android.criminalintent

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.android.criminalintent.database.CrimeBaseHelper
import com.example.android.criminalintent.database.CrimeDbSchema
import com.example.android.criminalintent.database.CrimeDbSchema.CrimeTable
import java.util.*
import kotlin.collections.ArrayList

object CrimeLab {

     lateinit var mCrimes : ArrayList<Crime>
     lateinit var context1: Context
     lateinit var sqLiteDatabase : SQLiteDatabase

    init {

      //
      mCrimes = ArrayList()
    }

    fun buidDatabase(context: Context){
        Log.i("CrimeLab","DataBase created")
        sqLiteDatabase = CrimeBaseHelper(context.applicationContext).writableDatabase
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

    fun getContentValue(crime :Crime) :ContentValues{
        var Cvalue = ContentValues()
        Cvalue.put(CrimeTable.Cols.UUID,crime.mId.toString())
        Cvalue.put(CrimeTable.Cols.TITlE,crime.mTitle)
        Cvalue.put(CrimeTable.Cols.DATE,crime.mDate.time)
        Cvalue.put(CrimeTable.Cols.SOLVED,crime.mSolved)
        return Cvalue
    }
}