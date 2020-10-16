package com.example.android.criminalintent.database

import android.database.Cursor
import android.database.CursorWrapper
import com.example.android.criminalintent.Crime
import com.example.android.criminalintent.database.CrimeDbSchema.CrimeTable
import java.util.*

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getCrime() : Crime?{
        val  uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID))
        val  title = getString(getColumnIndex(CrimeTable.Cols.TITlE))
        val  date:Long = getLong(getColumnIndex(CrimeTable.Cols.DATE))
        val  isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED))

        var crime = Crime(UUID.fromString(uuidString))
        crime.mDate = Date(date)
        crime.mSolved = if(isSolved==0) true else false
        crime.mTitle= title

        return null
    }
}