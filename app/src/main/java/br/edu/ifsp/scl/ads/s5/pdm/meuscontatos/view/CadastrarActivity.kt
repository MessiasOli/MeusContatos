package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.controller.UsuarioController
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.databinding.ActivityCadastrarBinding
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Usuario


class CadastrarActivity : AppCompatActivity() {
    private lateinit var activityCadastrarBinding: ActivityCadastrarBinding
    private lateinit var ctrUsuario : UsuarioController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityCadastrarBinding = ActivityCadastrarBinding.inflate(layoutInflater)
        ctrUsuario = UsuarioController()
        setContentView(activityCadastrarBinding.root)
    }

    fun onClick (view: View) {
        if(view == activityCadastrarBinding.cadastrarBt) {
            val id = ctrUsuario.buscaMaiorId()
            val nome = activityCadastrarBinding.nomeEt.text.toString()
            val email = activityCadastrarBinding.emailEt.text.toString()
            val senha = activityCadastrarBinding.senhaEt.text.toString()
            val repetirSenha = activityCadastrarBinding.repetirSenhaEt.text.toString()

            val usuario = Usuario(
                id,
                nome,
                email
            )

            if (saoValoresValidos(email, senha, repetirSenha)) {
                //Ciar usuário no Firebase
                AutenticadorFirebase.firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        ctrUsuario.insereUsuario(usuario)
                        Toast.makeText(this, "Usuário $email criado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else if(task.isComplete) {
                        Toast.makeText(this, "Este e-mail, já possui uma conta!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Erro na criação do usuário", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Campo preenchido incorretamente.", Toast.LENGTH_SHORT).show()
                activityCadastrarBinding.emailEt.requestFocus()
            }
        }
    }

    fun saoValoresValidos(email: String, senha: String, repetirSenha: String) : Boolean {
        return  if (email.isBlank() || email.isEmpty())
            false
        else if (senha.isBlank() || senha.isEmpty())
            false
        else if (repetirSenha.isBlank() || repetirSenha.isEmpty())
            false
        else if (senha != repetirSenha )
            false
        else if (!email.contains('@'))
            false
        else
            true
    }
}