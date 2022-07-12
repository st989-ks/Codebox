package com.pipe.codebox.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.pipe.codebox.R
import com.pipe.codebox.databinding.DialogFragmentBinding
import com.pipe.codebox.domain.entity.Locations
import com.yandex.mapkit.geometry.Point
import kotlinx.android.synthetic.main.dialog_fragment.*

class SavePointDialog(context: Context, private val location: Locations) :
    Dialog(context) {

    private lateinit var binding: DialogFragmentBinding

    lateinit var onSave: (Locations) -> Unit
    lateinit var onDelete: (Int) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window? = window
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        when (location.markerId) {
            R.drawable.point_a -> binding.radioButtonA.isChecked = true
            R.drawable.point_b -> binding.radioButtonB.isChecked = true
            R.drawable.point_c -> binding.radioButtonC.isChecked = true
            R.drawable.point_d -> binding.radioButtonD.isChecked = true
            else -> binding.radioButtonA.isChecked = true
        }
        binding.editName.setText(location.name)

        if (location.keyPoint != null){
            binding.btnDelete.isVisible = true
        }

        binding.btnSave.setOnClickListener {
            onSave(
                Locations(
                    keyPoint= location.keyPoint,
                    markerId = setRadioButton(),
                    name = getText(),
                    point = Point(location.point.latitude, location.point.longitude)
                )
            )
        }

        binding.btnDelete.setOnClickListener {
            onDelete(
                location.keyPoint!!
            )
        }
    }

    private fun getText():String{
        var textName: String = edit_name.editableText.toString()
        if (textName.isEmpty()) textName = "Безымянная"
        return textName
    }

    private fun setRadioButton():Int {
        return if (binding.radioButtonA.isChecked){
            R.drawable.point_a
        } else if (binding.radioButtonB.isChecked){
            R.drawable.point_b
        }else if (binding.radioButtonC.isChecked){
            R.drawable.point_c
        }else {
            R.drawable.point_d
        }
    }
}