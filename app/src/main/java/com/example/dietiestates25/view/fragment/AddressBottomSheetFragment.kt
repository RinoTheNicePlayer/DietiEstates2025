package com.example.dietiestates25.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dietiestates25.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddressBottomSheetFragment(private val onAddressEntered: (String, (Boolean) -> Unit) -> Unit) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_address_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addressEditText = view.findViewById<EditText>(R.id.address_edit_text)
        val erroreLabel = view.findViewById<TextView>(R.id.error_label_address)
        val confirmButton = view.findViewById<LinearLayout>(R.id.send_address_button)

        confirmButton.setOnClickListener {
            val address = addressEditText.text.toString()
            sendAddress(address, erroreLabel)
        }
    }

    private fun sendAddress(address: String, erroreLabel: TextView) {
        if (address.isNotEmpty()) {
            erroreLabel.visibility = View.INVISIBLE

            // Invia l'indirizzo e ricevi il risultato della validazione
            onAddressEntered(address) { isValid ->
                if (isValid) {
                    dismiss()  // Chiudi la bottom sheet se l'indirizzo è valido
                } else {
                    erroreLabel.visibility = View.VISIBLE  // Mostra l'errore
                }
            }
        } else {
            erroreLabel.visibility = View.VISIBLE
        }
    }

}