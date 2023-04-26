package com.example.polychat24

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.polychat24.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//class SignUpActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up)
//    }
//}

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 초기화 //안하면 데이터가 계속 쌓임
        mAuth = Firebase.auth

        //DB 초기화 //안하면 데이터가 계속 쌓임
        mDbRef = Firebase.database.reference

        binding.signUpBtn.setOnClickListener {
            val name = binding.nameEdit.text.toString().trim()
            val email = binding.emailEdit.text.toString().trim()    //trim() = 공백제거
            val password = binding.passwordEdit.text.toString().trim()
            signUp(name, email, password)
        }
    }

    //회원 가입
    private fun signUp(name: String, email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 성공시 실행
                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                    addUserToDatabase(
                        name,
                        email,
                        mAuth.currentUser?.uid!!)    //name, email, uId 저장
                } else {
                    // 실패시 실행
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    Log.d("SignUp", "Error: ${task.exception}")
                }
            }
    }

    //DB에 저장
    private fun addUserToDatabase(name: String, email: String, uId: String) {
        mDbRef.child("user").child(uId)
            .setValue(User(name, email, uId))    //user클래스에 name,email,uid를 담아 DB에 저장
    }
}