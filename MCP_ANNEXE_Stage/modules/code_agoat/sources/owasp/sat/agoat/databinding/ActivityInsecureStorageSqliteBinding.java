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
public final class ActivityInsecureStorageSqliteBinding implements ViewBinding {
    public final Button SQLButton;
    public final EditText password;
    private final LinearLayout rootView;
    public final EditText userName;

    private ActivityInsecureStorageSqliteBinding(LinearLayout rootView, Button SQLButton, EditText password, EditText userName) {
        this.rootView = rootView;
        this.SQLButton = SQLButton;
        this.password = password;
        this.userName = userName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInsecureStorageSqliteBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInsecureStorageSqliteBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_insecure_storage_sqlite, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInsecureStorageSqliteBinding bind(View rootView) {
        int id = R.id.SQLButton;
        Button SQLButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (SQLButton != null) {
            id = R.id.password;
            EditText password = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (password != null) {
                id = R.id.userName;
                EditText userName = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (userName != null) {
                    return new ActivityInsecureStorageSqliteBinding((LinearLayout) rootView, SQLButton, password, userName);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
