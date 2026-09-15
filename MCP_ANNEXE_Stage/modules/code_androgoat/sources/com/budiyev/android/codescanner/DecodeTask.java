package com.budiyev.android.codescanner;

import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;

/* JADX INFO: loaded from: classes2.dex */
final class DecodeTask {
    private final byte[] mImage;
    private final Point mImageSize;
    private final int mOrientation;
    private final Point mPreviewSize;
    private final boolean mReverseHorizontal;
    private final Rect mViewFrameRect;
    private final Point mViewSize;

    public DecodeTask(byte[] image, Point imageSize, Point previewSize, Point viewSize, Rect viewFrameRect, int orientation, boolean reverseHorizontal) {
        this.mImage = image;
        this.mImageSize = imageSize;
        this.mPreviewSize = previewSize;
        this.mViewSize = viewSize;
        this.mViewFrameRect = viewFrameRect;
        this.mOrientation = orientation;
        this.mReverseHorizontal = reverseHorizontal;
    }

    public Result decode(MultiFormatReader reader) throws ReaderException {
        int imageWidth;
        int imageHeight;
        int imageWidth2 = this.mImageSize.getX();
        int imageHeight2 = this.mImageSize.getY();
        int orientation = this.mOrientation;
        byte[] image = Utils.rotateYuv(this.mImage, imageWidth2, imageHeight2, orientation);
        if (orientation == 90 || orientation == 270) {
            imageWidth = imageHeight2;
            imageHeight = imageWidth2;
        } else {
            imageWidth = imageWidth2;
            imageHeight = imageHeight2;
        }
        Rect frameRect = Utils.getImageFrameRect(imageWidth, imageHeight, this.mViewFrameRect, this.mPreviewSize, this.mViewSize);
        int frameWidth = frameRect.getWidth();
        int frameHeight = frameRect.getHeight();
        if (frameWidth < 1 || frameHeight < 1) {
            return null;
        }
        return Utils.decodeLuminanceSource(reader, new PlanarYUVLuminanceSource(image, imageWidth, imageHeight, frameRect.getLeft(), frameRect.getTop(), frameWidth, frameHeight, this.mReverseHorizontal));
    }
}
