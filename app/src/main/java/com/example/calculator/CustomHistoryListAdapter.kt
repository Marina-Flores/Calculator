package com.example.calculator

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.calculator.data.entities.CalculationHistory

class CustomHistoryListAdapter(context: Context, resource: Int, objects: List<CalculationHistory>) :
    HistoryListAdapter(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)

        val editButton = view.findViewById<Button>(R.id.history_edit_button)
        val deleteButton = view.findViewById<Button>(R.id.history_delete_button)

        editButton.setOnClickListener {
            // TODO
        }

        deleteButton.setOnClickListener {
            // TODO
        }

        return view
    }
}