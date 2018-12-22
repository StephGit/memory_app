package ch.stephgit.memory

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ch.stephgit.memory.R
import java.util.*

data class ScoreListItem(val name: String, val date: Date, val score: String)

class ScoreAdapter(context: Context, @LayoutRes itemLayoutRes: Int, items: MutableList<ScoreListItem>):
    ArrayAdapter<ScoreListItem>(context, itemLayoutRes, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        val item = getItem(position)

        view.findViewById<TextView>(R.id.tvItemText1).text = item.name
        view.findViewById<TextView>(R.id.tvItemText2).text = item.date.toString()
        view.findViewById<TextView>(R.id.tvItemText3).text = item.score
        return view
    }
}