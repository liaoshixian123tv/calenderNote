package com.example.calendernote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendernote.databinding.ActivityMainBinding
import com.example.calendernote.event.ActionEvent
import com.example.calendernote.model.NoteDetail
import com.example.calendernote.view.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var addBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var noteAdapter:NoteAdapter
    private lateinit var noteList: ArrayList<NoteDetail>


    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContentView(R.layout.activity_main)
        noteList = ArrayList()
        addBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)
        noteAdapter = NoteAdapter(this, noteList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = noteAdapter
    }

    private fun addNote() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_note,null)

        val noteName = v.findViewById<EditText>(R.id.noteNameEditText)
        val noteStart = v.findViewById<EditText>(R.id.noteDateStartEditText)
        val noteEnd = v.findViewById<EditText>(R.id.noteDateEndEditText)
        val noteLocation = v.findViewById<EditText>(R.id.noteLocationEditText)
        val noteDetail = v.findViewById<EditText>(R.id.noteDetailEditText)


        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("OK"){
                dialog,_ ->
            val name = noteName.text.toString()
            val startTime = noteStart.text.toString()
            val endTime = noteEnd.text.toString()
            val location = noteLocation.text.toString()
            val detail = noteDetail.text.toString()
            val tmpNote = NoteDetail(name,startTime,endTime,location,detail)
            noteList.add(tmpNote)
            Log.d(TAG,noteList.toString())
            noteAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Insert Note Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_ ->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

    override fun onResume() {
        super.onResume()
        //  註冊EventBus, 多次註冊會崩潰
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }




        addBtn.setOnClickListener {
            EventBus.getDefault().post(ActionEvent(Constant.InsertNote))
        }
    }

    override fun onStop() {
        super.onStop()
        // 取消註冊EventBus
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ActionEvent){
        Log.d(TAG,"我收到EVENT了! 代號: "+event.actionType)

        when(event.actionType){
            Constant.InsertNote->{
                addNote()
            }
            else->{
                Log.d(TAG,"KAKAKAKAKAKAKKAKAKA")
            }
        }
    }

}