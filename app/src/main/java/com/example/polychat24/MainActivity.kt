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



//-------------------------------------------------------------------------------------------
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.polychat24.databinding.ActivityMainBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//class MainActivity : AppCompatActivity() {
//
//    lateinit var binding: ActivityMainBinding
//    lateinit var adapter: UserAdapter
//
//    private lateinit var mAuth: FirebaseAuth
//    private lateinit var mDbRef: DatabaseReference
//
//    private lateinit var userList: ArrayList<User>  //데이터를 담을 리스트
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        mAuth = Firebase.auth   //인증 초기화
//        mDbRef = Firebase.database.reference    //DB 초기화
//        userList = ArrayList()  //리스트 초기화
//
//        adapter = UserAdapter(this, userList)   //UserAdapter에 context, userList 넘겨주기
//
//        binding.userRecycelrView.layoutManager = LinearLayoutManager(this)
//        binding.userRecycelrView.adapter = adapter
//
//        //DB에서 user 사용자 정보 가져오기
//        mDbRef.child("user").addValueEventListener(object: ValueEventListener {
//            //데이터가 변경될 때마다 실행됨
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //snapshot.children : 하위 데이터를 꺼냄
//                for(postSnapshot in snapshot.children){
//                    val currentUser = postSnapshot.getValue(User::class.java)   //currentUser = 실제 사용자정보
//
//                    //mAuth.currentUser?.uid = 현재 로그인한 사용자정보(uid)
//                    //나를 제외한 사용자만 화면에 출력함
//                    if(mAuth.currentUser?.uid != currentUser?.userID){
//                        userList.add(currentUser!!)
//                    }
//                }
//                adapter.notifyDataSetChanged()  //실제 화면에 적용
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                //실패 시 실행
//            }
//        })
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu) //menu.xml 연결
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {   //menu를 선택했을 때 실행되는 함수
//        if(item.itemId == R.id.log_out) {   //log_out메뉴를 선택하면
//            mAuth.signOut() //signOut 실행
//            val intent = Intent(this@MainActivity, LogInActivity::class.java)
//            startActivity(intent)   //startActivity로 이동
//            finish()
//            return true
//        }
//        return true
//    }
//}
