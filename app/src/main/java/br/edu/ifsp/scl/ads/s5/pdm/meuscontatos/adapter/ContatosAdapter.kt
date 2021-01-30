package br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.adapter

import android.provider.ContactsContract
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.R
import br.edu.ifsp.scl.ads.s5.pdm.meuscontatos.model.Contato

class ContatosAdapter(
    private val contatosList: MutableList<Contato>,
    private val onContatoClickListener: OnContatoClickListener,

): RecyclerView.Adapter<ContatosAdapter.ViewHolder>() {

    inner class ViewHolder(layoutContatoView: View) : RecyclerView.ViewHolder(layoutContatoView),
        View.OnCreateContextMenuListener {
        val nomeTv = layoutContatoView.findViewById<TextView>(R.id.nomeTv)
        val telefoneTv = layoutContatoView.findViewById<TextView>(R.id.telefoneTv)
        val emailTv = layoutContatoView.findViewById<TextView>(R.id.emailTv)

        init {
            layoutContatoView.setOnCreateContextMenuListener(this)
        }

        // Posição será setada pelo onBindViewHolder para chamar as funções de tratamento de clique para o contato correto
        private val POSICAO_INVALIDA = -1
        var posicao: Int = POSICAO_INVALIDA

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            // Criar as opções de menu e seta os listeners de clique para cada MenuItem
            menu?.add("Editar")?.setOnMenuItemClickListener {
                if (posicao != POSICAO_INVALIDA) {
                    onContatoClickListener.onEditarMenuItemClick(posicao)
                    true
                }
                false
            }
            menu?.add("Remover")?.setOnMenuItemClickListener {
                if (posicao != POSICAO_INVALIDA) {
                    onContatoClickListener.onRemoverMenuItemClick(posicao)
                    true
                }
                false
            }
        }
    }

    // Chamado pelo LayoutManager para criar uma nova View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatosAdapter.ViewHolder {
        // Cria uma nova View
        val layoutContatoView: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_contato, parent, false)

        // Criar e retorna um ViewHolder associado a nova View
        return ViewHolder(layoutContatoView)
    }

    // Chamado pelo ViewHolder para alterar o conteúdo de uma View
    override fun onBindViewHolder(holder: ContatosAdapter.ViewHolder, position: Int) {
        // Busca o contato para pegar os valores
        val contato = contatosList[position]

        // Seta os novos valores no ViewHolder
        val nomeTv = holder.nomeTv
        val telefoneTv =  holder.telefoneTv
        val emailTv =  holder.emailTv

        nomeTv.text =  contato.nome
        telefoneTv.text = "Telefone: " + contato.telefone
        emailTv.text = "E-mail: " + contato.email


        // Seta o onClickListener da View associada ao ViewHolder como uma lambda que
        // chama a função definida na interface OnContatoClick, implementada na Activity e
        // recebida como parâmetro no construtor do Adapter. Ou seja, ao ser clicada a
        // View terá chamará a função definida na Activity passando a posição
        holder.itemView.setOnClickListener{
            onContatoClickListener.onContatoClick(position)
        }
        // Setando a posição para o ContextMenu
        holder.posicao = position
    }

    // Chamado pelo LayoutManager para buscar a quantidade de dados e preparar a quanti
    // dade de Views e ViewHolders
    override fun getItemCount(): Int = contatosList.size
}