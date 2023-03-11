package com.example.attende

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.attende.database.AppDatabase
import com.example.attende.database.Attendance
import com.example.attende.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var appDB: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)

        appDB = AppDatabase.getDatabase(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        nav(HomeFragment())

        binding.include.clear.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                appDB.AttendanceDao().deleteFromRecords()
                appDB.AttendanceDao().deleteFromAttende() }
        }

        binding.include.about.setOnClickListener{
            val intent = Intent(this, AboutPage::class.java)
            startActivity(intent)
        }

        binding.include.addSubject.setOnClickListener(View.OnClickListener {

            val inflater = LayoutInflater.from(this)
            val dialogLayout = inflater.inflate(R.layout.subject_name, null, false)


            val editText = dialogLayout.findViewById<EditText>(R.id.name)
            //Write in database
            //DialogBox to enter Subject
            try {
                val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this,R.style.DialogTheme)
                with(builder) {
                    setTitle("Enter The Name")
                    setPositiveButton("OK") { _, _ ->

                        writeInDatabase(editText)
                    }
                    setView(dialogLayout)
                    create()
                    show()
                }
            }catch (_: Throwable){

            }
        })
        binding.bottomNavigationView.menu.getItem(1).isChecked = true
        binding.bottomNavigationView.setOnItemSelectedListener() {
            when(it.itemId){
                R.id.home -> nav(HomeFragment())
                R.id.calendar -> nav(CalendarFragment())
                R.id.stats -> nav(StatsFragment())
            }
            true
        }


    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun writeInDatabase(view: EditText) {
        if (view.text.toString() != "") {
            val subject = Attendance(null, view.text.toString().trim().uppercase(), 0)
            GlobalScope.launch(Dispatchers.IO) { appDB.AttendanceDao().insert(subject) }
        } else {
            Toast.makeText(this, "FIELD CAN'T BE EMPTY", Toast.LENGTH_LONG).show()
        }
    }

    private fun nav(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}
