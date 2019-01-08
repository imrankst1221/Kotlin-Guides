package imrankst12221.firebase_firestore

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_student_item.view.*
import kotlinx.android.synthetic.main.popup_add_student.view.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mContext: Context
    private lateinit var mFirestore: FirebaseFirestore

    private var studentInfo = StudentInfo()
    lateinit var studentAdapter: StudentAdapter
    private lateinit var mAddStudentPopup: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = this

        initView()

        readDataFromFirestone()
    }

    private fun initView(){
        studentAdapter = StudentAdapter(mContext, studentInfo.studentList)
        recycler_student.adapter = studentAdapter
        recycler_student.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)

        fav_add.setOnClickListener {
            showAddStudentPopup()
        }
    }

    private fun showAddStudentPopup(){
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupAddStudent = inflater.inflate(R.layout.popup_add_student, null)

        val colorDrawable = ColorDrawable(ContextCompat.getColor(mContext, R.color.colorBlack))
        colorDrawable.alpha = 70

        mAddStudentPopup = PopupWindow(popupAddStudent, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        mAddStudentPopup.setBackgroundDrawable(colorDrawable)
        mAddStudentPopup.isOutsideTouchable = true

        if (Build.VERSION.SDK_INT >= 21) {
            mAddStudentPopup.setElevation(5.0f)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAddStudentPopup.showAsDropDown(popupAddStudent, Gravity.CENTER, 0, Gravity.CENTER)
        } else {
            mAddStudentPopup.showAsDropDown(popupAddStudent, Gravity.CENTER, 0)
        }

        popupAddStudent.btn_done.setOnClickListener {
            studentInfo.studentList.add(StudentItem(popupAddStudent.txt_name.text.toString(),
                    popupAddStudent.txt_age.text.toString().toInt(), popupAddStudent.txt_address.text.toString(),
                    popupAddStudent.txt_mobile.text.toString() ))
            writeDataOnFirestone()
        }
    }

    private fun writeDataOnFirestone(){
        mFirestore.collection("student_info").document("student_info")
                .set(studentInfo)
                .addOnSuccessListener {
                    Toast.makeText(mContext, "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }.addOnFailureListener {
                    e -> Log.e(TAG, "Error writing document", e)
                }
    }

    private fun readDataFromFirestone(){
        mFirestore = FirebaseFirestore.getInstance()
        mFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()

        mFirestore
                .collection("student_info").document("student_list")
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        studentInfo = document.toObject(StudentInfo::class.java) ?: StudentInfo()
                        studentAdapter.notifyDataSetChanged()
                        Toast.makeText(mContext, "DocumentSnapshot read successfully!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(mContext, "No such document!", Toast.LENGTH_LONG).show()
                        Log.e(TAG, "No such document")
                    }
                }.addOnFailureListener {
                    e -> Log.e(TAG, "Error writing document", e)
                }
    }
}
