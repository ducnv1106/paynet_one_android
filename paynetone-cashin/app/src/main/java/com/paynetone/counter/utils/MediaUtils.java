package com.paynetone.counter.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.afollestad.materialcamera.MaterialCamera;
import com.paynetone.counter.R;

import java.io.File;

public class MediaUtils {
    public static void captureImage(Fragment fragment) {
        File saveDir = null;
        if (ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/PaynetOne/";
            saveDir = new File(path);
            saveDir.mkdirs();

        }
        MaterialCamera materialCamera = new MaterialCamera(fragment)
                .saveDir(saveDir)
                .showPortraitWarning(true)
                .allowRetry(true)
                .autoSubmit(true)
                .defaultToFrontFacing(false);

        materialCamera.stillShot(); // launches the Camera in stillshot mode
        materialCamera.labelRetry(R.string.cam_retry_capture);
        materialCamera.labelConfirm(R.string.cam_use_capture);
        materialCamera.start(Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }
}
