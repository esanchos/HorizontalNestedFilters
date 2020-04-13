package com.earaujo.recyclerviewanimations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {

        filtersRecyclerView.adapter =
            FiltersAdapter(
                listOf(
                    listOf("Comprar", "Alugar"),
                    listOf("Ação", "Aventura", "Comédia", "Terror", "Suspense", "Drama")
                )
            ) {
                //TODO handle item click
            }
    }

}
