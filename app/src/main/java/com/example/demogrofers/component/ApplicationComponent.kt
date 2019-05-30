package com.example.demogrofers.component

import android.app.Application
import com.example.demogrofers.ScrumBoardApplication
import com.example.demogrofers.modules.NetworkModule
import com.example.demogrofers.modules.ViewModelModule
import com.example.demogrofers.modules.ViewModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    ViewModule::class,
    ViewModelModule::class
])
@Singleton
public interface ApplicationComponent : AndroidInjector<ScrumBoardApplication>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
