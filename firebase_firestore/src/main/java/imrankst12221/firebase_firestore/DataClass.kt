package imrankst12221.firebase_firestore

data class StudentInfo(
        val studentList: ArrayList<StudentItem> = arrayListOf()
)

data class StudentItem(
        val name: String = "",
        val age: Int = 0,
        val address: String,
        val mobile_no: String
)