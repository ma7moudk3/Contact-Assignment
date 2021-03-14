package com.example.contactsfirestore

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ContactAdapter(private val context: Context, var data: ArrayList<Contact>) : BaseAdapter() {
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(i: Int): Any {
        return data[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.conact_item, parent, false)
        val tvName = rowView.findViewById(R.id.tvName) as TextView
        tvName.text = data[position].name
        val tvAddress = rowView.findViewById(R.id.tvAddress) as TextView
        tvAddress.text = data[position].address
        val tvNumber = rowView.findViewById(R.id.tvNumber) as TextView
        tvNumber.text = data[position].number
        return rowView
    }
}