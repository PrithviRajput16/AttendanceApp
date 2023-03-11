package com.example.attende

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.attende.databinding.FragmentCalendarBinding
import com.example.attende.viewModels.CalendarViewModel
import kotlinx.coroutines.launch

class CalendarFragment : Fragment() {

    lateinit var viewModel: CalendarViewModel
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CalendarViewModel::class.java]

        val calendar = binding.calendar
        //recyclerView
        val recyclerView = binding.calendarRecyclerView
        val adapter = CalendarAdapter()
        recyclerView.adapter = adapter
        calendar.setOnDateChangeListener { _, _, month, dayOfMonth ->

            try {
                lifecycle.coroutineScope.launch {
                    viewModel.getAttendedName(dayOfMonth.toString(), (month + 1).toString())
                        .collect {
                            adapter.submitList(it)
                            binding.headerTitle.isVisible = true
                        }
                }
                /*
                GlobalScope.launch(Dispatchers.IO){
                    val list = viewModel.getAttendedName(dayOfMonth.toString(), (month +1).toString())
                    //val list = appDB.AttendanceDao().getAttendedName(dayOfMonth.toString(), (month +1).toString())
                    withContext(Dispatchers.Main){

                        adapter.submitList(list)
                        binding.headerTitle.isVisible = true

                    }
                }
                */

            } catch (_: java.lang.Exception) {

            }


        }


    }


}