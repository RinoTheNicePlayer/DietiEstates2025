package com.example.dietiestates25

import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.dietiestates25.controller.PropertyViewModel
import com.example.dietiestates25.model.PropertyResponse
import com.example.dietiestates25.view.activity.MainActivity
import com.example.dietiestates25.view.fragment.PropertyDetailsFragment
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class PropertyDetailsFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Mock
    private lateinit var propertyViewModel: PropertyViewModel

    private lateinit var fragment: PropertyDetailsFragment

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        fragment = PropertyDetailsFragment()
        fragment.propertyViewModel = propertyViewModel
    }

    @Test
    fun testGetInterestingPoints() {
        val property = PropertyResponse(
            // Initialize necessary fields for the test
        )
        val interestingPoint = TextView(ApplicationProvider.getApplicationContext())

        // Simulate ViewModel behavior
        val liveData = MutableLiveData<PropertyResponse>()
        liveData.value = property
        Mockito.`when`(propertyViewModel.selectedProperty).thenReturn(liveData)

        // Call the method to test
        fragment.getInterestingPoints(property, interestingPoint)

        // Verify that the TextView text was updated correctly
        assertEquals("Expected text", interestingPoint.text.toString())
    }
}