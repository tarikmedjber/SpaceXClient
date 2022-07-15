package com.tarikmedjber.spacexclient.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tarikmedjber.spacexclient.R
import com.tarikmedjber.spacexclient.databinding.LaunchItemBinding
import com.tarikmedjber.spacexclient.engine.models.Launches
import com.tarikmedjber.spacexclient.utils.DateTimeFormatter.getDate
import com.tarikmedjber.spacexclient.utils.DateTimeFormatter.getDaysSince
import com.tarikmedjber.spacexclient.utils.DateTimeFormatter.getTime
import java.time.LocalDate
import java.time.LocalDateTime

class HomeAdapter(
    private val listener: LaunchesViewHolder.OnLaunchListener,
    private val context: Context
) :
    RecyclerView.Adapter<LaunchesViewHolder>() {

    private var launchesList = emptyList<Launches>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LaunchesViewHolder.from(parent, listener, context)

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val launch = launchesList[position]
        holder.bind(launch, position)
    }

    override fun getItemCount(): Int {
        return launchesList.size
    }

    fun setItems(launches: List<Launches>) {
        launchesList = launches
        notifyDataSetChanged()
    }
}


class LaunchesViewHolder(
    private val binding: LaunchItemBinding,
    private val launchListener: OnLaunchListener,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    interface OnLaunchListener {
        fun onLaunchClicked(position: Int)
    }

    fun bind(launch: Launches, position: Int) {

        val date = getDate(launch.launchDate)
        val time = getTime(launch.launchDate)
        val daysSinceLastLaunch = getDaysSince(launch.launchDate)

        binding.missionText.text = context.getString(R.string.mission_info, launch.missionName)
        binding.dateTimeText.text =
            context.getString(R.string.launch_date_time_info, date, time)
        binding.rocketText.text = context.getString(
            R.string.launch_rocket_info,
            launch.rocket.rocketName,
            launch.rocket.rocketType
        )

        val daysSinceLaunchString: String = if (daysSinceLastLaunch > 0) {
            context.getString(R.string.since, context.getString(R.string.plus))
        } else {
            context.getString(R.string.from_now, context.getString(R.string.dash))
        }

        binding.daysText.text = context.getString(
            R.string.launch_days_from_since,
            daysSinceLaunchString,
            daysSinceLastLaunch.toString(),
        )

        Picasso
            .get()
            .load(launch.links.missionImage)
            .into(binding.launchImage)

        if (launch.launchSuccess) {
            binding.successIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_check
                )
            )
        } else {
            binding.successIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_close
                )
            )
        }

        binding.launchItem.setOnClickListener {
            launchListener.onLaunchClicked(position)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            listener: OnLaunchListener,
            context: Context
        ): LaunchesViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LaunchItemBinding.inflate(layoutInflater, parent, false)
            return LaunchesViewHolder(
                binding, listener, context
            )
        }
    }
}