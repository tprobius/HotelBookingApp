package com.tprobius.hotelbookingapp.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.tprobius.hotelbookingapp.data.repository.BookingInfoApiRepositoryImpl
import com.tprobius.hotelbookingapp.data.repository.HotelInfoApiRepositoryImpl
import com.tprobius.hotelbookingapp.data.repository.RoomListApiRepositoryImpl
import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi
import com.tprobius.hotelbookingapp.data.source.HotelBookingAppApi.Companion.BASE_URL
import com.tprobius.hotelbookingapp.features.booking.domain.repository.BookingInfoApiRepository
import com.tprobius.hotelbookingapp.features.booking.domain.usecases.GetBookingInfoUseCase
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoRouter
import com.tprobius.hotelbookingapp.features.booking.presentation.bookinginfofragment.BookingInfoViewModel
import com.tprobius.hotelbookingapp.features.hotel.domain.repository.HotelInfoApiRepository
import com.tprobius.hotelbookingapp.features.hotel.domain.usecases.GetHotelInfoUseCase
import com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment.HotelInfoRouter
import com.tprobius.hotelbookingapp.features.hotel.presentation.hotelinfofragment.HotelInfoViewModel
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoRouter
import com.tprobius.hotelbookingapp.features.payment.paymentinfofragment.PaymentInfoViewModel
import com.tprobius.hotelbookingapp.features.room.domain.repository.RoomListApiRepository
import com.tprobius.hotelbookingapp.features.room.domain.usecases.GetRoomListUseCase
import com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment.RoomListRouter
import com.tprobius.hotelbookingapp.features.room.presentation.roomlistfragment.RoomListViewModel
import com.tprobius.hotelbookingapp.navigation.BookingInfoRouterImpl
import com.tprobius.hotelbookingapp.navigation.HotelInfoRouterImpl
import com.tprobius.hotelbookingapp.navigation.PaymentInfoRouterImpl
import com.tprobius.hotelbookingapp.navigation.RoomListRouterImpl
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netModule = module {
    single { provideApiRetrofit() }
    single { provideHotelBookingAppApi(get()) }

    single<HotelInfoApiRepository> { HotelInfoApiRepositoryImpl(get(), Dispatchers.IO) }
    single<RoomListApiRepository> { RoomListApiRepositoryImpl(get(), Dispatchers.IO) }
    single<BookingInfoApiRepository> { BookingInfoApiRepositoryImpl(get(), Dispatchers.IO) }
}

val viewModelModule = module {
    viewModel { HotelInfoViewModel(get(), get()) }
    viewModel { RoomListViewModel(get(), get(), get()) }
    viewModel { BookingInfoViewModel(get(), get()) }
    viewModel { PaymentInfoViewModel(get()) }
}

val useCasesModule = module {
    single { GetHotelInfoUseCase(get()) }
    single { GetRoomListUseCase(get()) }
    single { GetBookingInfoUseCase(get()) }
}

val navigationModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().getNavigatorHolder() }

    factory<HotelInfoRouter> { HotelInfoRouterImpl(get()) }
    factory<RoomListRouter> { RoomListRouterImpl(get()) }
    factory<BookingInfoRouter> { BookingInfoRouterImpl(get()) }
    factory<PaymentInfoRouter> { PaymentInfoRouterImpl(get()) }
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