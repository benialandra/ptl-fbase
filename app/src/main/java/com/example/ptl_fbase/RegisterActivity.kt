package com.example.ptl_fbase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ptl_fbase.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
                    }
}