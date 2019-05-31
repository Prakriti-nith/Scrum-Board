package com.example.demogrofers.di

import com.example.demogrofers.views.ScrumBoardMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ScrumBoardViewModelsModule::class])
abstract class ViewModule {

    // TODO: FIX BUG: removing includes throws error which it should not throw as required module is included in below line
    @ContributesAndroidInjector(modules = [ScrumBoardViewModelsModule::class])
    abstract fun contributeScrumBoardMainActivity(): ScrumBoardMainActivity

//    @ContributesAndroidInjector()
//    abstract fun contributeCreateNewTaskActivity(): CreateNewTaskActivity
//
//    @ContributesAndroidInjector()
//    abstract fun contributeFilterStatesActivity(): FilterStatesActivity

}