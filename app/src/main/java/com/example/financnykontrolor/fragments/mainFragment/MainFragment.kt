package com.example.financnykontrolor.fragments.mainFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.androidplot.pie.PieRenderer
import com.androidplot.pie.SegmentFormatter
import com.example.financnykontrolor.MainActivity
import com.example.financnykontrolor.databinding.FragmentMainBinding

/**
 * Fragment where the graph is placed and also it's main fragment
 *
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    /**
     * Create viewModel and define application, dataSource,
     * mainViewModelFactory and mainDataViewModel
     *
     * @param inflater instant of a layout XML
     * @param container view which contains other views
     * @param savedInstanceState bundle object of the activity
     * @return return root of the fragment add data binding
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = (activity as MainActivity).getSource()

        val mainViewModelFactory = MainViewModelFactory(dataSource, application, this)

        val mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        viewModel = mainViewModel

        binding.lifecycleOwner = this

        viewModel.setAllData()

        return binding.root
    }

    /**
     * Fun which is working with view after create
     * after click on button can happend one of 3 situations:
     * 1st you do not set none of the dates or set only date to... to graph give all data
     * 2nd set only date from and not date to ... to graph give data from this date
     * 3rd set both dates... to graph give data between this dates
     *
     * @param view view which is created and using
     * @param savedInstanceState bundle object of the activity
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.datesButton.setOnClickListener {
            val dateFrom = binding.editFromDate.text.toString()
            val dateTo = binding.editToDate.text.toString()

            when {
                dateFrom.isEmpty() -> {
                    viewModel.setAllData()
                    binding.editFromDate.text.clear()
                    binding.editToDate.text.clear()
                }
                dateTo.isEmpty() -> {
                    viewModel.setDataFrom(dateFrom)
                    binding.editToDate.text.clear()
                }
                else -> {
                    viewModel.setDataFromTo(dateFrom, dateTo)
                }
            }
        }
    }

    /**
     * add data into graph and redraw him
     *
     */
    fun addDataToGraph() {
        binding.graph.clear()
        binding.graph.addSegment(viewModel.segmentIncome.value, SegmentFormatter(Color.GREEN))
        binding.graph.addSegment(viewModel.segmentExpense.value, SegmentFormatter(Color.RED))

        binding.graph.getRenderer(PieRenderer::class.java).startDegs = 90F
        binding.graph.redraw()
    }

}