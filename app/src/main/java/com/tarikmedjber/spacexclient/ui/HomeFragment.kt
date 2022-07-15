package com.tarikmedjber.spacexclient.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tarikmedjber.spacexclient.R
import com.tarikmedjber.spacexclient.databinding.FragmentHomeBinding
import com.tarikmedjber.spacexclient.utils.CurrencyFormatter.asCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(),
    LaunchesViewHolder.OnLaunchListener {

    enum class SpaceXSection {
        COMPANY_INFO,
        LAUNCHES
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModel<HomeViewModelImpl>()
    private var homeAdapter: HomeAdapter? = null

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

        setUpLaunchesList()
        setUpFilterSection()

        binding.refreshCompanyInfo.setOnClickListener {
            getCompanyInfo()
        }

        binding.refreshLaunches.setOnClickListener {
            getLaunches()
        }

        homeViewModel.companyInfoState.observe(
            viewLifecycleOwner,
            companyInfoObserver
        )

        homeViewModel.launchesState.observe(
            viewLifecycleOwner,
            launchesObserver
        )
    }

    private fun setUpLaunchesList() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        homeAdapter = context?.let {
            HomeAdapter(this, it)
        }
        binding.launchesList.adapter = homeAdapter
        binding.launchesList.layoutManager = layoutManager

        binding.launchesList.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
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

    private fun setUpAdapter() {
        homeViewModel.launchesList?.let {
            homeAdapter?.setItems(it)
        }
    }

    private fun getCompanyInfo() {
        homeViewModel.getCompanyInfo()
    }

    val companyInfoObserver =
        Observer<HomeViewModel.State> {
            when (it) {
                HomeViewModel.State.Error -> {
                    binding.companyInfoErrorIcon.visibility = View.VISIBLE
                    binding.refreshCompanyInfo.visibility = View.GONE
                    binding.companyInfoText.visibility = View.GONE
                    binding.companyInfoProgressBar.visibility = View.GONE
                    showErrorDialog(SpaceXSection.COMPANY_INFO)
                }
                HomeViewModel.State.Loading -> {
                    binding.companyInfoErrorIcon.visibility = View.GONE
                    binding.refreshCompanyInfo.visibility = View.GONE
                    binding.companyInfoProgressBar.visibility = View.VISIBLE
                    binding.companyInfoText.visibility = View.GONE
                }
                is HomeViewModel.State.Success -> {
                    binding.companyInfoErrorIcon.visibility = View.GONE
                    binding.refreshCompanyInfo.visibility = View.GONE
                    binding.companyInfoText.visibility = View.VISIBLE
                    binding.companyInfoProgressBar.visibility = View.GONE
                    setCompanyInfoText()
                }
            }
        }

    private fun setCompanyInfoText() {
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


    private fun getLaunches() {
        homeViewModel.getLaunches(currentFiltersSelected())
    }

    val launchesObserver =
        Observer<HomeViewModel.State> {
            when (it) {
                HomeViewModel.State.Error -> {
                    binding.launchesListErrorIcon.visibility = View.VISIBLE
                    binding.refreshLaunches.visibility = View.GONE
                    binding.launchesList.visibility = View.GONE
                    binding.launchesProgressBar.visibility = View.GONE
                    showErrorDialog(SpaceXSection.LAUNCHES)
                }
                HomeViewModel.State.Loading -> {
                    binding.launchesListErrorIcon.visibility = View.GONE
                    binding.refreshLaunches.visibility = View.GONE
                    binding.launchesProgressBar.visibility = View.VISIBLE
                    binding.launchesList.visibility = View.GONE
                }
                is HomeViewModel.State.Success -> {
                    binding.launchesListErrorIcon.visibility = View.GONE
                    binding.refreshLaunches.visibility = View.GONE
                    binding.launchesList.visibility = View.VISIBLE
                    binding.launchesProgressBar.visibility = View.GONE
                    setUpAdapter()
                }
            }
        }

    private fun showErrorDialog(spaceXSection: SpaceXSection) {
        val message: String =
            if (spaceXSection == SpaceXSection.COMPANY_INFO)
                getString(R.string.company_info_error_text) else getString(
                R.string.launches_error_text
            )
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setMessage(message)
                .setPositiveButton(getString(R.string.try_again)) { _, _ ->
                    when (spaceXSection) {
                        SpaceXSection.COMPANY_INFO -> getCompanyInfo()
                        SpaceXSection.LAUNCHES -> getLaunches()
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    when (spaceXSection) {
                        SpaceXSection.COMPANY_INFO -> {
                            binding.refreshCompanyInfo.visibility = View.VISIBLE
                            binding.companyInfoErrorIcon.visibility = View.GONE
                        }
                        SpaceXSection.LAUNCHES -> {
                            binding.refreshLaunches.visibility = View.VISIBLE
                            binding.launchesListErrorIcon.visibility = View.GONE
                        }
                    }
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun setUpFilterSection() {
        binding.filter.filterRadioGroup.setOnCheckedChangeListener { _, _ ->
            homeViewModel.launchesList?.let {
                homeViewModel.filterLaunchesList(
                    currentFiltersSelected(),
                    it
                )
            }
        }

        binding.filter.sortRadioGroup.setOnCheckedChangeListener { _, _ ->
            homeViewModel.launchesList?.let {
                homeViewModel.filterLaunchesList(
                    currentFiltersSelected(),
                    it
                )
            }
        }

    }

    private fun currentFiltersSelected(): FilteredOptions {

        return if (binding.filter.launchDate.isChecked) {
            if (binding.filter.ascending.isChecked) {
                FilteredOptions.Date(OrderType.Ascending)
            } else {
                FilteredOptions.Date(OrderType.Descending)
            }
        } else {
            // success is selected
            if (binding.filter.ascending.isChecked) {
                FilteredOptions.LaunchSuccess(OrderType.Ascending)
            } else {
                FilteredOptions.LaunchSuccess(OrderType.Descending)
            }
        }
    }

    override fun onLaunchClicked(position: Int) {
        val links = homeViewModel.launchesList?.get(position)?.links
        if (links != null) {
            val linksList = mutableListOf<String>()
            links.articleLink?.let {
                linksList.add(it)
            }
            links.wikipediaLink?.let {
                linksList.add(it)
            }
            links.video_link?.let {
                linksList.add(it)
            }
            if (linksList.isNotEmpty()) {
                openLinksDialog(linksList.toTypedArray())
            }
        }
    }

    private fun openLinksDialog(linksList: Array<String>) {
        context?.let {
            AlertDialog.Builder(it).setTitle(getString(R.string.link_to_open_title))
                .setItems(linksList) { dialog, which ->
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(linksList[which]))
                    startActivity(browserIntent)
                    dialog.dismiss()
                }.setCancelable(true)
                .show()
        }
    }

}