package com.example.android.criminalintent

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.android.criminalintent.database.CrimeBaseHelper
import com.example.android.criminalintent.database.CrimeCursorWrapper
import com.example.android.criminalintent.database.CrimeDbSchema
import com.example.android.criminalintent.database.CrimeDbSchema.CrimeTable
import java.util.*
import kotlin.collections.ArrayList

object CrimeLab {


     lateinit var context1: Context
     lateinit var mDatabase : SQLiteDatabase

    init {



    }

    fun buidDatabase(context: Context){
        Log.i("CrimeLab","DataBase created")
        mDatabase = CrimeBaseHelper(context.applicationContext).writableDatabase
    }

    fun getCrime(id : UUID):Crime?{

        return null
    }

    fun deleteCrime(id: UUID){
      /*  for(crime in mCrimes){
            if(crime.mId.equals(id)){
                mCrimes.remove(crime)
                break;
            }
        }*/

    }

    fun updateCrime(crime:Crime){
        var uuidString = crime.mId.toString()
        var contentValues = getContentValue(crime)
        mDatabase.update(CrimeTable.NAME,contentValues,CrimeTable.Cols.UUID + " = ?",
        uuidString.map { it.toString() }.toTypedArray())
    }

    fun getContentValue(crime :Crime) :ContentValues{
        var Cvalue = ContentValues()
        Cvalue.put(CrimeTable.Cols.UUID,crime.mId.toString())
        Cvalue.put(CrimeTable.Cols.TITlE,crime.mTitle)
        Cvalue.put(CrimeTable.Cols.DATE,crime.mDate.time)
        Cvalue.put(CrimeTable.Cols.SOLVED,crime.mSolved)
        return Cvalue
    }

    fun addCrime(crime: Crime) {
          var values: ContentValues  = getContentValue(crime)
          mDatabase.insert(CrimeTable.NAME,null,values)
    }

    fun queryCrime(wereClause:String,wereArg:Array<String>): CrimeCursorWrapper {
        var cursor = mDatabase.query(CrimeTable.NAME,
        null,
        wereClause,
        wereArg,
        null,
        null,
        null)
        return CrimeCursorWrapper(cursor)
    }
}