package com.example.demogrofers.di

import android.arch.lifecycle.ViewModel
import com.example.demogrofers.viewmodel.CreateNewTaskViewModel
import com.example.demogrofers.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreateNewTaskViewModelsModule {

    /*
     * This method basically says
     * inject this object into a Map using the @IntoMap annotation,
     * with the  MovieListViewModel.class as key,
     * and a Provider that will build a MovieListViewModel
     * object.
     *
     * */

    @Binds
    @IntoMap
    @ViewModelKey(CreateNewTaskViewModel::class)
    abstract fun createNewTaskViewModel(createNewTaskViewModel: CreateNewTaskViewModel): ViewModel
}