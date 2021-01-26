package com.messiasoli.meuscontatos.model

interface ContatoDao {
    fun createContato(contato: Contato)
    fun readContato(nome: String): Contato
    fun readContatos(): MutableList<Contato>
    fun updateContato(contato: Contato)
    fun deleteContato(nome: String)
}