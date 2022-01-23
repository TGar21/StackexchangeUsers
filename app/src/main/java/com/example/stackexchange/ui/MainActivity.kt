package com.example.stackexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stackexchange.data.repository.Repo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Repo().renameMethod()
    }
}