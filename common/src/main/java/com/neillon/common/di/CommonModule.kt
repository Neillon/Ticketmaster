package com.neillon.common.di

import com.neillon.common.network.ConnectivityChecker
import com.neillon.common.network.ConnectivityCheckerImpl
import org.koin.dsl.module

val commonModule = module {
    single<ConnectivityChecker> { ConnectivityCheckerImpl() }
}