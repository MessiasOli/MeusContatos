package com.messiasoli.meuscontatos.controller

import com.messiasoli.meuscontatos.model.Contato
import com.messiasoli.meuscontatos.model.ContatoDao
import com.messiasoli.meuscontatos.model.ContatoFirebase
import com.messiasoli.meuscontatos.model.ContatoSqlite
import com.messiasoli.meuscontatos.view.MainActivity

class ContatoController(mainActivity: MainActivity) {
    val contatoDao: ContatoDao
    init{
        //contatoDao = ContatoSqlite(mainActivity)
        contatoDao = ContatoFirebase()
    }

    fun insereContato(contato: Contato) = contatoDao.createContato(contato)
    fun buscaContato(nome: String) = contatoDao.readContato(nome)
    fun buscaContatos() = contatoDao.readContatos()
    fun atualizaContato(contato: Contato) = contatoDao.updateContato(contato)
    fun removeContato(nome: String) = contatoDao.deleteContato(nome)
}