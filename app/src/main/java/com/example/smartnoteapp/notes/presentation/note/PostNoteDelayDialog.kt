package com.example.smartnoteapp.notes.presentation.note

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.smartnoteapp.R

class PostNoteDelayDialog: DialogFragment() {

    var onDelaySelected: ((Int) -> Unit)? = null
    private lateinit var dialog: Dialog
    private lateinit var radioGroup: RadioGroup
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_post_note_delay_layout)

        radioGroup = dialog.findViewById(R.id.radioGroupDelay)
        okButton = dialog.findViewById(R.id.okBtn)
        cancelButton = dialog.findViewById(R.id.cancelBtn)

        setButtonClickListeners()

        return dialog
    }

    private fun setButtonClickListeners() {
        okButton.setOnClickListener { group ->
            val selectedDelay = getSelectedDelay(radioGroup)
            onDelaySelected?.invoke(selectedDelay)
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun getSelectedDelay(radioGroup: RadioGroup): Int {
        val selectedDelay =
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio1Min -> 1
                R.id.radio5Min -> 5
                R.id.radio10Min -> 10
                else -> 1
            }
        return selectedDelay
    }
}