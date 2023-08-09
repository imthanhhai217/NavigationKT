package com.jaroidx.navigation.utils

import android.app.Activity
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.jaroidx.navigation.R

class PermissionHelper {

    companion object {
        @JvmStatic
        fun requestPermission(
            activity: Activity?,
            activityResultLauncher: ActivityResultLauncher<String>,
            permission: String
        ) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (activity == null) {
                return
            }
            if (ContextCompat.checkSelfPermission(
                    activity, permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //Permission granted
                Log.d("TAG", "Permission granted")
            } else if (activity.shouldShowRequestPermissionRationale(permission)) {
                Log.d("TAG", "shouldShowRequestPermissionRationale: ")
                showDialogRetry(
                    activity,
                    activityResultLauncher,
                    permission,
                    "Please access $permission to enable this feature"
                )
            } else {
                activityResultLauncher.launch(permission)
            }
//            }
        }

        private fun showDialogRetry(
            activity: Activity?,
            activityResultLauncher: ActivityResultLauncher<String>,
            permission: String,
            message: String
        ) {
            if (activity == null) return
            val dialog = Dialog(activity, R.style.My_Theme_Dialog)
            dialog.window?.apply {
                requestFeature(Window.FEATURE_NO_TITLE)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_retry_request_permission)
            val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
            val btnOk = dialog.findViewById(R.id.btnOK) as TextView
            val btnCancel = dialog.findViewById(R.id.btnCancel) as TextView
            tvTitle.text = message
            btnOk.setOnClickListener {
                activityResultLauncher.launch(permission)
                dialog.dismiss()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}