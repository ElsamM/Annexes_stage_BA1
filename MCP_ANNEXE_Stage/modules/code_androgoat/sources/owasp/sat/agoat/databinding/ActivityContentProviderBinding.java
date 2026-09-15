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
public final class ActivityContentProviderBinding implements ViewBinding {
    public final Button CPViewButton;
    public final EditText password;
    private final LinearLayout rootView;
    public final EditText userName;

    private ActivityContentProviderBinding(LinearLayout rootView, Button CPViewButton, EditText password, EditText userName) {
        this.rootView = rootView;
        this.CPViewButton = CPViewButton;
        this.password = password;
        this.userName = userName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityContentProviderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityContentProviderBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_content_provider, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityContentProviderBinding bind(View rootView) {
        int id = R.id.CPViewButton;
        Button CPViewButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (CPViewButton != null) {
            id = R.id.password;
            EditText password = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (password != null) {
                id = R.id.userName;
                EditText userName = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (userName != null) {
                    return new ActivityContentProviderBinding((LinearLayout) rootView, CPViewButton, password, userName);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
