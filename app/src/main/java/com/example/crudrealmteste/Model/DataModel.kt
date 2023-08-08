package com.example.crudrealmteste.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DataModel : RealmObject() {

    var gender: String? = null

    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var cpfCnpj: String = ""
    var telefone: String = ""
    var email: String = ""
    var enderecoResidencial: String = ""
    var enderecoComercial: String = ""

}