package com.example.android.criminalintent.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.criminalintent.database.CrimeDbSchema.CrimeTable

class CrimeBaseHelper(context: Context) :SQLiteOpenHelper(context,"crimeBase.db",null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table "+CrimeTable.NAME +"( "+
        "_id integer primary key autoincrement, "+
        CrimeTable.Cols.UUID+", "+
        CrimeTable.Cols.TITlE+", "+
        CrimeTable.Cols.DATE+", "+
        CrimeTable.Cols.SOLVED + " )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}