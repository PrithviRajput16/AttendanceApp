package com.example.attende

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.PopupMenu.OnMenuItemClickListener
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.attende.database.AppDatabase
import com.example.attende.database.Attendance
import com.example.attende.database.Attendance2
import com.example.attende.databinding.ItemViewBinding
import kotlinx.coroutines.*
import java.util.*
import java.util.Calendar.*

class Adapter(Context: Context) : ListAdapter<Attendance, Adapter.ViewHolder>(DiffCallback), OnMenuItemClickListener {

    private val mContext = Context

    class ViewHolder(private val binding: ItemViewBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private val n = "String"

        @OptIn(DelicateCoroutinesApi::class)
        fun bind(Attendance: Attendance) {


            val add = binding.add
            val not = binding.not


            binding.subject.setOnClickListener {
                val intent = Intent(context, Display::class.java)
                intent.putExtra(n, Attendance.sName)
                context.startActivity(intent)
            }
            binding.subject.text = Attendance.sName
            binding.subject.setOnLongClickListener {
                val popupMenu = PopupMenu(context,binding.subject)

                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                    if(it.itemId == R.id.delete){
                        //To be called from Fragment by implementing interface
                        GlobalScope.launch(Dispatchers.IO){
                            AppDatabase.getDatabase(context).AttendanceDao().delete(binding.subject.text.toString())
                            AppDatabase.getDatabase(context).AttendanceDao().delete2(binding.subject.text.toString())
                        }
                    }
                    true
                })
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.show()
                true
            }
            val cal = Calendar.getInstance()

            val month = cal.get(MONTH).toInt() + 1




            binding.not.setOnClickListener {
                val data = Attendance2(
                    null, Attendance.sName, cal.get(DATE).toString(), month.toString(), false
                )

                GlobalScope.launch(Dispatchers.IO) {
                    if (AppDatabase.getDatabase(context).AttendanceDao().getDay(
                            cal.get(DATE).toString(),
                            month.toString(),
                            Attendance.sName,
                        ) == null
                    ) {
                        AppDatabase.getDatabase(context).AttendanceDao().insert(data)
                        withContext(Dispatchers.Main) {

                            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show()

                        }
                    }

                }


            }


            binding.add.setOnClickListener {
                val data = Attendance2(
                    null, Attendance.sName, cal.get(DATE).toString(), month.toString(), true
                )
                GlobalScope.launch(Dispatchers.IO) {
                    if (AppDatabase.getDatabase(context).AttendanceDao().getDay(
                            cal.get(DATE).toString(),
                            month.toString(),
                            Attendance.sName,
                        ) == null
                    ) {
                        AppDatabase.getDatabase(context).AttendanceDao().insert(data)
                        withContext(Dispatchers.Main) {

                            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()


                        }


                    } else {
                        withContext(Dispatchers.Main) {

                            Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context)), mContext)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Attendance>() {
            override fun areItemsTheSame(oldItem: Attendance, newItem: Attendance): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Attendance, newItem: Attendance): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }


}