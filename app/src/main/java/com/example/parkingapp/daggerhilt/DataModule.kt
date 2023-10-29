package com.example.parkingapp.daggerhilt

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.parkingapp.repository.ParkingRealisation
import com.example.parkingapp.repository.ParkingRepository
import com.example.parkingapp.room.DataBase
import com.example.parkingapp.viewmodel.ParkingViewModel
import com.example.parkingapp.viewmodel.ParkingViewModelFactory
import com.example.parkingapp.viewmodel.RegisterViewModel
import com.example.parkingapp.viewmodel.RegisterViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideParkingRepository(db: DataBase): ParkingRepository{
       return ParkingRealisation(db)
    }

    @Provides
    @Singleton
    fun provideDataBase(app: Application) = Room.databaseBuilder(app,
        DataBase::class.java,
        "parking.db"
    ).fallbackToDestructiveMigration()
     .build()

    @Provides
    @Singleton
    fun provideParkingViewModelFactory(repository: ParkingRepository): ViewModelProvider.Factory {
        return ParkingViewModelFactory(repository)
    }

    @Provides
    @Singleton
    fun provideParkingViewModel(repository: ParkingRepository): ParkingViewModel {
        return ParkingViewModel(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterViewModelFactory(parkingRepository: ParkingRepository): ViewModelProvider.Factory {
        return RegisterViewModelFactory(parkingRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterViewModel(parkingRepository: ParkingRepository): RegisterViewModel {
        return RegisterViewModel((parkingRepository))
    }

}