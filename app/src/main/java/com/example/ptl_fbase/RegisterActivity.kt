            package com.example.ptl_fbase

            import android.R
            import androidx.appcompat.app.AppCompatActivity
            import android.os.Bundle
            import android.util.Patterns
            import android.widget.Toast
            import com.example.ptl_fbase.databinding.ActivityRegisterBinding
            import com.google.firebase.auth.FirebaseAuth
            import android.content.Intent

            class RegisterActivity : AppCompatActivity() {

                lateinit var auth : FirebaseAuth
                lateinit var binding : ActivityRegisterBinding

                override fun onCreate(savedInstanceState: Bundle?) {
                    binding = ActivityRegisterBinding.inflate(layoutInflater)
                    super.onCreate(savedInstanceState)
                    setContentView(binding.root)

                    auth=FirebaseAuth.getInstance()

                  binding.tvToLogin.setOnClickListener {
                      val intent=Intent(this,LoginActivity::class.java)
                      startActivity(intent)
                  }

                    binding.btnRegister.setOnClickListener{
                        val email=binding.edtEmailRegister.text.toString()
                        val password=binding.edtPasswordRegister.text.toString()
                        if(email.isEmpty()){
                            binding.edtEmailRegister.error="Email Harus Diisi"
                            binding.edtEmailRegister.requestFocus()
                            return@setOnClickListener
                        }
                        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            binding.edtEmailRegister.error="Email Tidak Valid"
                            binding.edtEmailRegister.requestFocus()
                            return@setOnClickListener
                        }
                        if(password.isEmpty()){
                            binding.edtPasswordRegister.error="Password Harus Diisi"
                            binding.edtPasswordRegister.requestFocus()
                            return@setOnClickListener
                        }
                        if(password.length<8){
                            binding.edtPasswordRegister.error="Password Min 8 karakter"
                            binding.edtPasswordRegister.requestFocus()
                            return@setOnClickListener
                        }
                        RegisterFirebase(email,password)
                    }
                }

                private fun RegisterFirebase(email: String, password: String) {
                              auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) {
                                    if(it.isSuccessful){
                                        Toast.makeText(this,"Register Berhasil",Toast.LENGTH_SHORT).show()
                                        val intent=Intent(this,LoginActivity::class.java)
                                        startActivity(intent)
                                    }else{
                                        Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                                    }
                              }
                }
            }