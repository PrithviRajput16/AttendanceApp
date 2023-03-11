package com.example.attende

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.attende.database.AppDatabase
import com.example.attende.databinding.ActivityDisplayBinding
import kotlinx.coroutines.*

class Display : AppCompatActivity() {
    private lateinit var data: List<String>
    private lateinit var binding: ActivityDisplayBinding
    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appDB = AppDatabase.getDatabase(this)
        var percentage: Float = 0.0F
        GlobalScope.launch(Dispatchers.IO) {
            val int = appDB.AttendanceDao().getSub(intent.extras?.getString("String").toString()).toFloat()
            val int2 = appDB.AttendanceDao().getAttended(intent.extras?.getString("String").toString()).toFloat()
            percentage = (int2/int*100)
            binding.textView1.text = "You have attended ${int2.toInt()} Classes"
            withContext(Dispatchers.Main){

                val progressBar = binding.circularProgressBar
                progressBar.setProgressWithAnimation(percentage,1000)

            }

        }



    }
    fun anim(percentage: Float){

    }
}