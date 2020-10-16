package com.example.android.criminalintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.android.criminalintent.databinding.FragmentCrimeBinding
import java.text.SimpleDateFormat
import java.util.*


class CrimeFragment : Fragment() {

    lateinit var mCrimeObj:Crime
    private lateinit var binding:FragmentCrimeBinding
    val TAG = "CrimeFragment"
    private val REQUEST_CODE = 0

    companion object{
        fun newInstance(crimeId:UUID):Fragment{
            Log.i("CrimeFragment","New Instance of Fragment created")
            val agrs = Bundle()
            agrs.putSerializable("ARG_CRIME_ID",crimeId)
            var fragment = CrimeFragment()
            fragment.arguments = agrs
            return fragment
        }
    }

    override fun onPause() {
        super.onPause()
        CrimeLab.updateCrime(mCrimeObj)
    }

    //This is called when the fragment return from some other fragment "Target Fragment"
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!=Activity.RESULT_OK)
            return
        if(requestCode==REQUEST_CODE){
            //Get the updated date from the intent
            var date :Date = data?.getSerializableExtra("EXTRA_DATE") as Date
            mCrimeObj.mDate = date

            updateButton()
        }
    }

    private fun updateButton() {
        binding.crimeDate.text = (formatDate(mCrimeObj.mDate))
    }

    fun formatDate(date: Date): String {
        var  simpleDateFormat : SimpleDateFormat = SimpleDateFormat("EEE, d MMMM yyyy")
        return simpleDateFormat.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime , menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.item_delete ->{
                CrimeLab.deleteCrime(getUid())
                activity?.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // THis is used to make the istance of the fragement itself
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        var crimeId = getUid()
        CrimeLab.context1 = this!!.context!!
        mCrimeObj = CrimeLab.getCrime(crimeId)!!
    }

    private fun getUid() = arguments?.getSerializable("ARG_CRIME_ID") as UUID

    //THis is used to imflate the layout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //THe "container" is the parent ViewGroup and fragment needs to know about the fragment
        //"false" is passed to know whether you want to be fragment to been seen now and we passed false
        // we will seperately tell do it in the code.
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crime,container,false)

        binding.crimeTitle.addTextChangedListener(object :TextWatcher{


            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               Log.i("CrimeFragment","addTextChangedListener called")
                if(p0?.count()!=0)
               mCrimeObj.mTitle = p0.toString()
            }
        })


        binding.crimeTitleTextview.text = mCrimeObj?.mTitle
        binding.crimeSolved.isChecked = mCrimeObj?.mSolved ?: true
        updateButton()

        binding.crimeDate.setOnClickListener{
            var fm : FragmentManager? = fragmentManager
            if (fm != null) {

                //THe datePickerFragment should know which date to display in calender so we are
                //pasing the date in the "bundle" which is created after activiey but before
                //Fragment is created in this case we are usign "newInstance" method to pass the date
                // and this method will make a bundel and store it in so when the fragments need the date
                //it can ask the bunle to set the calender to relevent date
                var datePickerFragment = DatePickerFragment.newInstance(mCrimeObj?.mDate)

                //We also need to get the date from the DialogFragment so we set "this" fragment to be the
                //DialogFragment Target so whenever the dialog fragment dies or is called back it will call
                //"onActivitResult" on this fragment
                //Request code is used to see from which fragment does it return
                //here "REquestCode" for the dialog frament is set for zero
                datePickerFragment.setTargetFragment(this, REQUEST_CODE)

                datePickerFragment.show(fm,"This")
            }
        }

        binding.crimeSolved.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {

            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                mCrimeObj.mSolved = p1
            }

        })



        return binding.root


    }

}