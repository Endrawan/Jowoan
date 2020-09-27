package com.example.jowoan.views.main.fragmentToko

import android.app.Dialog
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.jowoan.R
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.Avatar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dialog.*


class TokoDialogFragment : DialogFragment() {

    private lateinit var avatar: Avatar
    private var status = 0

    companion object {
        fun newInstance(avatar: Avatar, status: Int): TokoDialogFragment {
            val args = Bundle()
            val gson = Gson()
            args.putString("avatar", gson.toJson(avatar))
            args.putInt("status", status)

            val fragment = TokoDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()
        avatar = gson.fromJson(requireArguments().getString("avatar"), Avatar::class.java)
        status = requireArguments().getInt("status")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlideApp.with(view.context).load("http://${avatar.URL}")
            .placeholder(ImageConfig.defaultAvatar).centerCrop()
            .into(imageView_image)

        if (!avatar.name.isNullOrEmpty()) {
            textView_name.text = avatar.name
        } else {
            textView_name.text = "Tidak bernama"
        }

        textView_category.text = "Avatar"
        textView_price.text = "${avatar.price} Poin"

        button_buy.setOnClickListener {
            Toast.makeText(requireContext(), "Telah dibeli!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onResume() {
        val window: Window? = dialog!!.window
        val size = Point()
        val display: Display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.75).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }
}