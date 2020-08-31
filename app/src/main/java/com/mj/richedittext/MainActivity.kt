package com.mj.richedittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val findViewById = findViewById<RichEditText>(R.id.preview)
        findViewById.setReadOnly(true)
        findViewById.hideMenuBar();

        mainEditor.setOnTextChangeListener { text: String? ->

            findViewById.text = text;
        }


    }
}