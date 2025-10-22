package mendoza.omar.practica9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private val userRef = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btnSave: Button = findViewById(R.id.save_button) as Button
        btnSave.setOnClickListener { saveMarkFromForm() }

        userRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(databaseError: DatabaseError) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousName: String?) {}
            override fun onChildChanged(dataSnapshot: DataSnapshot, previousName: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                val usuario = dataSnapshot.getValue(User::class.java)
                if (usuario != null) writeMark(usuario)
            }
        })


    }

    private fun saveMarkFromForm() {
        var name: EditText =findViewById(R.id.et_name) as EditText
        var lastName:EditText=findViewById(R.id.et_lastName) as EditText
        var age:EditText=findViewById(R.id.et_age) as EditText

        val usuario = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )
        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User) {
        var listV: TextView =findViewById(R.id.list_textView) as TextView
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }


}