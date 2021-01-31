package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.controller.ContatoController
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.databinding.ActivityContatoBinding
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Contato
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Usuario

class ContatoActivity : AppCompatActivity() {
    // Classe de ViewBinding
    private lateinit var activityContatoBinding: ActivityContatoBinding
    private lateinit var ctrContato : ContatoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContatoBinding = ActivityContatoBinding.inflate(layoutInflater)
        ctrContato = ContatoController()
        setContentView(activityContatoBinding.root)
        getSupportActionBar()?.setSubtitle("Cadastro de contatos");


        // Novo contato ou editar contato
        val contato: Contato? = intent.getParcelableExtra(MainActivity.Extras.EXTRA_CONTATO)
        if (contato != null) {
            // Editar contato
            activityContatoBinding.nomeContatoEt.setText(contato.nome)
            activityContatoBinding.nomeContatoEt.isEnabled = false
            activityContatoBinding.telefoneContatoEt.setText(contato.telefone)
            activityContatoBinding.emailContatoEt.setText(contato.email)


            if (intent.action == MainActivity.Extras.VISUALIZAR_CONTATO_ACTION) {
                // Visualizar contato
                activityContatoBinding.telefoneContatoEt.isEnabled = false
                activityContatoBinding.emailContatoEt.isEnabled = false
                activityContatoBinding.salvarBt.visibility = View.GONE
            }
        }


        activityContatoBinding.salvarBt.setOnClickListener {
            val usuario : Usuario = intent.getParcelableExtra(MainActivity.Extras.USUARIO)!!

            val novoContato = Contato(
                ctrContato.buscaMaiorIdContatos(),
                activityContatoBinding.nomeContatoEt.text.toString(),
                activityContatoBinding.telefoneContatoEt.text.toString(),
                activityContatoBinding.emailContatoEt.text.toString(),
                usuario.id
            )

            val retornoIntent = Intent()
            retornoIntent.putExtra(MainActivity.Extras.EXTRA_CONTATO, novoContato)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}