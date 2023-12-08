package com.lucas.petros.spotfylab.features.playlists.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.lucas.petros.spotfylab.R
import com.lucas.petros.spotfylab.databinding.FragmentCreatePlaylistDialogBinding

class CreatePlaylistDialogFragment : DialogFragment() {

    interface DialogListener {
        fun onCreateButtonClick(userInput: String)
    }

    private var dialogListener: DialogListener? = null
    private var editText: EditText? = null
    lateinit var binding: FragmentCreatePlaylistDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePlaylistDialogBinding.inflate(inflater, container, false)

        editText = binding.etNamePlaylist

        val positiveButton = binding.vButtonCreate
        val negativeButton = binding.ibBack

        positiveButton.setOnClickListener {
            dialogListener?.let {
                val userInput = editText?.text.toString()
                if (userInput.isBlank()){
                    editText?.error = "Preencha o campo para continuar"
                }else{
                    it.onCreateButtonClick(userInput)
                    dismissNow()
                }


            }
        }

        negativeButton.setOnClickListener {
            dialogListener?.let {
                dismissNow()
            }
        }

        return binding.root
    }

    fun setDialogListener(listener: DialogListener) {
        dialogListener = listener
    }
}