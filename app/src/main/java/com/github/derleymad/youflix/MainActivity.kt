package com.github.derleymad.youflix

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.derleymad.youflix.model.Category
import com.github.derleymad.youflix.utils.DataRequest

class MainActivity : AppCompatActivity(), DataRequest.Callback{

    private lateinit var progress: ProgressBar
    private lateinit var adapter: CategoryAdapter
    private val categories = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.progress_bar)

        adapter = CategoryAdapter(categories)
        val rv: RecyclerView = findViewById(R.id.rv_main)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        DataRequest(this).execute("https://derleymad.github.io/youflix/categories.json")
    }

    override fun onPreExecute() {
        progress.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        // aqui será quando o CategoryTask chamará de volta
        // (callback) - listener
        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged() // força o adaptar chamar de novo o onBindViewHolder, etc

        progress.visibility = View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progress.visibility = View.GONE
    }



}