package com.tprobius.hotelbookingapp

import android.app.Application
import com.tprobius.hotelbookingapp.di.navigationModule
import com.tprobius.hotelbookingapp.di.netModule
import com.tprobius.hotelbookingapp.di.useCasesModule
import com.tprobius.hotelbookingapp.di.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HotelBookingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            modules(netModule, viewModelModule, useCasesModule, navigationModule)
        }
    }
}