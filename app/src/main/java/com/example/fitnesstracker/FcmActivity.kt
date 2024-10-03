package com.example.fitnesstracker

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesstracker.model.Calc


class FcmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fcm)

        val editAge: EditText = findViewById(R.id.edit_fcm_age)
        val btnSendFcm: Button = findViewById(R.id.btn_fcm)
        val genderRadioGroup: RadioGroup = findViewById(R.id.gender_fcm)


        btnSendFcm.setOnClickListener {
            if (!validate(editAge, genderRadioGroup)) {
                Toast.makeText(this, "Digite os dados corretamente!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val age = editAge.text.toString().toInt()
            val selectedRadioButton: RadioButton =
                findViewById(genderRadioGroup.checkedRadioButtonId)
            val gender = selectedRadioButton.text.toString()

            val resultFcm = resultFCM(age, gender)
            showCustomDialogBox("Sua FCM é ${resultFcm}bpm.", resultFcm)
        }


    }

    private fun showCustomDialogBox(resultFcmText: String, resultFCM: Double) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.message)
        val btnFechar: Button = dialog.findViewById(R.id.btn_close)
        val btnSave: Button = dialog.findViewById(R.id.btn_back_menu)

        tvMessage.text = resultFcmText

        dialog.show()

        btnFechar.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            finish()

            Thread {
                val app = (application as App)
                val dao = app.db.calcDao()
                dao.insert(Calc(type = "fcm", res = resultFCM))

                runOnUiThread {

                }
            }.start()
        }
    }

    private fun resultFCM(age: Int, gender: String): Double {
        if (gender == "Feminino") {
            return 226.0 - age
        } else {
            return 220.0 - age
        }
    }

    private fun validate(editAge: EditText, genderId: RadioGroup): Boolean {
        if (editAge.text.toString().isNotEmpty() &&
            !editAge.text.toString().startsWith("0") &&
            genderId.checkedRadioButtonId != -1
        ) {

            return true
        } else {
            return false
        }
    }
}