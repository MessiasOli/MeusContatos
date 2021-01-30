package com.messiasoli.meuscontatos.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ActionMenuView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.R
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Contato
import com.messiasoli.meuscontatos.adapter.ContatosAdapter
import com.messiasoli.meuscontatos.adapter.OnContatoClickListener
import com.messiasoli.meuscontatos.controller.ContatoController
import com.messiasoli.meuscontatos.view.MainActivity.Extras.EXTRA_CONTATO
import com.messiasoli.meuscontatos.view.MainActivity.Extras.VISUALIZAR_CONTATO_ACTION

class MainActivity : AppCompatActivity(), OnContatoClickListener {
    // Data source do Adapter
    private lateinit var contatoList: MutableList<Contato>

    private lateinit var contatosAdapter: ContatosAdapter

    private lateinit var contatosLayoutManager: LinearLayoutManager

    private lateinit var activityMainBinding: ActivityMainBinding

    // Constates para a ContatoActivity
    private val NOVO_CONTATO_REQUEST_CODE = 0
    private val EDITAR_CONTATO_REQUEST_CODE = 1
    object Extras {
        val EXTRA_CONTATO = "EXTRA_CONTATO"
        val VISUALIZAR_CONTATO_ACTION = "VISUALIZAR_CONTATO_ACTION"
    }


    //Controller
    private lateinit var contatosController: ContatoController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(activityMainBinding.root)

        //Instanciar controller
        contatosController = ContatoController(mainActivity = MainActivity())

        //Inicializando Lista de contatos para o Adapter
        contatoList = mutableListOf()
        val populaContatosListAt = @SuppressLint("StaticFieldLeak")
        object: AsyncTask<Void, Void, List<Contato>>(){
            override fun doInBackground(vararg params: Void?): List<Contato> {
            // Thread Filha
            Thread.sleep(2000)
            return contatosController.buscaContatos()
            }

            override fun onPreExecute() {
                super.onPreExecute()
                activityMainBinding.contatosListPb.visibility = View.VISIBLE
                activityMainBinding.listaContatosRv.visibility = View.GONE
            }

            override fun onPostExecute(result: List<Contato>?) {
                super.onPostExecute(result)
                //Thread de Gui
                activityMainBinding.contatosListPb.visibility = View.GONE
                activityMainBinding.listaContatosRv.visibility = View.VISIBLE
                if (result != null) {
                    contatoList.clear()
                    contatoList.addAll(result)
                    contatosAdapter.notifyDataSetChanged()
                }
            }
        }

        populaContatosListAt.execute()

        //Instanciando o LayoutManager
        contatosLayoutManager = LinearLayoutManager(this)

        //Intanciando
        contatosAdapter = ContatosAdapter(contatoList, this)

        //Associar o Adapter e o LayoutManager com o RecyclerView
        activityMainBinding.listaContatosRv.adapter = contatosAdapter
        activityMainBinding.listaContatosRv.layoutManager = contatosLayoutManager
    }

    override fun onContatoClick(position: Int) {
        //Recuperar o contato clicado
        val contato: Contato = contatoList[position]

        val visualizarContatoIntent = Intent(this, ContatoActivity::class.java)
        visualizarContatoIntent.putExtra(EXTRA_CONTATO, contato)
        visualizarContatoIntent.action = VISUALIZAR_CONTATO_ACTION

        startActivity(visualizarContatoIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.novoContatoMi){
            val novoContatoIntent = Intent(this, ContatoActivity::class.java)
            startActivityForResult(novoContatoIntent, NOVO_CONTATO_REQUEST_CODE)
            true
        }else
            false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == NOVO_CONTATO_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            val novoContato = data.getParcelableExtra<Contato>(EXTRA_CONTATO)
            if (novoContato != null){
                contatosController.insereContato(novoContato)
                contatoList.add(novoContato)
                contatosAdapter.notifyDataSetChanged()
            }
        }
        else {
            if(requestCode == EDITAR_CONTATO_REQUEST_CODE && resultCode == RESULT_OK && data != null){
                val contatoEditado: Contato? = data.getParcelableExtra<Contato>(EXTRA_CONTATO)
                if (contatoEditado != null){
                    //Atualizar no banco
                    contatosController.atualizaContato(contatoEditado)


                    contatoList[contatoList.indexOfFirst { it.nome.equals(contatoEditado.nome) }] = contatoEditado
                    contatosAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onEditarMenuItemClick(posicao: Int) {
        val contatoSelecionado: Contato = contatoList[posicao]

        val editarContatoIntent = Intent(this, ContatoActivity::class.java)
        editarContatoIntent.putExtra(EXTRA_CONTATO, contatoSelecionado)
        startActivityForResult(editarContatoIntent, EDITAR_CONTATO_REQUEST_CODE)
    }

    override fun onRemoverMenuItemClick(position: Int) {
        val contatoExcluido = contatoList[position]

        if(position != -1){
            contatosController.removeContato(contatoExcluido.nome)

            contatoList.removeAt(position)
            contatosAdapter.notifyDataSetChanged()
        }
    }

}