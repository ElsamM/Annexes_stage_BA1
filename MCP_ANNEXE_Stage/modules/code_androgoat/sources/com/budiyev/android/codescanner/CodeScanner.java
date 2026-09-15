package com.budiyev.android.codescanner;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Process;
import android.view.SurfaceHolder;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.Decoder;
import com.google.zxing.BarcodeFormat;
import java.lang.Thread;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: loaded from: classes2.dex */
public final class CodeScanner {
    public static final int CAMERA_BACK = -1;
    public static final int CAMERA_FRONT = -2;
    private static final boolean DEFAULT_AUTO_FOCUS_ENABLED = true;
    private static final boolean DEFAULT_FLASH_ENABLED = false;
    private static final long DEFAULT_SAFE_AUTO_FOCUS_INTERVAL = 2000;
    private static final boolean DEFAULT_TOUCH_FOCUS_ENABLED = true;
    private static final int SAFE_AUTO_FOCUS_ATTEMPTS_THRESHOLD = 2;
    private volatile boolean mAutoFocusEnabled;
    private volatile AutoFocusMode mAutoFocusMode;
    private volatile int mCameraId;
    private final Context mContext;
    private volatile DecodeCallback mDecodeCallback;
    private final DecoderStateListener mDecoderStateListener;
    private volatile DecoderWrapper mDecoderWrapper;
    private volatile ErrorCallback mErrorCallback;
    private final ExceptionHandler mExceptionHandler;
    private volatile boolean mFlashEnabled;
    private volatile List<BarcodeFormat> mFormats;
    private volatile boolean mInitialization;
    private boolean mInitializationRequested;
    private final Object mInitializeLock;
    private volatile boolean mInitialized;
    private final Handler mMainThreadHandler;
    private boolean mPreviewActive;
    private final Camera.PreviewCallback mPreviewCallback;
    private int mSafeAutoFocusAttemptsCount;
    private final Camera.AutoFocusCallback mSafeAutoFocusCallback;
    private volatile long mSafeAutoFocusInterval;
    private final Runnable mSafeAutoFocusTask;
    private boolean mSafeAutoFocusTaskScheduled;
    private boolean mSafeAutoFocusing;
    private volatile ScanMode mScanMode;
    private final CodeScannerView mScannerView;
    private final Runnable mStopPreviewTask;
    private volatile boolean mStoppingPreview;
    private final SurfaceHolder.Callback mSurfaceCallback;
    private final SurfaceHolder mSurfaceHolder;
    private final Camera.AutoFocusCallback mTouchFocusCallback;
    private boolean mTouchFocusEnabled;
    private boolean mTouchFocusing;
    private int mViewHeight;
    private int mViewWidth;
    private volatile int mZoom;
    public static final List<BarcodeFormat> ALL_FORMATS = Collections.unmodifiableList(Arrays.asList(BarcodeFormat.values()));
    public static final List<BarcodeFormat> ONE_DIMENSIONAL_FORMATS = Collections.unmodifiableList(Arrays.asList(BarcodeFormat.CODABAR, BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.EAN_8, BarcodeFormat.EAN_13, BarcodeFormat.ITF, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED, BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.UPC_EAN_EXTENSION));
    public static final List<BarcodeFormat> TWO_DIMENSIONAL_FORMATS = Collections.unmodifiableList(Arrays.asList(BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX, BarcodeFormat.MAXICODE, BarcodeFormat.PDF_417, BarcodeFormat.QR_CODE));
    private static final List<BarcodeFormat> DEFAULT_FORMATS = ALL_FORMATS;
    private static final ScanMode DEFAULT_SCAN_MODE = ScanMode.SINGLE;
    private static final AutoFocusMode DEFAULT_AUTO_FOCUS_MODE = AutoFocusMode.SAFE;

