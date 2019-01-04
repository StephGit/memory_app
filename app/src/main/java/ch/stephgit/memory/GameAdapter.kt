package ch.stephgit.memory

import android.content.Context

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


data class GameListItem(val userName: String, val date: Date, val flips: Long)

class GameAdapter(context: Context, @LayoutRes itemLayoutRes: Int, items: List<GameListItem>):
    ArrayAdapter<GameListItem>(context, itemLayoutRes, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        val item = getItem(position)

        val formatDate = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault()).format(item?.date)

        view.findViewById<TextView>(R.id.tvItemText1).text = item?.userName
        view.findViewById<TextView>(R.id.tvItemText2).text = formatDate
        view.findViewById<TextView>(R.id.tvItemText3).text = item?.flips.toString()
        return view
    }
}