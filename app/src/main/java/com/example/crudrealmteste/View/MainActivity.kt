package com.example.crudrealmteste.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudrealmteste.Model.DataModelAdapter
import com.example.crudrealmteste.Presenter.MainContract
import com.example.crudrealmteste.Presenter.MainPresenter
import com.example.crudrealmteste.Model.DataModel
import com.example.crudrealmteste.R
import com.example.crudrealmteste.Utils.ValidationUtils
import com.example.crudrealmteste.Utils.ValidationUtils.Companion.isCNPJValid
import com.example.crudrealmteste.Utils.ValidationUtils.Companion.isCPFValid
import com.example.crudrealmteste.Utils.ValidationUtils.Companion.isEmailValid
import com.example.crudrealmteste.Utils.ValidationUtils.Companion.isFieldValid
import com.example.crudrealmteste.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.RealmResults


class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener,
    DataModelAdapter.OnEditClickListener, DataModelAdapter.OnDeleteClickListener {

    private lateinit var realm: Realm

    private lateinit var presenter: MainContract.Presenter
    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DataModelAdapter

    private var deleteItemId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getDefaultInstance()

        presenter = MainPresenter(this, applicationContext)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        loadListData()

        adapter = DataModelAdapter(realm.where(DataModel::class.java).findAll())
        adapter.editClickListener = this
        adapter.onDeleteClickListener = this
        recyclerView.adapter = adapter

        binding.btnNovoUsuario.setOnClickListener(this)
        binding.btnPesquisar.setOnClickListener(this)

        binding.edtPesquisar

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        presenter.detachView()
    }

    private fun loadListData() {
        val dataModels: RealmResults<DataModel> = realm.where(DataModel::class.java).findAll()
        adapter = DataModelAdapter(dataModels)
        recyclerView.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btnNovoUsuario -> presenter.onInsertClicked()


            R.id.btnPesquisar -> {
                val query = binding.edtPesquisar.text.toString()
                presenter.performSearch(query)
            }
        }
    }

    override fun showSearchResults(searchResults: RealmResults<DataModel>) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.search_results_dialog, null)
        builder.setView(dialogView)
        val dialog = builder.create()

        val recyclerView: RecyclerView = dialogView.findViewById(R.id.searchResultsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Usar o adaptador correto com o construtor adequado
        val adapter = DataModelAdapter(searchResults)
        adapter.onDeleteClickListener = object : DataModelAdapter.OnDeleteClickListener {
            override fun onDeleteClick(position: Int) {
                refreshItemList()
                dialog.dismiss()
            }
        }

        adapter.editClickListener = object : DataModelAdapter.OnEditClickListener {
            override fun onEditClick(dataModel: DataModel) {
                showUpdateDialog(dataModel)
                dialog.dismiss()
            }
        }
        recyclerView.adapter = adapter

        dialog.show()
    }

    override fun showEmptySearchResults() {
        Toast.makeText(this, "Nenhum resultado encontrado", Toast.LENGTH_SHORT).show()
    }

    override fun showInsertDialog() {
        val al = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.data_input_dialog, null)
        al.setView(view)

        val name: EditText = view.findViewById(R.id.name)
        val cpfCnpj: EditText = view.findViewById(R.id.cpfCnpj)
        val telefone: EditText = view.findViewById(R.id.telefone)
        val email: EditText = view.findViewById(R.id.email)
        val enderecoResidencial: EditText = view.findViewById(R.id.enderecoResid)
        val enderecoComercial: EditText = view.findViewById(R.id.enderecoCome)
        val gender: Spinner = view.findViewById(R.id.gender)
        val save: Button = view.findViewById(R.id.save)
        val alertDialog = al.show()

        val tipoPessoaArray = arrayOf("Tipo Pessoa", "Cpf", "Cnpj")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoPessoaArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender.adapter = adapter
        gender.setSelection(0)

        save.setOnClickListener {

            val selectedGender = gender.selectedItem.toString()

            if (selectedGender.equals("Tipo Pessoa")) {
                Toast.makeText(this, "Escolha o tipo de pessoa (CPF ou CNPJ)", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (isFieldValid(name, "Nome") && isFieldValid(cpfCnpj, "CPF/CNPJ") &&
                isFieldValid(telefone, "Telefone") && isFieldValid(email, "Email")
            ) {
                val selectedGender = gender.selectedItem.toString()

                val cpfCnpjValue = cpfCnpj.text.toString()
                val isCPF = selectedGender.equals("Cpf", ignoreCase = true)

                val isFieldValid = if (isCPF) {
                    isCPFValid(cpfCnpjValue)
                } else {
                    isCNPJValid(cpfCnpjValue)
                }

                if (!isFieldValid) {
                    val errorMessage =
                        if (isCPF) "CPF inválido. Insira um CPF válido." else "CNPJ inválido. Insira um CNPJ válido."
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val isEmailValid = isEmailValid(email.text.toString())
                if (!isEmailValid) {
                    Toast.makeText(
                        this,
                        "Email inválido. Insira um email válido.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }


                alertDialog.dismiss()

                val dataModel = DataModel()

                val currentId = realm.where(DataModel::class.java).max("id")
                val nextId = currentId?.toLong()?.plus(1) ?: 1

                dataModel.id = nextId
                dataModel.name = name.text.toString()
                dataModel.gender = gender.selectedItem.toString()
                dataModel.cpfCnpj = cpfCnpj.text.toString()
                dataModel.telefone = telefone.text.toString()
                dataModel.email = email.text.toString()
                dataModel.enderecoResidencial = enderecoResidencial.text.toString()
                dataModel.enderecoComercial = enderecoComercial.text.toString()

                presenter.onInsertData(dataModel) // Chama o método do Presenter para inserir os dados.
            }
        }
    }

    override fun onEditClick(dataModel: DataModel) {
        showUpdateDialog(dataModel)
    }

    override fun showUpdateDialog(dataModel: DataModel) {
        val al = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.data_input_dialog, null)
        al.setView(view)

        val name: EditText = view.findViewById(R.id.name)
        val cpfCnpj: EditText = view.findViewById(R.id.cpfCnpj)
        val telefone: EditText = view.findViewById(R.id.telefone)
        val email: EditText = view.findViewById(R.id.email)
        val enderecoResidencial: EditText = view.findViewById(R.id.enderecoResid)
        val enderecoComercial: EditText = view.findViewById(R.id.enderecoCome)

        val gender: Spinner = view.findViewById(R.id.gender)
        val save: Button = view.findViewById(R.id.save)
        val alertDialog = al.show()

        val tipoPessoaArray = arrayOf("Tipo Pessoa", "Cpf", "Cnpj")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoPessoaArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender.adapter = adapter
        gender.setSelection(0)

        name.setText(dataModel.name)
        cpfCnpj.setText(dataModel.cpfCnpj)
        telefone.setText(dataModel.telefone)
        email.setText(dataModel.email)
        enderecoResidencial.setText(dataModel.enderecoResidencial)
        enderecoComercial.setText(dataModel.enderecoComercial)
        val genderIndex = if (dataModel.gender.equals("Male", ignoreCase = true)) 0 else 1
        gender.setSelection(genderIndex)

        save.setOnClickListener {


            val selectedGender = gender.selectedItem.toString()

            if (selectedGender.equals("Tipo Pessoa")) {
                Toast.makeText(this, "Escolha o tipo de pessoa (CPF ou CNPJ)", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (isFieldValid(name, "Nome") && isFieldValid(cpfCnpj, "CPF/CNPJ") &&
                isFieldValid(telefone, "Telefone") && isFieldValid(email, "Email")
            ) {
                val selectedGender = gender.selectedItem.toString()

                val cpfCnpjValue = cpfCnpj.text.toString()
                val isCPF = selectedGender.equals("Cpf", ignoreCase = true)

                val isFieldValid = if (isCPF) {
                    isCPFValid(cpfCnpjValue)
                } else {
                    isCNPJValid(cpfCnpjValue)
                }

                if (!isFieldValid) {
                    val errorMessage =
                        if (isCPF) "CPF inválido. Insira um CPF válido." else "CNPJ inválido. Insira um CNPJ válido."
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val isEmailValid = isEmailValid(email.text.toString())
                if (!isEmailValid) {
                    Toast.makeText(
                        this,
                        "Email inválido. Insira um email válido.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                alertDialog.dismiss()

                realm.executeTransaction { realm ->
                    dataModel.name = name.text.toString()
                    dataModel.gender = gender.selectedItem.toString()
                    dataModel.cpfCnpj = cpfCnpj.text.toString()
                    dataModel.telefone = telefone.text.toString()
                    dataModel.email = email.text.toString()
                    dataModel.enderecoResidencial = enderecoResidencial.text.toString()
                    dataModel.enderecoComercial = enderecoComercial.text.toString()

                    realm.copyToRealmOrUpdate(dataModel)
                }

                refreshItemList()
                presenter.onUpdateClicked(dataModel)
                Toast.makeText(this, "Dados atualizadoscom sucesso.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSearchClicked(query: String) {
        presenter.performSearch(query)
    }

    override fun showEmptySearchMessage() {
        Toast.makeText(this, "Digite algo para pesquisar", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(dposition: Int) {
        deleteItemId?.toInt()?.let { presenter.onDeleteClicked(it) }
    }

    private fun refreshItemList() {
        adapter.notifyDataSetChanged()
    }

}