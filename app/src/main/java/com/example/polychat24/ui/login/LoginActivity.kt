package com.example.polychat24.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.polychat24.databinding.ActivityLogInBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogInBinding  //바인딩 객체 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)  //바인딩 객체 초기화
        setContentView(binding.root)
    }
}