package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityAccessControlIssue1Binding implements ViewBinding {
    private final LinearLayout rootView;
    public final Button setPIN;
    public final EditText setpin;

    private ActivityAccessControlIssue1Binding(LinearLayout rootView, Button setPIN, EditText setpin) {
        this.rootView = rootView;
        this.setPIN = setPIN;
        this.setpin = setpin;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAccessControlIssue1Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAccessControlIssue1Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_access_control_issue1, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityAccessControlIssue1Binding bind(View rootView) {
        int id = R.id.setPIN;
        Button setPIN = (Button) ViewBindings.findChildViewById(rootView, id);
        if (setPIN != null) {
            id = R.id.setpin;
            EditText setpin = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (setpin != null) {
                return new ActivityAccessControlIssue1Binding((LinearLayout) rootView, setPIN, setpin);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
