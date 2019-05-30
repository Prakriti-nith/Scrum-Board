package com.example.demogrofers.modules

import com.example.demogrofers.views.ScrumBoardMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun contributeScrumBoardMainActivity(): ScrumBoardMainActivity

//    @ContributesAndroidInjector()
//    abstract fun contributeCreateNewTaskActivity(): CreateNewTaskActivity
//
//    @ContributesAndroidInjector()
//    abstract fun contributeFilterStatesActivity(): FilterStatesActivity

}
