package com.example.crudrealmteste.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.crudrealmteste.R
import io.realm.Realm
import io.realm.RealmResults

class DataModelAdapter(private var dataModels: RealmResults<DataModel>) :
    RecyclerView.Adapter<DataModelAdapter.ViewHolder>() {

    interface OnEditClickListener {
        fun onEditClick(dataModel: DataModel)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(dposition: Int)
    }

    var editClickListener: OnEditClickListener? = null
    var onDeleteClickListener: OnDeleteClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataModels[position]
        if (dataModel != null) {
            holder.bind(dataModel)
        }
    }

    override fun getItemCount(): Int {
        return dataModels.size
    }

    fun deleteItem(position: Int) {
        val dataModelToDelete = dataModels[position]
        if (dataModelToDelete != null) {
            Realm.getDefaultInstance().executeTransaction { realm ->
                dataModelToDelete.deleteFromRealm()
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount - position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btn_Edit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.ic_delete)
        private val nameTextView: TextView = itemView.findViewById(R.id.editName)
        private val cpfCnpjTextView: TextView = itemView.findViewById(R.id.editCpfCnpj)
        private val email: TextView = itemView.findViewById(R.id.editEmail)
        private val telefoneTextView: TextView = itemView.findViewById(R.id.editTel)
        private val btnEndResid: TextView = itemView.findViewById(R.id.editResidencial)
        private val btnEndCome: TextView = itemView.findViewById(R.id.editComercial)

        init {

            btnDelete.setOnClickListener {
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.apply {
                    setTitle("Confirmar exclusão")
                    setMessage("Tem certeza de que deseja excluir este item?")
                    setPositiveButton("Sim") { dialog, _ ->
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            deleteItem(position)
                            onDeleteClickListener?.onDeleteClick(position)
                        }
                        dialog.dismiss()
                    }
                    setNegativeButton("Não") { dialog, _ ->
                        dialog.dismiss()
                    }
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

            btnEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val dataModel = dataModels[position]
                    if (dataModel != null) {
                        editClickListener?.onEditClick(dataModel)
                    }
                }
            }
        }

        fun bind(dataModel: DataModel) {
            nameTextView.text = dataModel.name
            cpfCnpjTextView.text = dataModel.cpfCnpj
            telefoneTextView.text = dataModel.telefone
            email.text = dataModel.email
            btnEndResid.text = dataModel.enderecoResidencial
            btnEndCome.text = dataModel.enderecoComercial
        }
    }
}