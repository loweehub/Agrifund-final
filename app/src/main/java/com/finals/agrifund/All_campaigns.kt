package com.finals.agrifund

import android.os.Bundle
import android.view.ViewDebug.RecyclerTraceType
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class All_campaigns : AppCompatActivity() {

    private val dataItems = mutableListOf<Data_Item>()
    private lateinit var adapter: Adapter_Campaigns
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_campaigns)



       


    }
}