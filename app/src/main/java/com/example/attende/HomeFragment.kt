package com.example.attende

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import com.example.attende.database.AppDatabase
import com.example.attende.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    lateinit var appDB: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.recyclerView
        val adapter = Adapter(requireContext())
        recyclerView.adapter = adapter
        appDB = AppDatabase.getDatabase(requireContext())





        try {
            //Read From database
            lifecycle.coroutineScope.launch {
                //List changes are picked up by flow and collect is called to submit the list to update ui
                appDB.AttendanceDao().getAll().collect() {
                    adapter.submitList(it)
                }
            }
        } catch (e: java.lang.Exception) {
            Log.d("Crashed", e.toString())

        }

    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}