    public CodeScanner(Context context, CodeScannerView view) {
        this.mInitializeLock = new Object();
        this.mFormats = DEFAULT_FORMATS;
        this.mScanMode = DEFAULT_SCAN_MODE;
        this.mAutoFocusMode = DEFAULT_AUTO_FOCUS_MODE;
        this.mDecodeCallback = null;
        this.mErrorCallback = null;
        this.mDecoderWrapper = null;
        this.mInitialization = false;
        this.mInitialized = false;
        this.mStoppingPreview = false;
        this.mAutoFocusEnabled = true;
        this.mFlashEnabled = false;
        this.mSafeAutoFocusInterval = DEFAULT_SAFE_AUTO_FOCUS_INTERVAL;
        this.mCameraId = -1;
        this.mZoom = 0;
        this.mTouchFocusEnabled = true;
        this.mTouchFocusing = false;
        this.mPreviewActive = false;
        this.mSafeAutoFocusing = false;
        this.mSafeAutoFocusTaskScheduled = false;
        this.mInitializationRequested = false;
        this.mSafeAutoFocusAttemptsCount = 0;
        this.mViewWidth = 0;
        this.mViewHeight = 0;
        this.mContext = context;
        this.mScannerView = view;
        this.mSurfaceHolder = view.getPreviewView().getHolder();
        this.mMainThreadHandler = new Handler();
        this.mSurfaceCallback = new SurfaceCallback();
        this.mPreviewCallback = new PreviewCallback();
        this.mTouchFocusCallback = new TouchFocusCallback();
        this.mSafeAutoFocusCallback = new SafeAutoFocusCallback();
        this.mSafeAutoFocusTask = new SafeAutoFocusTask();
        this.mStopPreviewTask = new StopPreviewTask();
        this.mDecoderStateListener = new DecoderStateListener();
        this.mExceptionHandler = new ExceptionHandler();
        this.mScannerView.setCodeScanner(this);
        this.mScannerView.setSizeListener(new ScannerSizeListener());
    }

    public CodeScanner(Context context, CodeScannerView view, int cameraId) {
        this(context, view);
        this.mCameraId = cameraId;
    }

    public int getCamera() {
        return this.mCameraId;
    }

    public void setCamera(int cameraId) {
        synchronized (this.mInitializeLock) {
            if (this.mCameraId != cameraId) {
                this.mCameraId = cameraId;
                if (this.mInitialized) {
                    boolean previewActive = this.mPreviewActive;
                    releaseResources();
                    if (previewActive) {
                        initialize();
                    }
                }
            }
        }
    }

    public List<BarcodeFormat> getFormats() {
        return this.mFormats;
    }

    public void setFormats(List<BarcodeFormat> formats) {
        DecoderWrapper decoderWrapper;
        synchronized (this.mInitializeLock) {
            this.mFormats = (List) Objects.requireNonNull(formats);
            if (this.mInitialized && (decoderWrapper = this.mDecoderWrapper) != null) {
                decoderWrapper.getDecoder().setFormats(formats);
            }
        }
    }

    public DecodeCallback getDecodeCallback() {
        return this.mDecodeCallback;
    }

    public void setDecodeCallback(DecodeCallback decodeCallback) {
        DecoderWrapper decoderWrapper;
        synchronized (this.mInitializeLock) {
            this.mDecodeCallback = decodeCallback;
            if (this.mInitialized && (decoderWrapper = this.mDecoderWrapper) != null) {
                decoderWrapper.getDecoder().setCallback(decodeCallback);
            }
        }
    }

    public ErrorCallback getErrorCallback() {
        return this.mErrorCallback;
    }

    public void setErrorCallback(ErrorCallback errorCallback) {
        this.mErrorCallback = errorCallback;
    }

    public ScanMode getScanMode() {
        return this.mScanMode;
    }

    public void setScanMode(ScanMode scanMode) {
        this.mScanMode = (ScanMode) Objects.requireNonNull(scanMode);
    }

    public int getZoom() {
        return this.mZoom;
    }

    public void setZoom(int zoom) {
        DecoderWrapper decoderWrapper;
        if (zoom < 0) {
            throw new IllegalArgumentException("Zoom value must be greater than or equal to zero");
        }
        synchronized (this.mInitializeLock) {
            if (zoom != this.mZoom) {
                this.mZoom = zoom;
                if (this.mInitialized && (decoderWrapper = this.mDecoderWrapper) != null) {
                    Camera camera = decoderWrapper.getCamera();
                    Camera.Parameters parameters = camera.getParameters();
                    Utils.setZoom(parameters, zoom);
                    camera.setParameters(parameters);
                }
            }
        }
        this.mZoom = zoom;
    }

