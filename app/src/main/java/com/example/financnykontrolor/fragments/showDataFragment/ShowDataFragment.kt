package com.example.financnykontrolor.fragments.showDataFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.financnykontrolor.MainActivity
import com.example.financnykontrolor.R
import com.example.financnykontrolor.databinding.FragmentShowDataBinding

/**
 * AddDataFragment is Fragment for showing data from database
 * through this Fragment you can go to edit them or delete them
 *
 */
class ShowDataFragment : Fragment() {

    private lateinit var binding: FragmentShowDataBinding
    private lateinit var viewModel: ShowDataViewModel
    val _viewModel : ShowDataViewModel
            get() = viewModel

    private lateinit var adapter : DataAdapter

    /**
     * Create viewModel and define application, dataSource,
     * showDataViewModelFactory and showDataViewModel
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

        binding = FragmentShowDataBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = (activity as MainActivity).getSource()

        val showDataViewModelFactory = ShowDataViewModelFactory(dataSource, application)

        val showDataViewModel = ViewModelProvider(this, showDataViewModelFactory)[ShowDataViewModel::class.java]

        viewModel = showDataViewModel

        binding.showDataViewModel = viewModel

        adapter = DataAdapter(this)

        binding.dataList.adapter = adapter


        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
            }
        }

        binding.lifecycleOwner = this

        return binding.root
    }

    /**
     * for going to update data
     *
     * @param id id of the data
     */
    fun updateData(id : Long) {
        goToAddDataFragment()
        (activity as MainActivity).setIdData(id)
    }

    /**
     * for go to the add data Fragment
     *
     */
    private fun goToAddDataFragment() {
        requireView().findNavController().navigate(R.id.addDataFragment)
    }
}


