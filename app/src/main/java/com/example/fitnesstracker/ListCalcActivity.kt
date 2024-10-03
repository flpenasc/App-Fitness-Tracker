package com.example.fitnesstracker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.Locale

class ListCalcActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result)
        rv = findViewById(R.id.rv_list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter




        val btnDelete: Button = findViewById(R.id.btn_delete)
        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

        Thread {
            val app = (application as App)
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()


            }

        }.start()


        btnDelete.setOnClickListener{

            showCustomDialogBox("Você deseja excluir todos os registros?")

        }



    }

    private fun showCustomDialogBox(rexultTextImc: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dailog_2)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.message)
        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
        val btnDont: Button = dialog.findViewById(R.id.btn_dont)

        tvMessage.text = rexultTextImc

        dialog.show()

        btnYes.setOnClickListener {
            finish()
            val type = intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")

            Thread {
                val app = (application as App)
                val dao = app.db.calcDao()

                val calcToDelete = dao.getRegisterByType(type)

                for (calc in calcToDelete) {
                    dao.delete(calc)
                }

                runOnUiThread {

                }

            }.start()


        }

        btnDont.setOnClickListener {
            dialog.dismiss()
        }
    }

    private inner class ListCalcAdapter(
        private val listCalc: List<Calc>,
    ) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {

        // 1 - Qual é o layout XML especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_datas, parent, false)
            return ListCalcViewHolder(view)
        }

        // 2 - disparado toda vez que houver uma rolagem na tela e for necessario trocar o conteudo da celula
        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = listCalc[position]
            holder.bind(itemCurrent)
        }

        // 3 - Informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return listCalc.size
        }

        // classe da célula em si
        private inner class ListCalcViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
            fun bind(item: Calc) {

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val res = item.res
                val data = sdf.format(item.createdDate)

                val tv = itemView as TextView
                tv.text = getString(R.string.list_response, res, data)




            }
        }


    }
}