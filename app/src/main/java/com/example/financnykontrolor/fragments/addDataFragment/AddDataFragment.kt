package com.example.financnykontrolor.fragments.addDataFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.financnykontrolor.databinding.FragmentAddDataBinding

/**
 *
 */
class AddDataFragment : Fragment() {

    private var _binding: FragmentAddDataBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}