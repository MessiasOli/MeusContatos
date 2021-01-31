package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Usuario (
    val id : Int = 0,
    val nome: String = "",
    val email: String = "",
): Parcelable
