package com.neillon.events.di

import androidx.room.Room
import com.neillon.events.data.datasources.remote.EventsService
import com.neillon.events.data.repository.EventsRepositoryImpl
import com.neillon.events.domain.repository.EventsRepository
import com.neillon.events.BuildConfig
import com.neillon.events.data.datasources.local.EventsDatabase
import com.neillon.events.data.datasources.local.LocalDateTimeConverter
import com.neillon.events.domain.usecase.GetAllEventsUseCase
import com.neillon.events.domain.usecase.SearchEventsUseCase
import com.neillon.events.ui.viewmodel.EventsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val eventsModule = module {
    // Data
    single<Retrofit> { provideRetrofit() }
    single<EventsDatabase> {
        Room.databaseBuilder(get(), EventsDatabase::class.java, "database-events")
            .addTypeConverter(LocalDateTimeConverter())
            .build()
    }
    factory<EventsService> { get<Retrofit>().create(EventsService::class.java) }
    factory<EventsRepository> { EventsRepositoryImpl(get(), get(), get()) }

    // Domain
    single { GetAllEventsUseCase(get()) }
    single { SearchEventsUseCase(get()) }

    // Presentation
    viewModel<EventsViewModel> { EventsViewModel(get(), get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(OkHttpClient().newBuilder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}