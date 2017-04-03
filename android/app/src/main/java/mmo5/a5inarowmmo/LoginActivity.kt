package mmo5.a5inarowmmo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userNameView = findViewById(R.id.userName) as EditText

        val signInButton = findViewById(R.id.sign_in_button) as Button
        signInButton.setOnClickListener {
            if (userNameView.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Please enter a user name", Toast.LENGTH_SHORT).show()
            } else {
                val activityIntent = Intent(applicationContext, MainActivity::class.java)
                startActivity(activityIntent)
            }
        }
    }
}

