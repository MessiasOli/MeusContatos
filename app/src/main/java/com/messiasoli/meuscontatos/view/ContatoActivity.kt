package com.messiasoli.meuscontatos.view

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.messiasoli.meuscontatos.R
import com.messiasoli.meuscontatos.databinding.ActivityContatoBinding
import com.messiasoli.meuscontatos.databinding.ActivityMainBinding
import com.messiasoli.meuscontatos.databinding.LayoutContatoBinding
import com.messiasoli.meuscontatos.model.Contato
import kotlinx.android.synthetic.main.layout_contato.*

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
            nomeContatoEt.setText(contato.nome)
            nomeContatoEt.isEnabled = false
            telefoneContatoEt.setText(contato.telefone)
            emailContatoEd.setText(contato.email)

            if (intent.action == MainActivity.Extras.VISUALIZAR_CONTATO_ACTION){
                // Visualizar contato
                telefoneContatoEt.isEnabled = false
                emailContatoEd.isEnabled = false
                salvarBt.visibility = View.GONE
            }
        }

        salvarBt.setOnClickListener {
            val novoContato = Contato(
                nomeContatoEt.text.toString(),
                telefoneContatoEt.text.toString(),
                emailContatoEd.text.toString()
            )
            val retornoIntent = Intent()
            retornoIntent.putExtra(MainActivity.Extras.EXTRA_CONTATO, novoContato)
            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}