package com.example.suki.common.util.networkstatus

import kotlinx.coroutines.flow.Flow

/**
 * Interface for the network status provider.
 */
interface NetworkStatusProvider {

    /**
     * Returns the network status.
     *
     * @return The network status as a [Flow] of [NetworkStatus]
     */
    fun getNetworkStatus(): Flow<NetworkStatus>
}