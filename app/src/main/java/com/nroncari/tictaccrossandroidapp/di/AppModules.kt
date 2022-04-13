package com.nroncari.tictaccrossandroidapp.di

import com.nroncari.tictaccrossandroidapp.data.datasource.GamePlayRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.datasource.GamePlayRemoteDataSourceImpl
import com.nroncari.tictaccrossandroidapp.data.datasource.GameRemoteDataSource
import com.nroncari.tictaccrossandroidapp.data.datasource.GameRemoteDataSourceImpl
import com.nroncari.tictaccrossandroidapp.data.repository.GamePlayRepositoryImpl
import com.nroncari.tictaccrossandroidapp.data.repository.GameRepositoryImpl
import com.nroncari.tictaccrossandroidapp.data.retrofit.HttpClient
import com.nroncari.tictaccrossandroidapp.data.retrofit.RetrofitClient
import com.nroncari.tictaccrossandroidapp.data.service.GamePlayService
import com.nroncari.tictaccrossandroidapp.data.service.GameService
import com.nroncari.tictaccrossandroidapp.domain.repository.GamePlayRepository
import com.nroncari.tictaccrossandroidapp.domain.repository.GameRepository
import com.nroncari.tictaccrossandroidapp.domain.usecase.ClearGameBoardUseCase
import com.nroncari.tictaccrossandroidapp.domain.usecase.ConnectGameUseCase
import com.nroncari.tictaccrossandroidapp.domain.usecase.CreateGameUseCase
import com.nroncari.tictaccrossandroidapp.domain.usecase.GamePlayUseCase
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.GamePlayViewModel
import com.nroncari.tictaccrossandroidapp.presentation.viewmodel.SessionGameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val domainModules = module {
    factory { CreateGameUseCase(repository = get()) }
    factory { ConnectGameUseCase(repository = get()) }
    factory { GamePlayUseCase(repository = get()) }
    factory { ClearGameBoardUseCase(repository = get()) }
}

val presentationModules = module {
    viewModel { SessionGameViewModel(createGameUseCase = get(), connectGameUseCase = get()) }
    viewModel { GamePlayViewModel(gamePlayUseCase = get(), clearBoardUseCase = get()) }
}

val dataModules = module {
    factory<GameRemoteDataSource> { GameRemoteDataSourceImpl(service = get()) }
    factory<GamePlayRemoteDataSource> { GamePlayRemoteDataSourceImpl(service = get()) }
    factory<GameRepository> { GameRepositoryImpl(remoteDataSource = get()) }
    factory<GamePlayRepository> { GamePlayRepositoryImpl(remoteDataSource = get()) }
}

val networkModules = module {
    single { RetrofitClient(application = androidContext()).newInstance() }
    single { HttpClient(get()) }
    factory { get<HttpClient>().create(GameService::class.java) }
    factory { get<HttpClient>().create(GamePlayService::class.java) }
}