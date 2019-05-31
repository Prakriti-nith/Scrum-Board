package com.example.demogrofers.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.demogrofers.viewmodel.ScrumBoardViewModel
import com.example.demogrofers.viewmodel.ViewModelFactory
import com.example.demogrofers.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
