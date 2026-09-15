package androidx.constraintlayout.core.motion.utils;

/* JADX INFO: loaded from: classes2.dex */
public class SpringStopEngine implements StopEngine {
    private static final double UNSET = Double.MAX_VALUE;
    private float mLastTime;
    private double mLastVelocity;
    private float mMass;
    private float mPos;
    private double mStiffness;
    private float mStopThreshold;
    private double mTargetPos;
    private float mV;
    double mDamping = 0.5d;
    private boolean mInitialized = false;
    private int mBoundaryMode = 0;

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public String debug(String desc, float time) {
        return null;
    }

    void log(String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        String line = ".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName() + "() ";
        System.out.println(line + str);
    }

    public void springConfig(float currentPos, float target, float currentVelocity, float mass, float stiffness, float damping, float stopThreshold, int boundaryMode) {
        this.mTargetPos = target;
        this.mDamping = damping;
        this.mInitialized = false;
        this.mPos = currentPos;
        this.mLastVelocity = currentVelocity;
        this.mStiffness = stiffness;
        this.mMass = mass;
        this.mStopThreshold = stopThreshold;
        this.mBoundaryMode = boundaryMode;
        this.mLastTime = 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity(float t) {
        return this.mV;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getInterpolation(float time) {
        compute(time - this.mLastTime);
        this.mLastTime = time;
        return this.mPos;
    }

    public float getAcceleration() {
        double k = this.mStiffness;
        double c = this.mDamping;
        double d = this.mPos;
        double d2 = this.mTargetPos;
        Double.isNaN(d);
        double x = d - d2;
        double d3 = this.mV;
        Double.isNaN(d3);
        return ((float) (((-k) * x) - (d3 * c))) / this.mMass;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public float getVelocity() {
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public boolean isStopped() {
        double d = this.mPos;
        double d2 = this.mTargetPos;
        Double.isNaN(d);
        double x = d - d2;
        double k = this.mStiffness;
        double v = this.mV;
        double m = this.mMass;
        Double.isNaN(v);
        Double.isNaN(v);
        Double.isNaN(m);
        double energy = (v * v * m) + (k * x * x);
        double max_def = Math.sqrt(energy / k);
        return max_def <= ((double) this.mStopThreshold);
    }

    private void compute(double dt) {
        double k = this.mStiffness;
        double c = this.mDamping;
        double d = this.mStiffness;
        double d2 = this.mMass;
        Double.isNaN(d2);
        int overSample = (int) ((9.0d / ((Math.sqrt(d / d2) * dt) * 4.0d)) + 1.0d);
        double d3 = overSample;
        Double.isNaN(d3);
        double dt2 = dt / d3;
        int i = 0;
        while (i < overSample) {
            double d4 = this.mPos;
            double d5 = this.mTargetPos;
            Double.isNaN(d4);
            double x = d4 - d5;
            double d6 = this.mV;
            Double.isNaN(d6);
            double d7 = ((-k) * x) - (d6 * c);
            double d8 = this.mMass;
            Double.isNaN(d8);
            double a = d7 / d8;
            double d9 = this.mV;
            Double.isNaN(d9);
            double avgV = d9 + ((a * dt2) / 2.0d);
            double k2 = k;
            double k3 = this.mPos;
            Double.isNaN(k3);
            double avgX = (k3 + ((dt2 * avgV) / 2.0d)) - this.mTargetPos;
            double c2 = c;
            double c3 = -avgX;
            double d10 = this.mMass;
            Double.isNaN(d10);
            double a2 = ((c3 * k2) - (avgV * c2)) / d10;
            double dv = a2 * dt2;
            double d11 = this.mV;
            Double.isNaN(d11);
            double d12 = this.mV;
            Double.isNaN(d12);
            this.mV = (float) (d12 + dv);
            double d13 = this.mPos;
            Double.isNaN(d13);
            this.mPos = (float) (d13 + ((d11 + (dv / 2.0d)) * dt2));
            if (this.mBoundaryMode > 0) {
                if (this.mPos < 0.0f && (this.mBoundaryMode & 1) == 1) {
                    this.mPos = -this.mPos;
                    this.mV = -this.mV;
                }
                if (this.mPos > 1.0f && (this.mBoundaryMode & 2) == 2) {
                    this.mPos = 2.0f - this.mPos;
                    this.mV = -this.mV;
                }
            }
            i++;
            c = c2;
            k = k2;
        }
    }
}
