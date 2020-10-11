package com.example.android.criminalintent


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.criminalintent.databinding.FragmentCrimeListBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CrimeListFragment : Fragment() {

    lateinit var dataBinding : FragmentCrimeListBinding
    var mAdapter: CrimeAdapter? =null
    val TAG = "CrimeListFragment"
    var mSubtitleVisible = false


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list,menu)

        var menuItem = menu.findItem(R.id.show_subtitle)

        if(mSubtitleVisible){
            menuItem.setTitle(R.string.hide_subtitle)
        }
        else
            menuItem.setTitle(R.string.show_subtitle)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.new_crime -> {
                mSubtitleVisible  = !mSubtitleVisible
                activity?.invalidateOptionsMenu()

                var intent: Intent = createIntent()
                startActivity(intent)
                return true
            }
            R.id.show_subtitle->{
                mSubtitleVisible =!mSubtitleVisible
                activity?.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun createIntent(): Intent {
        var crime = Crime()
        CrimeLab.mCrimes.add(crime)
        var intent: Intent = Intent(context, CrimePagerActivity::class.java)
        intent.putExtra("EXTRA_CRIME_ID", crime.mId)
        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      //  context?.let { CrimeLab.buidDatabase(it) }

        Log.i(TAG,"CrimeListFragment Inflated")
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_crime_list,container,false)
        dataBinding.crimeRecyclerView.layoutManager = LinearLayoutManager(activity)

        dataBinding.intentButton.setOnClickListener{
            Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show()

            var intent = createIntent()
            startActivity(intent)
        }
        updateUI()
        return dataBinding.root
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    fun updateUI(){

      //  CrimeLab.context1 = this.context!!
       var crimes = CrimeLab.mCrimes

        dataBinding.createNewButton.visibility = if(crimes.size == 0) View.VISIBLE else View.GONE

        if(mAdapter == null){
            mAdapter = CrimeAdapter(crimes)
            dataBinding.crimeRecyclerView.adapter =  mAdapter
        }
        else{
            mAdapter?.notifyDataSetChanged()
        }
        updateSubtitle()

   }

    fun updateSubtitle(){
        var size = CrimeLab.mCrimes.size
        var sub = getString(R.string.subtitle_format,size)
        if(!mSubtitleVisible){
            sub = ""
        }
        var activity1 = activity as AppCompatActivity
        activity1.supportActionBar?.subtitle = sub
    }

    class CrimeHolder(itemView: View, var thisFragmentContext: Context) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        lateinit var titleView: TextView
        lateinit var dateView: TextView
        lateinit var imageView: ImageView
        lateinit var mCrimes :Crime

        init{
            titleView = itemView.findViewById(R.id.crime_title)
            dateView = itemView.findViewById(R.id.crime_date)
            imageView = itemView.findViewById(R.id.crime_solved)
            itemView.setOnClickListener(this)

        }

        fun bind(crime:Crime){
            mCrimes  = crime
            var fomatedDate = formatDate(mCrimes.mDate)
            dateView.setText(fomatedDate)
            titleView.setText(mCrimes.mTitle.toString())
            imageView.visibility = if(mCrimes.mSolved) View.VISIBLE else View.GONE
        }

        fun formatDate(date: Date): String {
            var  simpleDateFormat : SimpleDateFormat = SimpleDateFormat("EEE, d MMMM yyyy")
            return simpleDateFormat.format(date)
        }

        override fun onClick(p0: View?) {

            var intent = Intent(thisFragmentContext,CrimePagerActivity::class.java)
            intent.putExtra("EXTRA_CRIME_ID",mCrimes.mId)
            thisFragmentContext.startActivity(intent)
        }
    }

    public class CrimeAdapter(var mCrimes :ArrayList<Crime>): RecyclerView.Adapter<CrimeListFragment.CrimeHolder>(){
        val TAG = "CrimeListFragment"

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListFragment.CrimeHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime,parent,false)
            return CrimeListFragment.CrimeHolder(view, parent.context)
        }

        override fun getItemCount(): Int {
            return mCrimes.size
        }


        //Always make this function to do less work  possible
        //as this will be called agian and again
        override fun onBindViewHolder(holder: CrimeListFragment.CrimeHolder, position: Int) {
             val crime:Crime = mCrimes[position]
             holder.bind(crime)

        }


    }
}
