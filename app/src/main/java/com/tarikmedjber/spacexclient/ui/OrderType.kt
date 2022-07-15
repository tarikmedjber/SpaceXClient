package com.tarikmedjber.spacexclient.ui

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
