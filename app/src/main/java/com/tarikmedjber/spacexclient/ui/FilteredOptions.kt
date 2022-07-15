package com.tarikmedjber.spacexclient.ui

sealed class FilteredOptions(val orderType: OrderType) {
    class LaunchSuccess(orderType: OrderType) : FilteredOptions(orderType)
    class Date(orderType: OrderType) : FilteredOptions(orderType)
}
