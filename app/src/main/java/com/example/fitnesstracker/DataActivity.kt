package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class DataActivity : AppCompatActivity() {

    private lateinit var data: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        data = findViewById(R.id.auto_data)
        val items = resources.getStringArray(R.array.data)
        data.setText(items.first())

        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)

        data.setAdapter(adapter)

        val btnSelect: Button = findViewById(R.id.btn_select)

        btnSelect.setOnClickListener{

            dataRequest()

        }



    }

    private fun dataRequest() {
        val items = resources.getStringArray(R.array.data)
        if (data.text.toString() == items[0]) {

            val intent = Intent(this@DataActivity, ListCalcActivity::class.java)
            intent.putExtra("type", "imc")
            startActivity(intent)
        }
        else if (data.text.toString() == items[1]) {
            val intent = Intent(this@DataActivity, ListCalcActivity::class.java)
            intent.putExtra("type", "tmb")
            startActivity(intent)
        }
        else if (data.text.toString() == items[2]) {
            val intent = Intent(this@DataActivity, ListCalcActivity::class.java)
            intent.putExtra("type", "fcm")
            startActivity(intent)
        }
    }
}