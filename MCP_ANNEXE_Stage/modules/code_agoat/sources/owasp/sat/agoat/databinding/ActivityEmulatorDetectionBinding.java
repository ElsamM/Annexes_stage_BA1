package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityEmulatorDetectionBinding implements ViewBinding {
    public final Button EmulatorCheck;
    private final LinearLayout rootView;

    private ActivityEmulatorDetectionBinding(LinearLayout rootView, Button EmulatorCheck) {
        this.rootView = rootView;
        this.EmulatorCheck = EmulatorCheck;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityEmulatorDetectionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityEmulatorDetectionBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_emulator_detection, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityEmulatorDetectionBinding bind(View rootView) {
        int id = R.id.EmulatorCheck;
        Button EmulatorCheck = (Button) ViewBindings.findChildViewById(rootView, id);
        if (EmulatorCheck != null) {
            return new ActivityEmulatorDetectionBinding((LinearLayout) rootView, EmulatorCheck);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
