package com.tprobius.hotelbookingapp.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.tprobius.hotelbookingapp.data.repository.HotelBookingAppApiRepositoryImpl
import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi.Companion.BASE_URL
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netModule = module {
    single { provideApiRetrofit() }
    single { provideHotelBookingAppApi(get()) }
    single { HotelBookingAppApiRepositoryImpl(get(), Dispatchers.IO) }
}

val viewModelModule = module { }

val navigationModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().getNavigatorHolder() }
}

fun provideApiRetrofit(): Retrofit.Builder {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
    client.addInterceptor(logging)

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
}

fun provideHotelBookingAppApi(builder: Retrofit.Builder): HotelBookingAppApi {
    return builder
        .build()
        .create(HotelBookingAppApi::class.java)
}