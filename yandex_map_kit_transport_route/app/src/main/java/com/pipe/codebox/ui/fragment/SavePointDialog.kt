package com.pipe.codebox.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.pipe.codebox.databinding.DialogFragmentBinding

class SavePointDialog(context: Context, private val str: String) :
    Dialog(context) {

    private lateinit var binding: DialogFragmentBinding

    lateinit var onOk: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.text.text = str
        binding.btnOk.setOnClickListener { onOk()}
    }
}