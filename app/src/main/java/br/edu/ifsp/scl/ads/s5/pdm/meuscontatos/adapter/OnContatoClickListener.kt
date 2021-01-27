package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.adapter

interface OnContatoClickListener {
    fun onContatoClick(position: Int)

    // Funções adicionadas para ContextMenu
    fun onEditarMenuItemClick(position: Int)
    fun onRemoverMenuItemClick(position: Int)
}