package com.afollestad.materialcamera.internal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.afollestad.materialcamera.R;

import com.bumptech.glide.Glide;
public class StillshotPreviewFragment extends BaseGalleryFragment {

    private ImageView mImageView;

    /**
     * Reference to the bitmap, in case 'onConfigurationChange' event comes, so we do not recreate the
     * bitmap
     */
    private static Bitmap mBitmap;

    public static StillshotPreviewFragment newInstance(
            String outputUri, boolean allowRetry, int primaryColor) {
        final StillshotPreviewFragment fragment = new StillshotPreviewFragment();
        fragment.setRetainInstance(true);
        Bundle args = new Bundle();
        args.putString("output_uri", outputUri);
        args.putBoolean(CameraIntentKey.ALLOW_RETRY, allowRetry);
        args.putInt(CameraIntentKey.PRIMARY_COLOR, primaryColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mcam_fragment_stillshot, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.stillshot_imageview);

        mConfirm.setText(mInterface.labelConfirm());
        mRetry.setText(mInterface.labelRetry());

        mRetry.setOnClickListener(this);
        mConfirm.setOnClickListener(this);

        Glide.with(getActivity())
                .asBitmap()
                .load(mOutputUri)
                .into(mImageView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            try {
                mBitmap.recycle();
                mBitmap = null;
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.retry) mInterface.onRetry(mOutputUri);
        else if (v.getId() == R.id.confirm) mInterface.useMedia(mOutputUri);
    }
}
