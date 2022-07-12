package com.pipe.codebox.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.databinding.DialogFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class SaveDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogFragmentBinding

    lateinit var onSave: (HealthData) -> Unit
    lateinit var onCancel: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnCancel.setOnClickListener { onCancel() }
            btnYes.setOnClickListener {
                onSave(
                    HealthData(
                        id = Calendar.getInstance().timeInMillis,
                        date = getCurrentDate(),
                        time = getCurrentTime(),
                        upperBloodPressure = getText(upperBloodPressureEditText),
                        lowerBloodPressure = getText(lowerBloodPressureEditText),
                        pulse = getText(pulseEditText)
                    )
                )
            }
        }
    }

    private fun getText(editText: EditText): String {
        var textName: String = editText.editableText.toString()
        if (textName.isEmpty()) textName = ""
        return textName
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd MMMM",Locale("ru"))
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(date)
    }
}