package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class UsuarioFirebase {
    private val USUARIO_REALTIME_DATABASE = "usuario"
    //Referência para o nó principal que é a lista de contatos do RtDb
    private val usuarioRtDb = Firebase.database.getReference(USUARIO_REALTIME_DATABASE)

    private val usuarios: MutableList<Usuario> = mutableListOf()
    init {
        usuarioRtDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novoUsuario: Usuario = snapshot.getValue<Usuario>() ?: Usuario()

                if (usuarios.indexOfFirst { it.nome.equals(novoUsuario.nome) } == -1) {
                    usuarios.add(novoUsuario)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val usuarioEditado: Usuario = snapshot.getValue<Usuario>() ?: Usuario()

                val posicao = usuarios.indexOfFirst { it.nome.equals(usuarioEditado.nome) }
                usuarios[posicao] = usuarioEditado
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val contatoRemovido: Usuario = snapshot.getValue<Usuario>() ?: Usuario()
                usuarios.remove(contatoRemovido)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
    }

    fun createUsuario(usuario: Usuario) = createOrUpdateUsurio(usuario)

    fun readUsuario(email: String): Usuario = usuarios[usuarios.indexOfFirst { it.email.equals(email) }]

    fun getMaxId() : Int {
        var id = 0
        try {
            id = usuarios.maxOf { it.id }
            return ++id
        }catch (e : Exception) {
            return ++id
        }
    }

    fun readUsuario(): MutableList<Usuario> = usuarios

    fun updateUsuario(usuario: Usuario) = createOrUpdateUsurio(usuario)

    fun deleteUsuario(nome: String) {
        usuarioRtDb.child(nome).removeValue()
    }

    private fun createOrUpdateUsurio(usuario: Usuario) {
        usuarioRtDb.child(usuario.nome).setValue(usuario)
    }
}