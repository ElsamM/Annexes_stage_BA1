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
public final class ActivityAccessControl1ViewBinding implements ViewBinding {
    public final Button downLoad;
    private final LinearLayout rootView;

    private ActivityAccessControl1ViewBinding(LinearLayout rootView, Button downLoad) {
        this.rootView = rootView;
        this.downLoad = downLoad;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAccessControl1ViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAccessControl1ViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_access_control1_view, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityAccessControl1ViewBinding bind(View rootView) {
        int id = R.id.downLoad;
        Button downLoad = (Button) ViewBindings.findChildViewById(rootView, id);
        if (downLoad != null) {
            return new ActivityAccessControl1ViewBinding((LinearLayout) rootView, downLoad);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
