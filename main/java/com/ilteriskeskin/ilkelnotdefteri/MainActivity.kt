package com.ilteriskeskin.ilkelnotdefteri

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notArray = ArrayList<String>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notArray)
        listView.adapter = arrayAdapter

        try {

            val database = this.openOrCreateDatabase("Notlar", Context.MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS notlar (eklenenNot VARCHAR)")

            val cursor = database.rawQuery("SELECT * FROM notlar", null)

            val notIx = cursor.getColumnIndex("eklenenNot")
            cursor.moveToFirst()

            while (cursor != null){

                notArray.add(cursor.getString(notIx))

                cursor.moveToNext()

                arrayAdapter.notifyDataSetChanged()

            }

            cursor?.close()

        }

        catch (e: Exception){

            e.printStackTrace()

        }
    }

    fun kayit(view: View){

        val not = etNot.text.toString()

        if (!etNot.text.isNullOrEmpty()) {

            try {

                val database = this.openOrCreateDatabase("Notlar", Context.MODE_PRIVATE, null)
                database.execSQL("CREATE TABLE IF NOT EXISTS notlar (eklenenNot VARCHAR)")

                val sqlString = "INSERT INTO notlar (eklenenNot) VALUES (?)"

                val statement = database.compileStatement(sqlString)
                statement.bindString(1, not)
                statement.execute()

            } catch (e: Exception) {

                e.printStackTrace()

            }

            Toast.makeText(this, "Kayıt başarılı.", Toast.LENGTH_LONG).show()
            etNot.setText("")

        }

        else{

            Toast.makeText(this, "Lütfen Not Ekleme Alanını Boş Doldurunuz.", Toast.LENGTH_LONG).show()

        }

    }
}
