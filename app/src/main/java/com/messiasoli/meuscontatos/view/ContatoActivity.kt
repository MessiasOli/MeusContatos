package com.messiasoli.meuscontatos.view

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.R
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.databinding.ActivityContatoBinding
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Contato

class ContatoActivity : AppCompatActivity() {
    //Classe de ViewBinding
    private lateinit var activityContatosBinding: ActivityContatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContatosBinding = ActivityContatoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_contato)

        // Novo contato ou editar contato
        val contato: Contato? = intent.getParcelableExtra(MainActivity.Extras.EXTRA_CONTATO)

        if (contato != null){
          // Editar contato
            activityContatosBinding.nomeContatoEt.setText(contato.nome)
            activityContatosBinding.nomeContatoEt.isEnabled = false
            activityContatosBinding.telefoneContatoEt.setText(contato.telefone)
            activityContatosBinding.emailContatoEt .setText(contato.email)

            if (intent.action == MainActivity.Extras.VISUALIZAR_CONTATO_ACTION){
                // Visualizar contato
                activityContatosBinding.telefoneContatoEt.isEnabled = false
                activityContatosBinding.emailContatoEt.isEnabled = false
                activityContatosBinding.salvarBt.visibility = View.GONE
            }
        }

        activityContatosBinding.salvarBt.setOnClickListener {
            val novoContato = Contato(
                activityContatosBinding.nomeContatoEt.text.toString(),
                activityContatosBinding.telefoneContatoEt.text.toString(),
                activityContatosBinding.emailContatoEt.text.toString()
            )
            val retornoIntent = Intent()
            retornoIntent.putExtra(MainActivity.Extras.EXTRA_CONTATO, novoContato)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}