    public boolean isTouchFocusEnabled() {
        return this.mTouchFocusEnabled;
    }

    public void setTouchFocusEnabled(boolean touchFocusEnabled) {
        this.mTouchFocusEnabled = touchFocusEnabled;
    }

    public boolean isAutoFocusEnabled() {
        return this.mAutoFocusEnabled;
    }

    public void setAutoFocusEnabled(boolean autoFocusEnabled) {
        synchronized (this.mInitializeLock) {
            boolean changed = this.mAutoFocusEnabled != autoFocusEnabled;
            this.mAutoFocusEnabled = autoFocusEnabled;
            this.mScannerView.setAutoFocusEnabled(autoFocusEnabled);
            DecoderWrapper decoderWrapper = this.mDecoderWrapper;
            if (this.mInitialized && this.mPreviewActive && changed && decoderWrapper != null && decoderWrapper.isAutoFocusSupported()) {
                setAutoFocusEnabledInternal(autoFocusEnabled);
            }
        }
    }

    public AutoFocusMode getAutoFocusMode() {
        return this.mAutoFocusMode;
    }

    public void setAutoFocusMode(AutoFocusMode autoFocusMode) {
        synchronized (this.mInitializeLock) {
            this.mAutoFocusMode = (AutoFocusMode) Objects.requireNonNull(autoFocusMode);
            if (this.mInitialized && this.mAutoFocusEnabled) {
                setAutoFocusEnabledInternal(true);
            }
        }
    }

    public void setAutoFocusInterval(long autoFocusInterval) {
        this.mSafeAutoFocusInterval = autoFocusInterval;
    }

    public boolean isFlashEnabled() {
        return this.mFlashEnabled;
    }

    public void setFlashEnabled(boolean flashEnabled) {
        synchronized (this.mInitializeLock) {
            boolean changed = this.mFlashEnabled != flashEnabled;
            this.mFlashEnabled = flashEnabled;
            this.mScannerView.setFlashEnabled(flashEnabled);
            DecoderWrapper decoderWrapper = this.mDecoderWrapper;
            if (this.mInitialized && this.mPreviewActive && changed && decoderWrapper != null && decoderWrapper.isFlashSupported()) {
                setFlashEnabledInternal(flashEnabled);
            }
        }
    }

    public boolean isPreviewActive() {
        return this.mPreviewActive;
    }

    public void startPreview() {
        synchronized (this.mInitializeLock) {
            if (!this.mInitialized && !this.mInitialization) {
                initialize();
            } else if (!this.mPreviewActive) {
                this.mSurfaceHolder.addCallback(this.mSurfaceCallback);
                startPreviewInternal(false);
            }
        }
    }

    public void stopPreview() {
        if (this.mInitialized && this.mPreviewActive) {
            this.mSurfaceHolder.removeCallback(this.mSurfaceCallback);
            stopPreviewInternal(false);
        }
    }

    public void releaseResources() {
        if (this.mInitialized) {
            if (this.mPreviewActive) {
                stopPreview();
            }
            releaseResourcesInternal();
        }
    }

