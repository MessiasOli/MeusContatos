package com.messiasoli.meuscontatos.model

import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Contato

interface ContatoDao {
    fun createContato(contato: Contato)
    fun readContato(nome: String): Contato
    fun readContatos(): MutableList<Contato>
    fun updateContato(contato: Contato)
    fun deleteContato(nome: String)
}