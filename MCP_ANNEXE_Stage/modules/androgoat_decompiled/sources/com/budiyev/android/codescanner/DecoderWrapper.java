package com.budiyev.android.codescanner;

import android.hardware.Camera;

/* JADX INFO: loaded from: classes2.dex */
final class DecoderWrapper {
    private final boolean mAutoFocusSupported;
    private final Camera mCamera;
    private final Camera.CameraInfo mCameraInfo;
    private final Decoder mDecoder;
    private final int mDisplayOrientation;
    private final boolean mFlashSupported;
    private final Point mImageSize;
    private final Point mPreviewSize;
    private final boolean mReverseHorizontal;
    private final Point mViewSize;

    public DecoderWrapper(Camera camera, Camera.CameraInfo cameraInfo, Decoder decoder, Point imageSize, Point previewSize, Point viewSize, int displayOrientation, boolean autoFocusSupported, boolean flashSupported) {
        this.mCamera = camera;
        this.mCameraInfo = cameraInfo;
        this.mDecoder = decoder;
        this.mImageSize = imageSize;
        this.mPreviewSize = previewSize;
        this.mViewSize = viewSize;
        this.mDisplayOrientation = displayOrientation;
        this.mReverseHorizontal = cameraInfo.facing == 1;
        this.mAutoFocusSupported = autoFocusSupported;
        this.mFlashSupported = flashSupported;
    }

    public Camera getCamera() {
        return this.mCamera;
    }

    public Camera.CameraInfo getCameraInfo() {
        return this.mCameraInfo;
    }

    public Decoder getDecoder() {
        return this.mDecoder;
    }

    public Point getImageSize() {
        return this.mImageSize;
    }

    public Point getPreviewSize() {
        return this.mPreviewSize;
    }

    public Point getViewSize() {
        return this.mViewSize;
    }

    public int getDisplayOrientation() {
        return this.mDisplayOrientation;
    }

    public boolean shouldReverseHorizontal() {
        return this.mReverseHorizontal;
    }

    public boolean isAutoFocusSupported() {
        return this.mAutoFocusSupported;
    }

    public boolean isFlashSupported() {
        return this.mFlashSupported;
    }

    public void release() {
        this.mCamera.release();
        this.mDecoder.shutdown();
    }
}
