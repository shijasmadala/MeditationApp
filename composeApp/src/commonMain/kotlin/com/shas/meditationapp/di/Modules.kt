package com.shas.meditationapp.di


import SessionManager
import com.shas.meditationapp.auth.presentation.AuthViewModel
import com.shas.meditationapp.core.data.HttpClientFactory
import com.shas.meditationapp.explore.data.repository.ExploreRepositoryImpl
import com.shas.meditationapp.explore.domain.repository.ExploreRepository
import com.shas.meditationapp.explore.presentation.ExploreViewModel
import com.shas.meditationapp.explore.presentation.search.SearchScreenViewModel
import com.shas.meditationapp.home.data.network.KtorRemoteHomeSource
import com.shas.meditationapp.home.data.network.RemoteHomeSource
import com.shas.meditationapp.home.data.repository.HomeRepositoryImpl
import com.shas.meditationapp.home.domain.repository.HomeRepository
import com.shas.meditationapp.home.presentation.HomeViewModel
import com.shas.meditationapp.song_details.data.repository.SongDetailsRepositoryImpl
import com.shas.meditationapp.song_details.domain.repository.SongDetailsRepository
import com.shas.meditationapp.song_details.presentation.SongDetailViewModel
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
    singleOf(::ExploreRepositoryImpl).bind<ExploreRepository>()
    singleOf(::SongDetailsRepositoryImpl).bind<SongDetailsRepository>()
    single {
        SessionManager(get())
    }

    viewModelOf(::HomeViewModel)
    viewModelOf(::ExploreViewModel)
    viewModelOf(::SongDetailViewModel)
    viewModelOf(::SearchScreenViewModel)
    viewModelOf(::AuthViewModel)
}