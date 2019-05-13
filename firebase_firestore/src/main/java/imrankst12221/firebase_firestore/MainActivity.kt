package imrankst12221.firebase_firestore

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup_add_student.view.*
import java.lang.Exception

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

        FirebaseApp.initializeApp(this)

        initListView()

        readDataFromFirestore()

        // write single row
        //writeDataOnFirestore(StudentItem("John dao", 18, "California", "098741015"))

        // read data with condition
        //readDataFromFirestore(18)
    }

    private fun initListView(){
        studentAdapter = StudentAdapter(mContext, studentInfo.studentList)
        recycler_student.adapter = studentAdapter
        recycler_student.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        fav_add.setOnClickListener {
            showAddStudentPopup()
        }
    }

    // write whole data class
    private fun writeDataOnFirestore(){
        mFirestore.collection("student_info").document("student_list")
                .set(studentInfo)
                .addOnSuccessListener {
                    Toast.makeText(mContext, "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "DocumentSnapshot successfully written!")

                    initListView()
                }.addOnFailureListener {
                    e -> Log.e(TAG, "Error writing document", e)
                }
    }

    // write single row
    private fun writeDataOnFirestore(studentItem: StudentItem){
        val student = HashMap<String, Any>()
        student["name"] = studentItem.name
        student["age"] = studentItem.name
        student["address"] = studentItem.address
        student["mobile_no"] = studentItem.mobile_no
        mFirestore.collection("student_info").document("student_list")
                .set(student)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    // read whole data class
    private fun readDataFromFirestore(){
        mFirestore = FirebaseFirestore.getInstance()
        mFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        mFirestore
                .collection("student_info").document("student_list")
                .get()
                .addOnSuccessListener { document ->
                    try {
                        if (document != null) {
                            studentInfo = document.toObject(StudentInfo::class.java) ?: StudentInfo()

                            initListView()
                            Toast.makeText(mContext, "DocumentSnapshot read successfully!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(mContext, "No such document!", Toast.LENGTH_LONG).show()
                        }
                    }catch (ex: Exception){
                        Log.e(TAG, ex.message)
                    }
                }.addOnFailureListener {
                    e -> Log.e(TAG, "Error writing document", e)
                }
    }

    // read data with condition
    private fun readDataFromFirestore(ageCondition: Int){
        mFirestore = FirebaseFirestore.getInstance()
        mFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        mFirestore
                .collection("student_info")
                .whereLessThan("age", ageCondition)
                .get()
                .addOnSuccessListener { documents ->
                    try {
                        if (documents != null) {
                            for (document in documents) {
                                Log.d(TAG, "${document.id} => ${document.data}")
                            }
                            Toast.makeText(mContext, "DocumentSnapshot read successfully!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(mContext, "No such document!", Toast.LENGTH_LONG).show()
                        }
                    }catch (ex: Exception){
                        Log.e(TAG, ex.message)
                    }
                }.addOnFailureListener {
                    e -> Log.e(TAG, "Error writing document", e)
                }
    }


    // student data insert from UI
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

        mAddStudentPopup.isFocusable = true
        mAddStudentPopup.update()
        popupAddStudent.btn_done.setOnClickListener {
            studentInfo.studentList.add(StudentItem(popupAddStudent.txt_name_edit.text.toString(),
                    popupAddStudent.txt_age_edit.text.toString().toInt(), popupAddStudent.txt_address_edit.text.toString(),
                    popupAddStudent.txt_mobile_edit.text.toString() ))
            writeDataOnFirestore()
        }
    }
}
