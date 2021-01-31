package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.R
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {
    // View biding
    private lateinit var activityRecuperarSenhaBinding: ActivityRecuperarSenhaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instanciar view binding
        activityRecuperarSenhaBinding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(activityRecuperarSenhaBinding.root)
    }

    fun onClick (view: View) {
        if (view == activityRecuperarSenhaBinding.enviarEmailBt) {
            val email = activityRecuperarSenhaBinding.emailRecuperacaoSenhaEt.text.toString()
            if (email.isNotBlank() && email.isNotEmpty()) {
                AutenticadorFirebase.firebaseAuth.sendPasswordResetEmail(email)
                Toast.makeText(this, "Recuperação de senha enviada para o email $email" , Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Digite o e-mail" , Toast.LENGTH_SHORT).show()
                activityRecuperarSenhaBinding.emailRecuperacaoSenhaEt.requestFocus()
            }

        }
    }
}