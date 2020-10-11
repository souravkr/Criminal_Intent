package com.example.android.criminalintent.database


import java.util.*

class CrimeDbSchema {

    object CrimeTable {

        val NAME: String = "crimes"

        class Cols {
            companion object {
                val UUID: String = "uuid"
                val TITlE: String = "title"
                val DATE: String = "date"
                val SOLVED: String = "solved"
            }
        }

    }

}