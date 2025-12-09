package com.shas.meditationapp.di


import com.shas.meditationapp.core.data.HttpClientFactory
import com.shas.meditationapp.explore.presentation.ExploreViewModel
import com.shas.meditationapp.home.data.network.KtorRemoteHomeSource
import com.shas.meditationapp.home.data.network.RemoteHomeSource
import com.shas.meditationapp.home.data.repository.HomeRepositoryImpl
import com.shas.meditationapp.home.domain.repository.HomeRepository
import com.shas.meditationapp.home.presentation.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteHomeSource).bind<RemoteHomeSource>()
    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()

    viewModelOf(::HomeViewModel)
    viewModelOf(::ExploreViewModel)
}