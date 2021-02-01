package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model

interface ContatoDao {
    fun createContato(contato: Contato)
    fun readContato(nome: String): Contato
    fun readContatos(idUsuario: Int): MutableList<Contato>
    fun updateContato(contato: Contato)
    fun deleteContato(nome: String)
    fun getMaxId() : Int
}