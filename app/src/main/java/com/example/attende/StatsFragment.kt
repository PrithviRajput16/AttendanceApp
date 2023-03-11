package com.example.attende

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attende.database.AppDatabase
import com.example.attende.databinding.FragmentStatsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Calendar.*

class StatsFragment : Fragment() {

    lateinit var binding: FragmentStatsBinding
    lateinit var appDB: AppDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(LayoutInflater.from(requireContext()))
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cal = Calendar.getInstance()
        appDB = AppDatabase.getDatabase(requireContext())
        GlobalScope.launch(Dispatchers.IO) {
            val attended = appDB.AttendanceDao().getAllName((cal.get(MONTH).toInt()+1).toString()).toFloat()
            val total = appDB.AttendanceDao().getAllNameAttended((cal.get(MONTH).toInt()+1).toString()).toFloat()
            val percentage = total/attended*100
            withContext(Dispatchers.Main){

                val progressBar = binding.circularProgressBar
                progressBar.setProgressWithAnimation(percentage,1000)

                binding.percent.text = "${percentage.toInt()}"
                binding.text.text = "${(100f-percentage)} PERCENT ATTENDANCE MORE TO GO "
            }
        }
    }

}