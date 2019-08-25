package com.example.androidtest.di

import com.example.androidtest.ui.LocationActivity
import com.example.androidtest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity


    @ContributesAndroidInjector
    abstract fun contributeLocationActivity(): LocationActivity
}