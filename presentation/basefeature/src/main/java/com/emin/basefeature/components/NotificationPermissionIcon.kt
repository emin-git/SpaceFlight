package com.emin.basefeature.components

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                Toast.makeText(context, "İzin verildi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "İzin reddedildi", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Icon(
        imageVector = Icons.Default.Notifications,
        contentDescription = "Bildirim İzni",
        tint = Color.Black,
        modifier = Modifier
            .clickable {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // Android 13 ve üzeri için izin iste
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    // Android 12 ve altı için: kendi dialog'unu göster
                    showCustomPermissionDialog(context)
                }
            }
    )
}

fun showCustomPermissionDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Bildirim İzni")
        .setMessage("Uygulama sana bildirim gönderebilmek için iznine ihtiyaç duyuyor.")
        .setPositiveButton("Tamam") { dialog, _ ->
            // Burada gerçek bir sistem izni yok, sadece kullanıcıya bilgilendirme yapıyoruz
            Toast.makeText(context, "İzin varsayılıyor (Android 12 ve altı)", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        .setNegativeButton("İptal") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}


