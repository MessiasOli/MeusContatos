package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.R
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.controller.UsuarioController
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.databinding.ActivityAutenticacaoBinding
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view.AutenticacaoActivity.Extra.USUARIO
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AutenticacaoActivity : AppCompatActivity() {

    private lateinit var activityAutenticacaoBinding: ActivityAutenticacaoBinding
    private lateinit var ctrUsuario : UsuarioController

    // Google sing in Option e request code
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val GOOGLE_SIGIN_IN_REQUEST_CODE = 1

    object Extra {
        val USUARIO = "USUARIO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctrUsuario = UsuarioController()
        activityAutenticacaoBinding = ActivityAutenticacaoBinding.inflate(layoutInflater)
        setContentView(activityAutenticacaoBinding.root)

        // Instanciar a GSO
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Instanciar o GSO
        AutenticadorFirebase.googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        activityAutenticacaoBinding.loginGoogleBt.setOnClickListener{
            // Verificar se já existe alguem conectado
            var googleSignInAccount: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
            if (googleSignInAccount == null) {
                //Não existe uma conta Google logada ainda
                startActivityForResult(AutenticadorFirebase.googleSignInClient?.signInIntent, GOOGLE_SIGIN_IN_REQUEST_CODE)
            }
            else
            {
                // Já existe uma conta Gooogle Logada
                posLoginSucess()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGIN_IN_REQUEST_CODE){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Pegando uma conta google a partir dele
                val contaGoogle = task.getResult(ApiException::class.java)
                if (contaGoogle != null){
                    // Extraindo as credenciais a partir do idToken da conta Google
                    val credencial = GoogleAuthProvider.getCredential(contaGoogle.idToken, null)

                    AutenticadorFirebase.firebaseAuth.signInWithCredential(credencial).addOnCompleteListener{task ->
                        if(task.isSuccessful) {
                            Toast.makeText(this, "Usuário ${contaGoogle.email} autenticado", Toast.LENGTH_SHORT).show()
                            posLoginSucess()
                        }
                        else {
                            Toast.makeText(this, "Falha ao autenticação do Google", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(this, "Falha ao autenticação do Google", Toast.LENGTH_SHORT).show()
                }

            } catch (e: ApiException){
                Toast.makeText(this, "Falha ao autenticação do Google", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.cadastrarBt -> {
                startActivity(Intent(this, CadastrarActivity::class.java))
            }
            R.id.entrarBt -> {
                val email = activityAutenticacaoBinding.emailEt.text.toString()
                val senha = activityAutenticacaoBinding.senhaEt.text.toString()

                AutenticadorFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener {
                    Toast.makeText(this, "Usuário $email autenticado", Toast.LENGTH_SHORT).show()
                    val usuario = ctrUsuario.buscaUsuario(email)
                    val mainActivity = Intent(this, MainActivity::class.java)
                    mainActivity.putExtra(USUARIO, usuario)
                    startActivityForResult(mainActivity, 0)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this, "Falha ao autenticar", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.recuperarSenhaBt -> {
                startActivity(Intent(this, RecuperarSenhaActivity::class.java))
            }
        }
    }

    private fun posLoginSucess() {
        val email = activityAutenticacaoBinding.emailEt.toString()
        val senha = activityAutenticacaoBinding.senhaEt.toString()
        AutenticadorFirebase.firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {}
    }
}