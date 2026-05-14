package devilstudio.com.farmerfriend

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var adminLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        adminLoginButton = findViewById(R.id.adminLoginButton)

        adminLoginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Kullanıcı adı ve şifre giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val seller = SellerRepository.login(username, password)

            if (seller != null) {
                Toast.makeText(this, "${seller.name} girişi başarılı", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, AdminActivity::class.java)
                intent.putExtra("sellerId", seller.id)
                intent.putExtra("sellerName", seller.name)
                intent.putExtra("sellerCity", seller.city)
                intent.putExtra("sellerDistrict", seller.district)
                intent.putExtra("sellerPhone", seller.phone)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Kullanıcı adı veya şifre hatalı", Toast.LENGTH_SHORT).show()
            }
        }
    }
}