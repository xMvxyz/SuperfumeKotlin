package com.SuperfumeKotlin.util

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utilidad para seleccionar imágenes de la galería o capturar con la cámara
 */
object ImagePickerHelper {
    
    /**
     * Crea un URI temporal para guardar una foto capturada por la cámara
     */
    fun createImageUri(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir("images")
        
        // Crear directorio si no existe
        if (storageDir != null && !storageDir.exists()) {
            storageDir.mkdirs()
        }
        
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }
    
    /**
     * Obtiene el launcher para seleccionar imagen de galería
     */
    @Composable
    fun rememberGalleryLauncher(
        onImageSelected: (Uri) -> Unit
    ): ManagedActivityResultLauncher<String, Uri?> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { onImageSelected(it) }
        }
    }
    
    /**
     * Obtiene el launcher para capturar foto con cámara
     */
    @Composable
    fun rememberCameraLauncher(
        onImageCaptured: (Boolean) -> Unit
    ): ManagedActivityResultLauncher<Uri, Boolean> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()
        ) { success ->
            onImageCaptured(success)
        }
    }
}
