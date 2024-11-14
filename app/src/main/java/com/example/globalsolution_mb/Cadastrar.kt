package com.example.globalsolution_mb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Cadastrar : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastrar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa Firebase Authentication e Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Configura o botão de cadastro
        val botaoCadastrar = findViewById<Button>(R.id.botaoCadastrar)
        botaoCadastrar.setOnClickListener {
            val nome = findViewById<EditText>(R.id.nome).text.toString().trim()
            val email = findViewById<EditText>(R.id.email).text.toString().trim()
            val senha = findViewById<EditText>(R.id.senha).text.toString().trim()

            // Verifica se todos os campos foram preenchidos, se o e-mail é válido e se a senha tem no mínimo 6 caracteres
            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (senha.length >= 6) {
                        cadastrarUsuario(nome, email, senha)
                    } else {
                        Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o botão para redirecionar ao login
        val botaoLogin = findViewById<Button>(R.id.botaologin)
        botaoLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Função para cadastrar o usuário com Firebase Authentication e salvar o nome no Firestore
    private fun cadastrarUsuario(nome: String, email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Obtém o ID do usuário recém-criado
                    val userId = auth.currentUser?.uid
                    val usuario = hashMapOf(
                        "nome" to nome,
                        "email" to email
                    )

                    // Salva o nome e o email do usuário no Firestore, usando o userId como ID do documento
                    userId?.let {
                        db.collection("usuarios").document(it)
                            .set(usuario)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                // Redireciona para a tela principal após o cadastro bem-sucedido
                                startActivity(Intent(this, Principal::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Erro ao salvar dados do usuário: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Exibe a mensagem de erro caso o cadastro falhe
                    val errorMessage = task.exception?.localizedMessage ?: "Erro desconhecido"
                    Toast.makeText(this, "Erro ao cadastrar usuário: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
