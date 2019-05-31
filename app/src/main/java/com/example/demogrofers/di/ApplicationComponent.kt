package com.example.demogrofers.di

import android.app.Application
import com.example.demogrofers.ScrumBoardApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    ViewModule::class,
    ViewModelFactoryModule::class
])
@Singleton
interface ApplicationComponent : AndroidInjector<ScrumBoardApplication>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
