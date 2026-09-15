package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityBinaryPatchingBinding implements ViewBinding {
    public final Button adminButton;
    public final TextView isAdminText;
    private final LinearLayout rootView;

    private ActivityBinaryPatchingBinding(LinearLayout rootView, Button adminButton, TextView isAdminText) {
        this.rootView = rootView;
        this.adminButton = adminButton;
        this.isAdminText = isAdminText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityBinaryPatchingBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityBinaryPatchingBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_binary_patching, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityBinaryPatchingBinding bind(View rootView) {
        int id = R.id.adminButton;
        Button adminButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (adminButton != null) {
            id = R.id.isAdminText;
            TextView isAdminText = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (isAdminText != null) {
                return new ActivityBinaryPatchingBinding((LinearLayout) rootView, adminButton, isAdminText);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
