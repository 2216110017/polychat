package com.example.polychat24

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.polychat24.databinding.ActivityMainBinding
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var userList: ArrayList<User>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)    //메뉴 액션바

        userList = ArrayList()  //리스트 초기화

        adapter = UserAdapter(this, userList)
        binding.userRecycelrView.layoutManager = LinearLayoutManager(this)
        binding.userRecycelrView.adapter = adapter

        // LoginDB.json 파일에서 사용자 정보 읽어오기
        try {
            val jsonFile = application.assets.open("LoginDB.json").bufferedReader().use {
                it.readText()
            }
            val jsonArray = JSONArray(jsonFile)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val currentUser = User(
                    jsonObject.getInt("userID"),
                    jsonObject.getString("stuNum"),
                    jsonObject.getString("stuName")
                )
                userList.add(currentUser)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu) //menu.xml 연결
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {   //menu를 선택했을 때 실행되는 함수
        if(item.itemId == R.id.log_out) {   //log_out메뉴를 선택하면
            val intent = Intent(this@MainActivity, LogInActivity::class.java)
            startActivity(intent)   //startActivity로 이동
            finish()
            return true
        }
        return true
    }
}



