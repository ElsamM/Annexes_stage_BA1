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
public final class ActivityBioMetricAuthBinding implements ViewBinding {
    public final Button bioauth;
    private final LinearLayout rootView;

    private ActivityBioMetricAuthBinding(LinearLayout rootView, Button bioauth) {
        this.rootView = rootView;
        this.bioauth = bioauth;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityBioMetricAuthBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityBioMetricAuthBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_bio_metric_auth, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityBioMetricAuthBinding bind(View rootView) {
        int id = R.id.bioauth;
        Button bioauth = (Button) ViewBindings.findChildViewById(rootView, id);
        if (bioauth != null) {
            return new ActivityBioMetricAuthBinding((LinearLayout) rootView, bioauth);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
