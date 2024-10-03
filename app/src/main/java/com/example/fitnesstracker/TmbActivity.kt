package com.example.fitnesstracker

import android.annotation.SuppressLint
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


class TmbActivity : AppCompatActivity() {


    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)


        val editAge: EditText = findViewById(R.id.edit_tmb_age)
        val editWeight: EditText = findViewById(R.id.edit_tmb_weight)
        val editHeight: EditText = findViewById(R.id.edit_tmb_height)

        val btnSendTmc: Button = findViewById(R.id.btn_tmb)

        val genderRadioGroup: RadioGroup = findViewById(R.id.gender)



        btnSendTmc.setOnClickListener {

            if (!validate(editAge, editWeight, editHeight, genderRadioGroup)) {
                Toast.makeText(this, "Digite os dados corretamente!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val selectedRadioButton: RadioButton =
                findViewById(genderRadioGroup.checkedRadioButtonId)

            val age = editAge.text.toString().toInt()
            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()
            val gender = selectedRadioButton.text.toString()

            val resultTMB = calculateTMB(age, weight, height, gender)

            showCustomDialogBox("Seu TBM é ${String.format("%.2f", resultTMB)} kcal", resultTMB)


        }

    }

    private fun validate(
        editAge: EditText,
        editWeight: EditText,
        editHeight: EditText,
        selectedGenderId: RadioGroup
    ): Boolean {

        if (editAge.text.toString().isNotEmpty() &&
            editWeight.text.toString().isNotEmpty() &&
            editHeight.text.toString().isNotEmpty() &&
            !editAge.text.startsWith("0") &&
            !editWeight.text.startsWith("0") &&
            !editHeight.text.startsWith("0") &&
            selectedGenderId.checkedRadioButtonId != -1
        ) {
            return true
        } else {
            return false
        }

    }

    private fun calculateTMB(age: Int, weight: Int, height: Int, gender: String): Double {

        if (gender == "Feminino") {
            return 655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
        } else {
            return 66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)
        }

    }


    private fun showCustomDialogBox(rexultTextTmb: String, resulTmb: Double) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.message)
        val btnFechar: Button = dialog.findViewById(R.id.btn_close)
        val btnSave: Button = dialog.findViewById(R.id.btn_back_menu)

        tvMessage.text = rexultTextTmb

        dialog.show()

        btnFechar.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            finish()

            Thread {
                val app = (application as App)
                val dao = app.db.calcDao()
                dao.insert(Calc(type = "tmb", res = resulTmb))

                runOnUiThread {

                }

            }.start()
        }

    }



}