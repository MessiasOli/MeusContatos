package com.messiasoli.meuscontatos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.messiasoli.meuscontatos.R
import com.messiasoli.meuscontatos.adapter.ContatosAdapter
import com.messiasoli.meuscontatos.adapter.OnContatoClickListener
import com.messiasoli.meuscontatos.model.Contato
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnContatoClickListener {
    // Data source do Adapter
    private lateinit var contatoList: MutableList<Contato>

    private lateinit var contatosAdapter: ContatosAdapter

    private lateinit var contatosLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializando Lista de contatos para o Adapter
        contatoList = mutableListOf()
        for(i in 1..50){
            contatoList.add(
                Contato(
                    "Nome $i",
                    "Telefone $i",
                    "Email $i"
                )
            )
        }

        //Instanciando o LayoutManager
        contatosLayoutManager = LinearLayoutManager(this)

        //Intanciando
        contatosAdapter = ContatosAdapter(contatoList, this)

        //Associar o Adapter e o LayoutManager com o RecyclerView
        listaContatosRv.adapter = contatosAdapter
        listaContatosRv.layoutManager = contatosLayoutManager
    }

    override fun onContatoClick(position: Int) {
        //Recuperar o contato clicado
        val contato: Contato = contatoList[position]
        Toast.makeText(this, contato.nome, Toast.LENGTH_SHORT).show()
    }
}