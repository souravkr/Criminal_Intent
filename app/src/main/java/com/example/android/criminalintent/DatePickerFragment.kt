package com.example.android.criminalintent


import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*


/*class DatePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog(context)
            .setTitle(R.string.date_picker_title).
    }
}*/

class DatePickerFragment  : DialogFragment() {

    companion object {
        //add the previos date in the bundle and make the instance of this class
        fun newInstance(date:Date):DatePickerFragment {
            val args = Bundle()
            args.putSerializable("ARG_DATE",date)
            var fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //We get the old date set from the bundle
        var date :Date= arguments?.getSerializable("ARG_DATE") as Date

        //We set the old date to the calender
        val cal: Calendar = Calendar.getInstance()
        cal.time = date


        val dpd = DatePickerDialog(activity , DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            //Make a "date Object" of the "date" selected in the calander
            date = GregorianCalendar(year,monthOfYear,dayOfMonth).time


            sendResult(Activity.RESULT_OK,date)

        }, cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

        //Display the datePicker
        dpd.show()

        return dpd
    }

    fun sendResult(resultCode:Int,date:Date){
        if(targetFragment==null){
            return;
        }

        var intent = Intent()
        intent.putExtra("EXTRA_DATE",date)
        targetFragment!!.onActivityResult(targetRequestCode,resultCode,intent)
    }

}