package com.example.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.vector_imc,
                textStringId = R.string.imc,
                color = Color.TRANSPARENT
            )
        )

        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.vector_tmb,
                textStringId = R.string.tmb,
                color = Color.TRANSPARENT
            )
        )

        mainItems.add(
            MainItem(
                id = 3,
                drawableId = R.drawable.vector_cfm,
                textStringId = R.string.fcm,
                color = Color.TRANSPARENT
            )
        )

//        mainItems.add(
//            MainItem(
//                id = 4,
//                drawableId = R.drawable.vector_water,
//                textStringId = R.string.water,
//                color = Color.TRANSPARENT
//            )
//        )

        mainItems.add(
            MainItem(
                id = 5,
                drawableId = R.drawable.vector_data,
                textStringId = R.string.data,
                color = Color.TRANSPARENT
            )
        )

        mainItems.add(
            MainItem(
                id = 6,
                drawableId = R.drawable.vector_tipss,
                textStringId = R.string.tips,
                color = Color.TRANSPARENT
            )
        )


        val adapter = MainAdapter(mainItems) { id ->

            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }

                2 -> {
                    val intent2 = Intent(this@MainActivity, TmbActivity::class.java)
                    startActivity(intent2)
                }

                3 -> {
                    val intent3 = Intent(this@MainActivity, FcmActivity::class.java)
                    startActivity(intent3)

                }

                4 -> {

                }

                5 -> {
                    val intent5 = Intent(this@MainActivity, DataActivity::class.java)
                    startActivity(intent5)


                }

                6 -> {
                    val intent6 = Intent(this@MainActivity, TipsActivity::class.java)
                    startActivity(intent6)


                }
            }

        }

        rvMain = findViewById(R.id.rv_main)

        rvMain.adapter = adapter

        rvMain.layoutManager = GridLayoutManager(this, 3)
    }

    // classe para administrar a recycleview e suas celulas
    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Qual é o layout XML especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - disparado toda vez que houver uma rolagem na tela e for necessario trocar o conteudo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        // 3 - Informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // classe da célula em si
        private inner class MainViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.image)
                val name: TextView = itemView.findViewById(R.id.name)
                val container: LinearLayout =
                    itemView.findViewById(R.id.container_linear_layout)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)

                }


            }
        }


    }

}