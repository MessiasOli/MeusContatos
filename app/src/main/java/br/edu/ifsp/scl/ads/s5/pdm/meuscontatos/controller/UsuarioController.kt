package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.controller

import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.*
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.view.MainActivity

class UsuarioController() {
    val usuarioDao : UsuarioFirebase
    init {
        usuarioDao = UsuarioFirebase()
    }

    fun insereUsuario(usuario: Usuario) = usuarioDao.createUsuario(usuario)
    fun buscaUsuario(email: String) = usuarioDao.readUsuario(email)
    fun buscaUsuarios() = usuarioDao.readUsuario()
    fun atualizaUsuario(usuario: Usuario) = usuarioDao.updateUsuario(usuario)
    fun removeUsuario(nome: String) = usuarioDao.deleteUsuario(nome)
    fun buscaMaiorId() = usuarioDao.getMaxId()
}