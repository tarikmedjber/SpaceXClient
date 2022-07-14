package com.tarikmedjber.spacexclient.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tarikmedjber.spacexclient.R
import com.tarikmedjber.spacexclient.databinding.FragmentHomeBinding
import com.tarikmedjber.spacexclient.utils.CurrencyFormatter.asCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModel<HomeViewModelImpl>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFilterSection()
        binding.refreshCompanyInfo.setOnClickListener {
            getCompanyInfo()
        }
        homeViewModel.companyInfoState.observe(
            viewLifecycleOwner,
            companyInfoObserver
        )
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

    private fun getCompanyInfo() {
        homeViewModel.getCompanyInfo()
    }

    private val companyInfoObserver =
        Observer<HomeViewModel.State> {
            when (it) {
                HomeViewModel.State.Error -> {
                    binding.refreshCompanyInfo.visibility = View.GONE
                    binding.companyInfoText.visibility = View.GONE
                    binding.companyInfoProgressBar.visibility = View.GONE
                    showErrorDialog()
                }
                HomeViewModel.State.Loading -> {
                    binding.refreshCompanyInfo.visibility = View.GONE
                    binding.companyInfoProgressBar.visibility = View.VISIBLE
                    binding.companyInfoText.visibility = View.GONE
                }
                is HomeViewModel.State.Success -> {
                    binding.refreshCompanyInfo.visibility = View.GONE
                    binding.companyInfoText.visibility = View.VISIBLE
                    binding.companyInfoProgressBar.visibility = View.GONE

                    val companyName = homeViewModel.companyInfo?.name
                    val founder = homeViewModel.companyInfo?.founder
                    val founded = homeViewModel.companyInfo?.founded
                    val employees = homeViewModel.companyInfo?.employees
                    val launchSites = homeViewModel.companyInfo?.launchSites
                    val valuation = homeViewModel.companyInfo?.valuation?.asCurrency()

                    binding.companyInfoText.text = getString(
                        R.string.company_info,
                        companyName,
                        founder,
                        founded,
                        employees,
                        launchSites,
                        valuation
                    )
                }
            }
        }

    private fun showErrorDialog() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(getString(R.string.error_text))
                .setPositiveButton(getString(R.string.try_again)) { _, _ ->
                    getCompanyInfo()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    binding.refreshCompanyInfo.visibility = View.VISIBLE
                    dialog.dismiss()
                }
                .show()
        }
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