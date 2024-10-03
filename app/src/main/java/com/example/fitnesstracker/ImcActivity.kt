package com.example.fitnesstracker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnesstracker.model.Calc

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)

        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, "Digite seu peso e altura corretamente!", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()


            val resultImc = calculate(weight, height)

            val rexultTextImc = imcResponse(resultImc)

            showCustomDialogBox(
                "Seu IMC é ${String.format("%.2f", resultImc)}. \n$rexultTextImc!",
                resultImc
            )


//            val dialog = AlertDialog.Builder(this)


//            dialog.setTitle("Seu IMC é: ${String.format("%.2f", resultImc)}")
//            dialog.setMessage(rexultTextImc)
//            dialog.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
//                override fun onClick(dialog: DialogInterface, which: Int) {
//
//                }
//            })
//            val d = dialog.create()
//            d.show()


        }

    }

    private fun showCustomDialogBox(rexultTextImc: String, resultImc: Double) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.message)
        val btnClose: Button = dialog.findViewById(R.id.btn_close)
        val btnSave: Button = dialog.findViewById(R.id.btn_back_menu)

        tvMessage.text = rexultTextImc

        dialog.show()

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            finish()

            Thread {
                val app = (application as App)
                val dao = app.db.calcDao()
                dao.insert(Calc(type = "imc", res = resultImc))

                runOnUiThread {

                }

            }.start()
        }

    }

    private fun imcResponse(imc: Double): String {
        return when {
            imc < 15.0 -> "Você está severamente abaixo do peso"
            imc < 16.0 -> "Você está muito abaixo do peso"
            imc < 18.5 -> "Você está abaixo do peso"
            imc < 25.0 -> "Você está normal"
            imc < 30.0 -> "Você está acima do peso"
            imc < 35 -> "Você está moderadamente obeso"
            imc < 40.0 -> "Você está severamente obeso"
            else -> "Você está extremamente obeso"
        }
    }
    //função para validar o texto

    private fun calculate(weight: Int, height: Int): Double {

        return weight / ((height / 100.0) * (height / 100.0))

    }

    private fun validate(): Boolean {

        if (editWeight.text.toString().isNotEmpty() &&
            editHeight.text.toString().isNotEmpty()
            && !editWeight.text.toString().startsWith("0")
            && !editHeight.text.toString().startsWith("0")
        ) {
            return true

        } else {
            return false
        }
    }
}