package com.example.demogrofers.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.demogrofers.viewmodel.ScrumBoardViewModel
import com.example.demogrofers.viewmodel.ViewModelFactory
import com.example.demogrofers.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


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
    @ViewModelKey(ScrumBoardViewModel::class)
    protected abstract fun scrumBoardViewModel(scrumBoardViewModel: ScrumBoardViewModel): ViewModel
}
