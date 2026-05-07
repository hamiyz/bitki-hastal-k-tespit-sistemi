package devilstudio.com.farmerfriend

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val navHome = findViewById<TextView>(R.id.navHome)
        val navHistory = findViewById<TextView>(R.id.navHistory)
        val navAbout = findViewById<TextView>(R.id.navAbout)

        navHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        navHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        navAbout.setOnClickListener {
            Toast.makeText(this, "Zaten hakkında ekranındasınız", Toast.LENGTH_SHORT).show()
        }
    }
}