package com.dev.rickandmortydeadoralive.ui.activities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.utils.Constants
import com.dev.rickandmortydeadoralive.utils.filterTypes.FilterEnum
import com.dev.rickandmortydeadoralive.utils.filterTypes.FilterType
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {


    private val sharedPreferences by lazy {
        getSharedPreferences(Constants.FILTER_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        currentFilteredGenderText.text =
            sharedPreferences.getString(Constants.GENDER_FILTER, FilterType.WITHOUT_FILTERING)
        currentFilteredStatusText.text =
            sharedPreferences.getString(Constants.STATUS_FILTER, FilterType.WITHOUT_FILTERING)
        filteredNameEdit.setText(sharedPreferences.getString(Constants.NAME_FILTER, ""))

        acceptButton.setOnClickListener {
            saveFilterPreference(Constants.NAME_FILTER, filteredNameEdit.text.toString())
            setResult(MainActivity.FILTER_DONE_RESULT_CODE)
            finish()
        }
        currentFilteredStatusText.setOnClickListener{ showStatusFilter() }
        currentFilteredGenderText.setOnClickListener{ showGenderFilter() }

    }

    private fun showStatusFilter() {
        val builder = AlertDialog.Builder(this)
        val filters = FilterEnum.STATUS_FILTER.filterType.filterOptions
        builder.setTitle("Options")
            .setItems(filters) { _, which ->
                val choosenOption = filters[which]
                saveFilterPreference(Constants.STATUS_FILTER, choosenOption)
                currentFilteredStatusText.text = choosenOption
            }
        builder.create().show()
    }

    private fun showGenderFilter() {
        val builder = AlertDialog.Builder(this)
        val filters = FilterEnum.GENDER_FILTER.filterType.filterOptions
        builder.setTitle("Options")
            .setItems(filters) { _, which ->
                val choosenOption = filters[which]
                saveFilterPreference(Constants.GENDER_FILTER, choosenOption)
                currentFilteredGenderText.text = choosenOption
            }
        builder.create().show()
    }

    private fun saveFilterPreference(preferenceType: String, preferenceValue: String) =
        sharedPreferences.edit().putString(preferenceType, preferenceValue).apply()

}
