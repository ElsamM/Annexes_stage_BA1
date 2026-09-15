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
public final class ActivityCloudServicesBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final Button viewCloudServices;

    private ActivityCloudServicesBinding(LinearLayout rootView, Button viewCloudServices) {
        this.rootView = rootView;
        this.viewCloudServices = viewCloudServices;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCloudServicesBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCloudServicesBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_cloud_services, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityCloudServicesBinding bind(View rootView) {
        int id = R.id.viewCloudServices;
        Button viewCloudServices = (Button) ViewBindings.findChildViewById(rootView, id);
        if (viewCloudServices != null) {
            return new ActivityCloudServicesBinding((LinearLayout) rootView, viewCloudServices);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
