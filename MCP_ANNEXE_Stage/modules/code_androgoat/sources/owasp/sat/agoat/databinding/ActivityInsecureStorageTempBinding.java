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
public final class ActivityInsecureStorageTempBinding implements ViewBinding {
    public final Button TempViewButton;
    public final EditText password;
    private final LinearLayout rootView;
    public final EditText userName;

    private ActivityInsecureStorageTempBinding(LinearLayout rootView, Button TempViewButton, EditText password, EditText userName) {
        this.rootView = rootView;
        this.TempViewButton = TempViewButton;
        this.password = password;
        this.userName = userName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInsecureStorageTempBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInsecureStorageTempBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_insecure_storage_temp, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInsecureStorageTempBinding bind(View rootView) {
        int id = R.id.TempViewButton;
        Button TempViewButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (TempViewButton != null) {
            id = R.id.password;
            EditText password = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (password != null) {
                id = R.id.userName;
                EditText userName = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (userName != null) {
                    return new ActivityInsecureStorageTempBinding((LinearLayout) rootView, TempViewButton, password, userName);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
