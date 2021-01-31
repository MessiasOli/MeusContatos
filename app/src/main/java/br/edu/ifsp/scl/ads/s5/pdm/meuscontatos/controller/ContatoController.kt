package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.controller

import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.*
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view.MainActivity

class ContatoController() {
    val contatoDao: ContatoDao
    init {
        //contatoDao = ContatoSqlite(mainActivity)
        //contatoDao = ContatoSharedPreferences(mainActivity)
        contatoDao = ContatoFirebase()
    }

    fun insereContato(contato: Contato) = contatoDao.createContato(contato)
    fun buscaContato(nome: String) = contatoDao.readContato(nome)
    fun buscaContatos(idUsuario: Int) = contatoDao.readContatos(idUsuario)
    fun atualizaContato(contato: Contato) = contatoDao.updateContato(contato)
    fun removeContato(nome: String) = contatoDao.deleteContato(nome)
    fun buscaMaiorIdContatos() : Int {
        var id = contatoDao.getMaxId()
        if (id == null) id = 0
        return ++id
    }
}