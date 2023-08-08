package com.example.crudrealmteste.Presenter

import com.example.crudrealmteste.Model.DataModel
import io.realm.RealmResults

interface MainContract {
    interface View {
        fun showInsertDialog()
        fun showUpdateDialog(dataModel: DataModel)
        fun onSearchClicked(query: String)
        fun showSearchResults(searchResults: RealmResults<DataModel>)
        fun showEmptySearchResults()
        fun showEmptySearchMessage()
    }

    interface Presenter {
        fun onInsertClicked()
        fun onUpdateClicked(dataModel: DataModel)
        fun onInsertData(dataModel: DataModel)
        fun detachView()
        fun onDeleteClicked(position: Int)
        fun onSearchClicked(query: String)
        fun performSearch(query: String)

    }
}
