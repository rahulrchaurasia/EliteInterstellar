package com.interstellar.elite.test

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.interstellar.elite.R



class TestActivity : AppCompatActivity() {

    lateinit var arrayList: ArrayList<String>
    lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val autotextView = findViewById<AutoCompleteTextView>(R.id.actTest) as AutoCompleteTextView
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        arrayList = ArrayList<String>()
        arrayList.add("Winter")
        arrayList.add("Spring")
        arrayList.add("Summer")
        arrayList.add("Monsoon")
        arrayList.add("Sumita")
        arrayList.add("Rahul")
        arrayList.add("Suchain")
        arrayList.add("Rajesh")
        arrayList.add("Rajesh")
        arrayList.add("Shailesh")
        arrayList.add("Hitesh")


        arrayAdapter = ArrayAdapter<String>(applicationContext,R.layout.support_simple_spinner_dropdown_item,arrayList)

        autotextView.setAdapter(arrayAdapter)
      // autotextView.threshold(1)



    }


}