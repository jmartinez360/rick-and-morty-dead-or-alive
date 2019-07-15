package com.dev.rickandmortydeadoralive.ui.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.dev.rickandmortydeadoralive.R
import com.dev.rickandmortydeadoralive.utils.filterTypes.FilterEnum

class FilterActivity : AppCompatActivity() {

    private val currentStatus: String? = null
    private val currentGender: String? = null
    private val currentName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
    }

    private fun showStatusFilter() {
        val builder = AlertDialog.Builder(this)
        val filters = FilterEnum.STATUS_FILTER.filterType.filterOptions
        builder.setTitle("Options")
            .setItems(filters, DialogInterface.OnClickListener { dialog, which ->
                //todo guardar en preferencias
            })
        builder.create().show()
    }

    private fun showGenderFilter() {
        val builder = AlertDialog.Builder(this)
        val filters = FilterEnum.STATUS_FILTER.filterType.filterOptions
        builder.setTitle("Options")
            .setItems(filters, DialogInterface.OnClickListener { dialog, which ->
                //todo guardar en preferencias
            })
        builder.create().show()
    }
}
