package com.example.suki.common.util.networkstatus

/**
 * The networks status model. It can be available or unavailable.
 */
sealed class NetworkStatus {
    /**
     * The available network status.
     */
    object Available : NetworkStatus()

    /**
     * The unavailable network status.
     */
    object Unavailable : NetworkStatus()

}