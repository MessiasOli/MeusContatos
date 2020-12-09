package com.messiasoli.meuscontatos.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contato(
    val nome: String = "",
    var telefone: String = "",
    var email: String = ""
):Parcelable
