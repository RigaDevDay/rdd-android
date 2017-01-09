package lv.rigadevday.android.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import lv.rigadevday.android.R
import lv.rigadevday.android.repository.Repository
import lv.rigadevday.android.repository.model.Speaker
import lv.rigadevday.android.utils.logD
import lv.rigadevday.android.utils.logE


class TabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        Repository().speakers().addListenerForSingleValueEvent(listener)
    }

    val listener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError?) {
            error?.message?.logE()
        }

        override fun onDataChange(snapshot: DataSnapshot?) {
            snapshot?.let {
                val speaker = it.children.map { it.getValue(Speaker::class.java) }
                    .toList()[2]

                speaker.badges.logD()
            }
        }
    }
}
