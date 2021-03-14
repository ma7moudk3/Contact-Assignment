package com.example.contactsfirestore

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    var db = FirebaseFirestore.getInstance()
    var etContactName: EditText? = null
    var etContactNumber: EditText? = null
    var etContactAddress: EditText? = null
    var btnSave: Button? = null
    var listView: ListView? = null
    var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etContactNumber = findViewById(R.id.etContactNumber)
        etContactName = findViewById(R.id.etContactName)
        etContactAddress = findViewById(R.id.etContactAddress)
        btnSave = findViewById(R.id.btnSave)
        listView = findViewById(R.id.listView)
        progressBar = findViewById(R.id.progressBar)
        progressBar!!.visibility = View.VISIBLE
        listView!!.visibility = View.GONE
        getAllProduct()
        btnSave!!.setOnClickListener() {
            saveToFirebase()
        }
    }

    private fun saveToFirebase() {
        val contactName: String = etContactName!!.text.toString()
        val contactNumber: String = etContactNumber!!.text.toString()
        val contactAddress: String = etContactAddress!!.text.toString()

        val product: MutableMap<String, Any> = HashMap()
        product["contact_name"] = contactName
        product["contact_number"] = contactNumber
        product["contact_address"] = contactAddress

        db.collection("contacts")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Added succefully $documentReference", Toast.LENGTH_LONG)
                    .show()
                listView!!.invalidateViews()
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error adding document", e) }

    }

    @SuppressLint("SetTextI18n")
    private fun getAllProduct() {
        var data = ArrayList<Contact>()

        db.collection(
            "contacts"
        ).get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                Log.e("tag", "${document.id} => ${document.data}")
                Log.e("tag2", "name ${document.getString("contact_name")}")
                Log.e("tag2", "number: ${document.getString("contact_number")}")
                Log.e("tag2", "address: ${document.getString("contact_address")}")

                data.add(
                    Contact(
                        "${document.getString("contact_name")}",
                        "${document.getString("contact_number")}",
                        "${document.getString("contact_address")}"
                    )
                )
                val contactAdapter = ContactAdapter(this, data)
                listView!!.adapter = contactAdapter
            }

        }
        progressBar!!.visibility = View.GONE
        listView!!.visibility = View.VISIBLE

    }
}