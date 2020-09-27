package com.example.jowoan.views.main.fragmentToko

import android.app.Dialog
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.jowoan.R
import com.example.jowoan.config.AvatarConfig
import com.example.jowoan.config.ImageConfig
import com.example.jowoan.custom.AppCompatActivity
import com.example.jowoan.custom.GlideApp
import com.example.jowoan.models.Avatar
import com.example.jowoan.models.User
import com.example.jowoan.network.APICallback
import com.example.jowoan.views.auth.LoginActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dialog.*


class TokoDialogFragment : DialogFragment() {

    private lateinit var avatar: Avatar
    private var status = AvatarConfig.NOT_OWNED
    private lateinit var act: AppCompatActivity
    private lateinit var user: User

    companion object {
        fun newInstance(avatar: Avatar): TokoDialogFragment {
            val args = Bundle()
            val gson = Gson()
            args.putString("avatar", gson.toJson(avatar))

            val fragment = TokoDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gson = Gson()
        avatar = gson.fromJson(requireArguments().getString("avatar"), Avatar::class.java)
        act = activity as AppCompatActivity
        user = act.user
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

        setStatus()
        initButton()
        button_buy.setOnClickListener {
            handleButtonClick()
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

    private fun buyAndEquip() {
        user.points -= avatar.price
        user.ownedAvatars.add(avatar)
        showProgress()
        act.jowoanService.buyAvatar(user.token, user.ID, avatar.ID)
            .enqueue(APICallback(object : APICallback.Action<String> {
                override fun responseSuccess(data: String) {
                    changeAvatarRequest()
                }

                override fun dataNotFound(message: String) {
                    hideProgress()
                    act.toast("Data not found! $message")
                }

                override fun responseFailed(status: String, message: String) {
                    hideProgress()
                    act.toast("Beli Avatar Gagal! Status: $status Message: $message")
                }

                override fun networkFailed(t: Throwable) {
                    hideProgress()
                    act.toast("Beli Avatar Gagal! ${t.message}")
                }

                override fun tokenExpired() {
                    hideProgress()
                    act.toast("Token telah expired, silahkan login ulang")
                    act.logout()
                    Intent(act, LoginActivity::class.java).also {
                        startActivity(it)
                        act.finishAffinity()
                    }
                }
            }))
    }

    private fun changeAvatarRequest() {
        showProgress()
        user.avatarID = avatar.ID
        Log.d("changeAvatarBody", act.gson.toJson(user))
        act.jowoanService.userUpdate(user.token, user.ID, user)
            .enqueue(APICallback(object : APICallback.Action<User> {
                override fun responseSuccess(data: User) {
                    hideProgress()
                    act.toast("Berhasil mengganti avatar!")
                    act.saveUser(data)
                }

                override fun dataNotFound(message: String) {
                    hideProgress()
                    act.toast("Data not found! $message")
                }

                override fun responseFailed(status: String, message: String) {
                    hideProgress()
                    act.toast("Set Avatar Gagal! Status: $status Message: $message")
                }

                override fun networkFailed(t: Throwable) {
                    hideProgress()
                    act.toast("Set Avatar Gagal! ${t.message}")
                }

                override fun tokenExpired() {
                    hideProgress()
                    act.toast("Token telah expired, silahkan login ulang")
                    act.logout()
                    Intent(act, LoginActivity::class.java).also {
                        startActivity(it)
                        act.finishAffinity()
                    }
                }

            }))
    }

    private fun initButton() {
        when (status) {
            AvatarConfig.OWNED -> {
                button_buy.text = "Gunakan"
            }
            AvatarConfig.EQUIPPED -> {
                button_buy.isEnabled = false
                button_buy.text = "Digunakan"
            }
            else -> { // Not owned
                button_buy.text = "Beli"
            }
        }
    }

    private fun setStatus() {
        if (user.avatarID == avatar.ID) status = AvatarConfig.EQUIPPED
        else {
            for (a in user.ownedAvatars) {
                if (a.ID == avatar.ID) {
                    status = AvatarConfig.OWNED
                    break
                }
            }
        }
    }

    private fun handleButtonClick() {
        when (status) {
            AvatarConfig.OWNED -> {
                changeAvatarRequest()
                status = AvatarConfig.EQUIPPED
            }
            AvatarConfig.EQUIPPED -> {
                act.toast("Avatar telah digunakan!")
            }
            else -> { // Not owned
                if (user.points < avatar.price) {
                    act.toast("Maaf pointmu belum cukup!")
                    return
                }
                buyAndEquip()
                status = AvatarConfig.EQUIPPED
            }
        }
        initButton()
    }

    private fun showProgress() {
        button_buy.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
        button_buy.visibility = View.VISIBLE
    }
}