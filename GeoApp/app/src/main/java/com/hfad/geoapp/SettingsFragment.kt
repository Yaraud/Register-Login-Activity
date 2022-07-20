package com.hfad.geoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.hfad.geoapp.viewmodels.PointViewModel
import com.hfad.geoapp.viewmodels.PointViewModelFactory


class SettingsFragment : PreferenceFragmentCompat() {


    private var distance: Int = 100
    private var frequency: Int = 1
    private val viewModel: PointViewModel by activityViewModels {
        PointViewModelFactory(
            (activity?.application as GeoApplication).database.pointDao()
             , (activity?.application as GeoApplication).database.settingsDao()
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.layout_settings, rootKey)
        (activity as AppCompatActivity).supportActionBar?.title = "Settings"
        val defValue1 = 100
        val defValue2 = 1
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        distance = sharedPreferences.getInt("distance",defValue1)
        frequency = sharedPreferences.getInt("frequency",defValue2)
        sharedPreferences.registerOnSharedPreferenceChangeListener { preferences, key ->
            when(key){
                "distance" -> distance = preferences.getInt(key,defValue1)
                "frequency" -> frequency = preferences.getInt(key,defValue2)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateSettings(distance,frequency)
    }
}