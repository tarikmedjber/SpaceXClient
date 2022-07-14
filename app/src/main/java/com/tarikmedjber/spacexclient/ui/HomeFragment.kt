package com.tarikmedjber.spacexclient.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tarikmedjber.spacexclient.R
import com.tarikmedjber.spacexclient.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.custom_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                if (binding.filter.filterSection.isVisible) {
                    binding.filter.filterSection.visibility = View.GONE
                } else {
                    binding.filter.filterSection.visibility = View.VISIBLE
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFilterSection()
    }

    private fun setUpFilterSection() {
        binding.filter.filterRadioGroup.setOnCheckedChangeListener { _, optionId ->
            when (optionId) {
                R.id.launch_date -> {
                }
                R.id.successful_launch -> {
                }
            }
        }

        binding.filter.sortRadioGroup.setOnCheckedChangeListener { _, optionId ->
            when (optionId) {
                R.id.ascending -> {
                }
                R.id.descending -> {
                }
            }

        }

    }

}