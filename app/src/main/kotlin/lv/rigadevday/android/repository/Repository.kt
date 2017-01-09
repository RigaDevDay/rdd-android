package lv.rigadevday.android.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Repository {

    val database: DatabaseReference by lazy { FirebaseDatabase.getInstance().reference }

    fun speakers(): DatabaseReference = database.child("speakers")

}
