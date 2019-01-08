package imrankst12221.firebase_firestore

import android.content.Context
import android.os.Handler
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_student_item.view.*
import java.lang.Exception

class StudentAdapter(private val mContext: Context, private var matchInfoItems: ArrayList<StudentItem>):
        RecyclerView.Adapter<StudentAdapter.DataHolder>(){
    private val TAG: String = "---NewsAdapter"

    private val inflater: LayoutInflater = LayoutInflater.from(mContext)
    private var itemView: View? = null
    private var params: LinearLayout.LayoutParams? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        itemView = inflater.inflate(R.layout.layout_student_item, parent, false)
        params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return DataHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.itemView.txt_name.text = matchInfoItems[position].name
        holder.itemView.txt_address.text = matchInfoItems[position].address
        holder.itemView.txt_mobile.text = matchInfoItems[position].mobile_no
        holder.itemView.txt_age.text = matchInfoItems[position].age.toString()
    }

    override fun getItemCount(): Int {
        return matchInfoItems.size
    }


    inner class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            //layoutRoot.setOnClickListener(this)
        }
    }
}