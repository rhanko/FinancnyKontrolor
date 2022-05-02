package com.example.financnykontrolor.fragments.mainFragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.androidplot.pie.PieChart
import com.androidplot.pie.PieRenderer
import com.androidplot.pie.Segment
import com.androidplot.pie.SegmentFormatter
import com.example.financnykontrolor.MainActivity
import com.example.financnykontrolor.databinding.FragmentMainBinding

/**
 * Fragment where the graph is placed and also it's main fragment
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    val _viewModel : MainViewModel
        get() = viewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.datesButton.setOnClickListener {
            val dateFrom = binding.editFromDate.text.toString()
            val dateTo = binding.editToDate.text.toString()

            if (dateFrom.isEmpty()) {
                viewModel.setAllData()
            } else if (dateTo.isEmpty()) {
                viewModel.setDataFrom(dateFrom)
            } else {
                viewModel.setDataFromTo(dateFrom, dateTo)
            }
        }
    }

    fun addDataToGraph() {
        val formatterIncome = SegmentFormatter(Color.GREEN)
        val formatterExpense = SegmentFormatter(Color.RED)

        binding.graph.clear()
        binding.graph.addSegment(viewModel._segmentIncome.value, formatterIncome)
        binding.graph.addSegment(viewModel._segmentExpense.value, formatterExpense)

        binding.graph.getRenderer(PieRenderer::class.java).startDegs = 90F
        binding.graph.redraw()
    }

}