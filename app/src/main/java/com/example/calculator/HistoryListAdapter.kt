package com.example.calculator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.calculator.data.entities.CalculationHistory
import java.text.SimpleDateFormat
import java.util.*

abstract class HistoryListAdapter(context: Context, resource: Int, objects: List<CalculationHistory>) :
    ArrayAdapter<CalculationHistory>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        val holder: ViewHolder

        if (row == null) {
            val inflater = LayoutInflater.from(context)
            row = inflater.inflate(R.layout.list_item_history, parent, false)

            holder = ViewHolder()
            holder.expressionTextView = row.findViewById(R.id.history_expression)
            holder.resultTextView = row.findViewById(R.id.history_result)
            holder.timestampTextView = row.findViewById(R.id.history_timestamp)

            row.tag = holder
        } else {
            holder = row.tag as ViewHolder
        }

        val item = getItem(position)

        val timeZone = TimeZone.getTimeZone("GMT-3")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        dateFormat.timeZone = timeZone

        holder.expressionTextView.text = item?.expression
        holder.resultTextView.text = item?.result.toString()
        holder.timestampTextView.text = dateFormat.format(Date(item?.timestamp!!))

        return row!!
    }

    private class ViewHolder {
        lateinit var expressionTextView: TextView
        lateinit var resultTextView: TextView
        lateinit var timestampTextView: TextView
    }
}
