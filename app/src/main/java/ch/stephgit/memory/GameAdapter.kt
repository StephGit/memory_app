package ch.stephgit.memory

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*


data class GameListItem(val userName: String, val date: Date, val score: Int)

class GameAdapter(context: Context, @LayoutRes itemLayoutRes: Int, items: MutableList<GameListItem>):
    ArrayAdapter<GameListItem>(context, itemLayoutRes, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        val item = getItem(position)

        view.findViewById<TextView>(R.id.tvItemText1).text = item?.userName
        view.findViewById<TextView>(R.id.tvItemText2).text = item?.date.toString()
        view.findViewById<TextView>(R.id.tvItemText3).text = item?.score.toString()
        return view
    }
}