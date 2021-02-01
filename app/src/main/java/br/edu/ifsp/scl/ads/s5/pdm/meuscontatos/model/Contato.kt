package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contato(
    val id : Int = 0,
    val nome: String = "",
    var telefone: String = "",
    var email: String = "",
    var idUsuario: Int = 0
): Parcelable