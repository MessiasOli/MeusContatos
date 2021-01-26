package com.messiasoli.meuscontatos.adapter

interface OnContatoClickListener {
    fun onContatoClick(position: Int)

    // Funções adicionada para ContextoMenu
    fun onEditarMenuItemClick(posicao: Int)
    fun onRemoverMenuItemClick(posicao: Int)
}