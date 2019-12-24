package com.maq.propertyapp.properties

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maq.propertyapp.getOrAwaitValue
import com.maq.propertyapp.network.PropertiesApi
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PropertyViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Other code…


    @Test
    fun getPropertiesTest(){

        val propertyViewModel = PropertyViewModel(PropertiesRepository(PropertiesApi.invoke()))
        propertyViewModel.getProperties()

        val value = propertyViewModel.properties.getOrAwaitValue()

        assertThat(value.data,not(nullValue()))

    }
}