    void performTouchFocus(Rect viewFocusArea) {
        synchronized (this.mInitializeLock) {
            if (this.mInitialized && this.mPreviewActive && !this.mTouchFocusing) {
                try {
                    setAutoFocusEnabled(false);
                    DecoderWrapper decoderWrapper = this.mDecoderWrapper;
                    if (this.mPreviewActive && decoderWrapper != null && decoderWrapper.isAutoFocusSupported()) {
                        Point imageSize = decoderWrapper.getImageSize();
                        int imageWidth = imageSize.getX();
                        int imageHeight = imageSize.getY();
                        int orientation = decoderWrapper.getDisplayOrientation();
                        if (orientation == 90 || orientation == 270) {
                            imageWidth = imageHeight;
                            imageHeight = imageWidth;
                        }
                        Rect imageArea = Utils.getImageFrameRect(imageWidth, imageHeight, viewFocusArea, decoderWrapper.getPreviewSize(), decoderWrapper.getViewSize());
                        Camera camera = decoderWrapper.getCamera();
                        camera.cancelAutoFocus();
                        Camera.Parameters parameters = camera.getParameters();
                        Utils.configureFocusArea(parameters, imageArea, imageWidth, imageHeight, orientation);
                        Utils.configureFocusModeForTouch(parameters);
                        camera.setParameters(parameters);
                        camera.autoFocus(this.mTouchFocusCallback);
                        this.mTouchFocusing = true;
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    boolean isAutoFocusSupportedOrUnknown() {
        DecoderWrapper wrapper = this.mDecoderWrapper;
        return wrapper == null || wrapper.isAutoFocusSupported();
    }

    boolean isFlashSupportedOrUnknown() {
        DecoderWrapper wrapper = this.mDecoderWrapper;
        return wrapper == null || wrapper.isFlashSupported();
    }

    private void initialize() {
        initialize(this.mScannerView.getWidth(), this.mScannerView.getHeight());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initialize(int width, int height) {
        this.mViewWidth = width;
        this.mViewHeight = height;
        if (width > 0 && height > 0) {
            this.mInitialization = true;
            this.mInitializationRequested = false;
            InitializationThread initializationThread = new InitializationThread(width, height);
            initializationThread.setUncaughtExceptionHandler(this.mExceptionHandler);
            initializationThread.start();
            return;
        }
        this.mInitializationRequested = true;
    }

    private void startPreviewInternal(boolean internal) {
        try {
            DecoderWrapper decoderWrapper = this.mDecoderWrapper;
            if (decoderWrapper != null) {
                Camera camera = decoderWrapper.getCamera();
                camera.setPreviewCallback(this.mPreviewCallback);
                camera.setPreviewDisplay(this.mSurfaceHolder);
                if (!internal && decoderWrapper.isFlashSupported() && this.mFlashEnabled) {
                    setFlashEnabledInternal(true);
                }
                camera.startPreview();
                this.mStoppingPreview = false;
                this.mPreviewActive = true;
                this.mSafeAutoFocusing = false;
                this.mSafeAutoFocusAttemptsCount = 0;
                if (decoderWrapper.isAutoFocusSupported() && this.mAutoFocusEnabled) {
                    Rect frameRect = this.mScannerView.getFrameRect();
                    if (frameRect != null) {
                        Camera.Parameters parameters = camera.getParameters();
                        Utils.configureDefaultFocusArea(parameters, decoderWrapper, frameRect);
                        camera.setParameters(parameters);
                    }
                    if (this.mAutoFocusMode == AutoFocusMode.SAFE) {
                        scheduleSafeAutoFocusTask();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPreviewInternalSafe() {
        if (this.mInitialized && !this.mPreviewActive) {
            startPreviewInternal(true);
        }
    }

    private void stopPreviewInternal(boolean internal) {
        try {
            DecoderWrapper decoderWrapper = this.mDecoderWrapper;
            if (decoderWrapper != null) {
                Camera camera = decoderWrapper.getCamera();
                camera.cancelAutoFocus();
                Camera.Parameters parameters = camera.getParameters();
                if (!internal && decoderWrapper.isFlashSupported() && this.mFlashEnabled) {
                    Utils.setFlashMode(parameters, DebugKt.DEBUG_PROPERTY_VALUE_OFF);
                }
                camera.setParameters(parameters);
                camera.setPreviewCallback(null);
                camera.stopPreview();
            }
        } catch (Exception e) {
        }
        this.mStoppingPreview = false;
        this.mPreviewActive = false;
        this.mSafeAutoFocusing = false;
        this.mSafeAutoFocusAttemptsCount = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopPreviewInternalSafe() {
        if (this.mInitialized && this.mPreviewActive) {
            stopPreviewInternal(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResourcesInternal() {
        this.mInitialized = false;
        this.mInitialization = false;
        this.mStoppingPreview = false;
        this.mPreviewActive = false;
        this.mSafeAutoFocusing = false;
        DecoderWrapper decoderWrapper = this.mDecoderWrapper;
        if (decoderWrapper != null) {
            this.mDecoderWrapper = null;
            decoderWrapper.release();
        }
    }

    private void setFlashEnabledInternal(boolean flashEnabled) {
        Camera camera;
        Camera.Parameters parameters;
        try {
            DecoderWrapper decoderWrapper = this.mDecoderWrapper;
            if (decoderWrapper == null || (parameters = (camera = decoderWrapper.getCamera()).getParameters()) == null) {
                return;
            }
            if (flashEnabled) {
                Utils.setFlashMode(parameters, "torch");
            } else {
                Utils.setFlashMode(parameters, DebugKt.DEBUG_PROPERTY_VALUE_OFF);
            }
            camera.setParameters(parameters);
        } catch (Exception e) {
        }
    }

    private void setAutoFocusEnabledInternal(boolean autoFocusEnabled) {
        Rect frameRect;
        try {
            DecoderWrapper decoderWrapper = this.mDecoderWrapper;
            if (decoderWrapper != null) {
                Camera camera = decoderWrapper.getCamera();
                camera.cancelAutoFocus();
                this.mTouchFocusing = false;
                Camera.Parameters parameters = camera.getParameters();
                AutoFocusMode autoFocusMode = this.mAutoFocusMode;
                if (autoFocusEnabled) {
                    Utils.setAutoFocusMode(parameters, autoFocusMode);
                } else {
                    Utils.disableAutoFocus(parameters);
                }
                if (autoFocusEnabled && (frameRect = this.mScannerView.getFrameRect()) != null) {
                    Utils.configureDefaultFocusArea(parameters, decoderWrapper, frameRect);
                }
                camera.setParameters(parameters);
                if (autoFocusEnabled) {
                    this.mSafeAutoFocusAttemptsCount = 0;
                    this.mSafeAutoFocusing = false;
                    if (autoFocusMode == AutoFocusMode.SAFE) {
                        scheduleSafeAutoFocusTask();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void safeAutoFocusCamera() {
        DecoderWrapper decoderWrapper;
        if (!this.mInitialized || !this.mPreviewActive || (decoderWrapper = this.mDecoderWrapper) == null || !decoderWrapper.isAutoFocusSupported() || !this.mAutoFocusEnabled) {
            return;
        }
        if (this.mSafeAutoFocusing && this.mSafeAutoFocusAttemptsCount < 2) {
            this.mSafeAutoFocusAttemptsCount++;
        } else {
            try {
                Camera camera = decoderWrapper.getCamera();
                camera.cancelAutoFocus();
                camera.autoFocus(this.mSafeAutoFocusCallback);
                this.mSafeAutoFocusAttemptsCount = 0;
                this.mSafeAutoFocusing = true;
            } catch (Exception e) {
                this.mSafeAutoFocusing = false;
            }
        }
        scheduleSafeAutoFocusTask();
    }

    private void scheduleSafeAutoFocusTask() {
        if (this.mSafeAutoFocusTaskScheduled) {
            return;
        }
        this.mSafeAutoFocusTaskScheduled = true;
        this.mMainThreadHandler.postDelayed(this.mSafeAutoFocusTask, this.mSafeAutoFocusInterval);
    }

    private final class ScannerSizeListener implements CodeScannerView.SizeListener {
        private ScannerSizeListener() {
        }

        @Override // com.budiyev.android.codescanner.CodeScannerView.SizeListener
        public void onSizeChanged(int width, int height) {
            synchronized (CodeScanner.this.mInitializeLock) {
                if (width != CodeScanner.this.mViewWidth || height != CodeScanner.this.mViewHeight) {
                    boolean previewActive = CodeScanner.this.mPreviewActive;
                    if (CodeScanner.this.mInitialized) {
                        CodeScanner.this.releaseResources();
                    }
                    if (previewActive || CodeScanner.this.mInitializationRequested) {
                        CodeScanner.this.initialize(width, height);
                    }
                }
            }
        }
    }

    private final class PreviewCallback implements Camera.PreviewCallback {
        private PreviewCallback() {
        }

        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(byte[] data, Camera camera) {
            DecoderWrapper decoderWrapper;
            if (!CodeScanner.this.mInitialized || CodeScanner.this.mStoppingPreview || CodeScanner.this.mScanMode == ScanMode.PREVIEW) {
                return;
            }
            if (data == null || (decoderWrapper = CodeScanner.this.mDecoderWrapper) == null) {
                return;
            }
            Decoder decoder = decoderWrapper.getDecoder();
            if (decoder.getState() == Decoder.State.IDLE) {
                Rect frameRect = CodeScanner.this.mScannerView.getFrameRect();
                if (frameRect == null || frameRect.getWidth() < 1) {
                    return;
                }
                if (frameRect.getHeight() < 1) {
                    return;
                }
                decoder.decode(new DecodeTask(data, decoderWrapper.getImageSize(), decoderWrapper.getPreviewSize(), decoderWrapper.getViewSize(), frameRect, decoderWrapper.getDisplayOrientation(), decoderWrapper.shouldReverseHorizontal()));
            }
        }
    }

    private final class SurfaceCallback implements SurfaceHolder.Callback {
        private SurfaceCallback() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder holder) {
            CodeScanner.this.startPreviewInternalSafe();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder.getSurface() == null) {
                CodeScanner.this.mPreviewActive = false;
            } else {
                CodeScanner.this.stopPreviewInternalSafe();
                CodeScanner.this.startPreviewInternalSafe();
            }
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {
            CodeScanner.this.stopPreviewInternalSafe();
        }
    }

    private final class DecoderStateListener implements Decoder.StateListener {
        private DecoderStateListener() {
        }

        @Override // com.budiyev.android.codescanner.Decoder.StateListener
        public boolean onStateChanged(Decoder.State state) {
            if (state == Decoder.State.DECODED) {
                ScanMode scanMode = CodeScanner.this.mScanMode;
                if (scanMode == ScanMode.PREVIEW) {
                    return false;
                }
                if (scanMode == ScanMode.SINGLE) {
                    CodeScanner.this.mStoppingPreview = true;
                    CodeScanner.this.mMainThreadHandler.post(CodeScanner.this.mStopPreviewTask);
                }
            }
            return true;
        }
    }

    private final class InitializationThread extends Thread {
        private final int mHeight;
        private final int mWidth;

        public InitializationThread(int width, int height) {
            super("cs-init");
            this.mWidth = width;
            this.mHeight = height;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws Throwable {
            Process.setThreadPriority(10);
            initialize();
        }

        private void initialize() throws Throwable {
            Camera camera;
            Decoder decoder;
            Camera camera2 = null;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int cameraId = CodeScanner.this.mCameraId;
            if (cameraId == -1 || cameraId == -2) {
                int numberOfCameras = Camera.getNumberOfCameras();
                int facing = cameraId == -1 ? 0 : 1;
                int i = 0;
                while (true) {
                    if (i >= numberOfCameras) {
                        break;
                    }
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing != facing) {
                        i++;
                    } else {
                        camera2 = Camera.open(i);
                        CodeScanner.this.mCameraId = i;
                        break;
                    }
                }
                camera = camera2;
            } else {
                Camera camera3 = Camera.open(cameraId);
                Camera.getCameraInfo(cameraId, cameraInfo);
                camera = camera3;
            }
            if (camera == null) {
                throw new CodeScannerException("Unable to access camera");
            }
            Camera.Parameters parameters = camera.getParameters();
            if (parameters == null) {
                throw new CodeScannerException("Unable to configure camera");
            }
            int orientation = Utils.getDisplayOrientation(CodeScanner.this.mContext, cameraInfo);
            boolean portrait = Utils.isPortrait(orientation);
            Point imageSize = Utils.findSuitableImageSize(parameters, portrait ? this.mHeight : this.mWidth, portrait ? this.mWidth : this.mHeight);
            int imageWidth = imageSize.getX();
            int imageHeight = imageSize.getY();
            parameters.setPreviewSize(imageWidth, imageHeight);
            parameters.setPreviewFormat(17);
            Point previewSize = Utils.getPreviewSize(portrait ? imageHeight : imageWidth, portrait ? imageWidth : imageHeight, this.mWidth, this.mHeight);
            List<String> focusModes = parameters.getSupportedFocusModes();
            boolean autoFocusSupported = focusModes != null && (focusModes.contains(DebugKt.DEBUG_PROPERTY_VALUE_AUTO) || focusModes.contains("continuous-picture"));
            if (!autoFocusSupported) {
                CodeScanner.this.mAutoFocusEnabled = false;
            }
            Point viewSize = new Point(this.mWidth, this.mHeight);
            if (autoFocusSupported && CodeScanner.this.mAutoFocusEnabled) {
                Utils.setAutoFocusMode(parameters, CodeScanner.this.mAutoFocusMode);
                Rect frameRect = CodeScanner.this.mScannerView.getFrameRect();
                if (frameRect != null) {
                    Utils.configureDefaultFocusArea(parameters, frameRect, previewSize, viewSize, imageWidth, imageHeight, orientation);
                }
            }
            List<String> flashModes = parameters.getSupportedFlashModes();
            boolean flashSupported = flashModes != null && flashModes.contains("torch");
            if (!flashSupported) {
                CodeScanner.this.mFlashEnabled = false;
            }
            int zoom = CodeScanner.this.mZoom;
            if (zoom != 0) {
                Utils.setZoom(parameters, zoom);
            }
            Utils.configureFpsRange(parameters);
            Utils.configureSceneMode(parameters);
            Utils.configureVideoStabilization(parameters);
            camera.setParameters(parameters);
            camera.setDisplayOrientation(orientation);
            synchronized (CodeScanner.this.mInitializeLock) {
                try {
                    try {
                        try {
                            try {
                                Camera camera4 = camera;
                                try {
                                    decoder = new Decoder(CodeScanner.this.mDecoderStateListener, CodeScanner.this.mExceptionHandler, CodeScanner.this.mFormats, CodeScanner.this.mDecodeCallback);
                                } catch (Throwable th) {
                                    th = th;
                                }
                                try {
                                    CodeScanner.this.mDecoderWrapper = new DecoderWrapper(camera4, cameraInfo, decoder, imageSize, previewSize, viewSize, orientation, autoFocusSupported, flashSupported);
                                    decoder.start();
                                    CodeScanner.this.mInitialization = false;
                                    CodeScanner.this.mInitialized = true;
                                    CodeScanner.this.mMainThreadHandler.post(new FinishInitializationTask(previewSize));
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (Throwable th6) {
                    th = th6;
                }
            }
        }
    }

    private final class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        private ExceptionHandler() {
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread t, Throwable e) {
            CodeScanner.this.releaseResourcesInternal();
            ErrorCallback errorCallback = CodeScanner.this.mErrorCallback;
            if (errorCallback != null) {
                errorCallback.onError(e);
                return;
            }
            throw new CodeScannerException(e);
        }
    }

    private final class TouchFocusCallback implements Camera.AutoFocusCallback {
        private TouchFocusCallback() {
        }

        @Override // android.hardware.Camera.AutoFocusCallback
        public void onAutoFocus(boolean success, Camera camera) {
            CodeScanner.this.mTouchFocusing = false;
        }
    }

    private final class SafeAutoFocusCallback implements Camera.AutoFocusCallback {
        private SafeAutoFocusCallback() {
        }

        @Override // android.hardware.Camera.AutoFocusCallback
        public void onAutoFocus(boolean success, Camera camera) {
            CodeScanner.this.mSafeAutoFocusing = false;
        }
    }

    private final class SafeAutoFocusTask implements Runnable {
        private SafeAutoFocusTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            CodeScanner.this.mSafeAutoFocusTaskScheduled = false;
            if (CodeScanner.this.mAutoFocusMode == AutoFocusMode.SAFE) {
                CodeScanner.this.safeAutoFocusCamera();
            }
        }
    }

    private final class StopPreviewTask implements Runnable {
        private StopPreviewTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            CodeScanner.this.stopPreview();
        }
    }

    private final class FinishInitializationTask implements Runnable {
        private final Point mPreviewSize;

        private FinishInitializationTask(Point previewSize) {
            this.mPreviewSize = previewSize;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (CodeScanner.this.mInitialized) {
                CodeScanner.this.mScannerView.setPreviewSize(this.mPreviewSize);
                CodeScanner.this.mScannerView.setAutoFocusEnabled(CodeScanner.this.isAutoFocusEnabled());
                CodeScanner.this.mScannerView.setFlashEnabled(CodeScanner.this.isFlashEnabled());
                CodeScanner.this.startPreview();
            }
        }
    }
}
