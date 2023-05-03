package com.example.polychat24

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.polychat24.databinding.ActivityLogInBinding
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)    //화면 뷰 연결

        binding.loginBtn.setOnClickListener {
            val stuNum = binding.stuNumEdit.text.toString()
            val stuName = binding.stuNameEdit.text.toString()
            if (isLoginValid(stuNum, stuName)) {
                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //학번(stuNum), 이름(stuName) 비교 결과 도출
    private fun isLoginValid(stuNum: String, stuName: String): Boolean {
        val jsonString = readJSONFile() //json 파일 읽기
        val jsonArray = JSONArray(jsonString)   //JSONArray로 변환하여 검색

        for (i in 0 until jsonArray.length()) {     //사용자 검색
            val jsonObject = jsonArray.getJSONObject(i)
            val jsonStuNum = jsonObject.getString("stuNum")
            val jsonStuName = jsonObject.getString("stuName")
            if (stuNum == jsonStuNum && stuName == jsonStuName) {
                return true     //학번, 이름이 일치하면 true
            }
        }
//stuNum != null &&
        return false    //일치하는 데이터가 없으면 false
    }

    //json 읽기
    private fun readJSONFile(): String {
        val json: String?
        try {
            val inputStream: InputStream = assets.open("LoginDB.json")    //LoginDB.json을 불러옴
            json = inputStream.bufferedReader().use { it.readText() }   //LoginDB.json을 읽음
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
}

//-------------------------------------------------------------------------------------------------
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.polychat24.databinding.ActivityLogInBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase
//
//class LogInActivity : AppCompatActivity() {
//    lateinit var binding: ActivityLogInBinding
//    lateinit var mAuth: FirebaseAuth    //인증 서비스 객체 생성
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLogInBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //인증 초기화
//        mAuth = Firebase.auth
//
//        //로그인 버튼 이벤트
//        binding.loginBtn.setOnClickListener {
//            val email = binding.emailEdit.text.toString()
//            val password = binding.passwordEdit.text.toString()
//            login(email, password)
//        }
//
//        //회원가입 버튼 이벤트
//        binding.signUpBtn.setOnClickListener {
//            val intent: Intent = Intent(this@LogInActivity, SignUpActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    //로그인
//    private fun login(email: String, password: String){
//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {     //성공 시 실행
//                    val intent: Intent = Intent(this@LogInActivity,
//                        MainActivity::class.java)
//                    startActivity(intent)
//                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
//                    finish()
//                } else {                    //실패 시 실행
//                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
//                    Log.d("Login", "Error: ${task.exception}")
//                }
//            }
//    }
//}
