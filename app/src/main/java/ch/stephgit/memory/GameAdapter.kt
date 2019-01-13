package ch.stephgit.memory

import android.content.Context

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ch.stephgit.memory.persistence.entity.Game
import java.text.SimpleDateFormat
import java.util.*


class GameAdapter(context: Context, @LayoutRes itemLayoutRes: Int, items: List<Game>):
    ArrayAdapter<Game>(context, itemLayoutRes, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        val item = getItem(position)

        val formatDate = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.getDefault()).format(item?.date)

        view.findViewById<TextView>(R.id.tvItemText1).text = item?.username
        view.findViewById<TextView>(R.id.tvItemText2).text = formatDate
        view.findViewById<TextView>(R.id.tvItemText3).text = item?.flips.toString()
        return view
    }
}