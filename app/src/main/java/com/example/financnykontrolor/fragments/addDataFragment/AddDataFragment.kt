package com.example.financnykontrolor.fragments.addDataFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.financnykontrolor.database.DataDatabase
import com.example.financnykontrolor.databinding.FragmentAddDataBinding

/**
 *
 */
class AddDataFragment : Fragment() {

    private lateinit var binding: FragmentAddDataBinding
    private lateinit var viewModel: AddDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {

        binding = FragmentAddDataBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = DataDatabase.getInstance(application).dataDatabaseDao

        val addDataViewModelFactory = AddDataViewModelFactory(dataSource, application)

        val addDataViewModel = ViewModelProvider(this, addDataViewModelFactory)[AddDataViewModel::class.java]

        viewModel = addDataViewModel

        binding.lifecycleOwner = this

        binding.addDataViewModel = addDataViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveDataButton.setOnClickListener {saveData()}
    }

    fun saveData() {
        Log.i("saveData", "starting...")
        viewModel.setAmount(binding.editAmount.text.toString().toDouble())

        Log.i("setAmount :D", "amount was added")

        if (binding.income.isChecked) {
            viewModel.setType(true)
        } else if (binding.expense.isChecked){
            viewModel.setType(false)
        }
        Log.i("setType :D", "type was added")

        viewModel.setCategory(binding.category.text.toString())
        Log.i("setCategory :D", "category was added")

        viewModel.setDate(binding.editDate.text.toString().toLong())
        Log.i("setDate :D", "date was added")

        viewModel.setDescription(binding.editDescription.text.toString())
        Log.i("setDesc :D", "desc was added")

        viewModel.saveData()
        Log.i("saveData", "data was sucessfully saved")
    }
}