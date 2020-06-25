package com.example.messagingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.login).setOnClickListener { view ->
            login(view)
        }
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }

    fun login(view: View){
        var loginStr = findViewById<TextView>(R.id.username).text
        var password = findViewById<TextView>(R.id.password).text

        var able = true
        Snackbar.make(view, password, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        if (able) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Snackbar.make(view, "Login failarino", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}