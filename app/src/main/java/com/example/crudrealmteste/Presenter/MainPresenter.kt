package com.example.crudrealmteste.Presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.crudrealmteste.Model.DataModel
import io.realm.Case
import io.realm.Realm

class MainPresenter(private var view: MainContract.View?, private val context: Context) :
    MainContract.Presenter {

    private val realm: Realm = Realm.getDefaultInstance()

    override fun onInsertClicked() {
        view?.showInsertDialog()
    }

    override fun onInsertData(dataModel: DataModel) {
        realm.executeTransaction { realm ->
            insertOrUpdateData(dataModel)
        }
    }

    private fun insertOrUpdateData(dataModel: DataModel) {
        val currentId = realm.where(DataModel::class.java).max("id")
        val nextId = currentId?.toLong()?.plus(1) ?: 1

        dataModel.id = nextId
        realm.copyToRealm(dataModel)
    }

    private fun updateData(updatedDataModel: DataModel, context: Context) {
        realm.executeTransactionAsync({ realm ->
            val existingDataModel =
                realm.where(DataModel::class.java).equalTo("id", updatedDataModel.id).findFirst()
            existingDataModel?.apply {//
            }
        }, {
            Log.d("UpdateData", "Atualização bem-sucedida")
        }, {
            Log.e("UpdateData", "Erro na atualização", it)
        })
    }

    override fun onUpdateClicked(dataModel: DataModel) {
        updateData(dataModel, context)
    }

    override fun onSearchClicked(query: String) {
        view?.onSearchClicked(query)
    }

    override fun onDeleteClicked(position: Int) {

        val dataModel = realm.where(DataModel::class.java).findAll()[position]
        realm.executeTransaction { realm ->
            val dataModelToDelete =
                realm.where(DataModel::class.java).equalTo("id", dataModel?.id).findFirst()
            dataModelToDelete?.deleteFromRealm()
        }
    }

    override fun detachView() {
        view = null
    }

    override fun performSearch(query: String) {
        val realm = Realm.getDefaultInstance()

        if (query.isEmpty()) {
            view?.showEmptySearchMessage() // Exibir mensagem de campo de busca vazio
            return
        }

        val searchResults = realm.where(DataModel::class.java)
            .beginsWith("name", query, Case.INSENSITIVE)
            .findAll()

        if (searchResults.isNotEmpty()) {
            view?.showSearchResults(searchResults)
        } else {
            view?.showEmptySearchResults()
        }

        realm.close()
    }

}






