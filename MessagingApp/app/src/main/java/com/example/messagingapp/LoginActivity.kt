package com.example.messagingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.messagingapp.db.UserDb
import com.example.messagingapp.models.Settings
import com.google.android.material.snackbar.Snackbar
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*

class LoginActivity : AppCompatActivity() {
    var database: Database? = null

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
        val view = super.onCreateView(parent, name, context, attrs);

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        database = DbConnection.getConnection()

        if (database == null)
        {
            Snackbar.make(view!!, "Database connection failed", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        return view
    }

    fun login(view: View){
        var loginStr = findViewById<TextView>(R.id.username).text.toString()
        var passwordStr = findViewById<TextView>(R.id.password).text.toString()

        val query = database!!
            .from(UserDb)
            .select()
            .where { (UserDb.login eq loginStr) and (UserDb.password eq passwordStr) }

        if (query.totalRecords > 0) {
            for (row in query){
                var id = row[UserDb.id]
                Settings.setSettings(id!!)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Snackbar.make(view, "Login failarino", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}