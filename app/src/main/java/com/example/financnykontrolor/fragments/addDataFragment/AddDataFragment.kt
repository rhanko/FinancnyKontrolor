package com.example.financnykontrolor.fragments.addDataFragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.financnykontrolor.MainActivity
import com.example.financnykontrolor.R
import com.example.financnykontrolor.databinding.FragmentAddDataBinding
import com.example.financnykontrolor.getDatefromMilliseconds

/**
 * AddDataFragment is Fragment for adding new data to database or for edit them
 *
 */
class AddDataFragment : Fragment() {

    private lateinit var binding: FragmentAddDataBinding
    private lateinit var viewModel: AddDataViewModel

    /**
     * Create viewModel and define application, dataSource,
     * addDataViewModelFactory and addDataViewModel
     *
     * @param inflater instant of a layout XML
     * @param container view which contains other views
     * @param savedInstanceState bundle object of the activity
     * @return return root of the fragment add data binding
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        binding = FragmentAddDataBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = (activity as MainActivity).getSource()

        val addDataViewModelFactory = AddDataViewModelFactory(dataSource, application, this)

        val addDataViewModel = ViewModelProvider(this, addDataViewModelFactory)[AddDataViewModel::class.java]

        viewModel = addDataViewModel

        binding.lifecycleOwner = this

        binding.addDataViewModel = viewModel

        viewModel.setId((activity as MainActivity).getIdData()!!)

        if (viewModel.idA.value!! > 0) {
            viewModel.initializeDataFromDatabase()
        }

        return binding.root
    }

    /**
     * Fun which is working with view after create
     * on click on button what can happend
     * if click on saveButton -> save data and go to ShowDataFragment
     * if click on againButton -> give back old data ( if is edit) or clear it (if you add new data)
     * if click on deleteButton -> unsave data, stay old data and go to ShowDataFragment
     *
     * @param view view which is created and using
     * @param savedInstanceState bundle object of the activity
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveDataButton.setOnClickListener {

            if (saveData()) {
                goToShowDataFragment()
            } else {
                viewModel.initializeDataFromDatabase()
            }
        }

        binding.againDataButton.setOnClickListener {
            reinitialization(true)
        }

        binding.deleteDataButton.setOnClickListener {
            reinitialization(false)
            goToShowDataFragment()
        }
    }

    /**
     * For go to the show data Fragment
     *
     */
    private fun goToShowDataFragment() {
        requireActivity().onBackPressed()
        requireView().findNavController().navigate(R.id.showDataFragment)
    }

    /**
     * For save Data to Database
     *
     * @return true if saved the data, else false
     */
    private fun saveData() : Boolean {

        val amountString = binding.editAmount.text.toString()
        val categoryString = binding.editCategory.text.toString()
        val dateString = binding.editDate.text.toString()
        val descString = binding.editDescription.text.toString()


        if (amountString.isNotEmpty()) {
            viewModel.setAmount(amountString.toDouble())
        }

        if (binding.income.isChecked) {
            viewModel.setType(true)
        } else if (binding.expense.isChecked){
            viewModel.setType(false)
        }

        if (categoryString.isNotEmpty()) {
            viewModel.setCategory(categoryString)
        }

        if (dateString.isNotEmpty()) {
            viewModel.setDate(dateString)
        }

        if (descString.isNotEmpty()) {
            viewModel.setDescription(descString)
        }

        if (viewModel.saveData()) {
            return true
        }
        showPopUpWindow(resources.getString(R.string.error), resources.getString(R.string.error_message))
        return false
    }

    /**
     * show pop-up window with one button "OK"
     *
     * @param title title of the pop-up window
     * @param message message of the pop-up window
     */
    private fun showPopUpWindow(title : String, message : String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.ok), null)
            .show()

    }

    /**
     * For a new start to adding data (removes written texts)
     * If it editing existed, it return back to original data
     *
     * @param reinitialization true if send message, else false
     */
    fun reinitialization(reinitialization : Boolean) {
        if (viewModel.idA.value!! <= 0) {
            binding.editAmount.text.clear()
            binding.type.check(binding.expense.id)
            binding.editCategory.text.clear()
            binding.editDate.text.clear()
            binding.editDescription.text.clear()

            viewModel.reinitializeData()
        } else {

            binding.editAmount.setText(viewModel.amountA.value.toString())
            binding.editCategory.setText(viewModel.categoryA.value.toString())
            binding.editDate.setText(getDatefromMilliseconds(viewModel.dateA.value!!))
            binding.editDescription.setText(viewModel.descriptionA.value.toString())

            if (viewModel.typeA.value == true) {
                binding.type.check(binding.income.id)
            } else {
                binding.type.check(binding.expense.id)
            }
        }

        if (reinitialization) {
            showPopUpWindow(resources.getString(R.string.info), resources.getString(R.string.reinitialization_message))
        }
    }
}