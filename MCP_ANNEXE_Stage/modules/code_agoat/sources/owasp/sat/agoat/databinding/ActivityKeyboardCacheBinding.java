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
public final class ActivityKeyboardCacheBinding implements ViewBinding {
    public final Button Logging1;
    public final EditText password;
    private final LinearLayout rootView;
    public final EditText userName;

    private ActivityKeyboardCacheBinding(LinearLayout rootView, Button Logging1, EditText password, EditText userName) {
        this.rootView = rootView;
        this.Logging1 = Logging1;
        this.password = password;
        this.userName = userName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityKeyboardCacheBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityKeyboardCacheBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_keyboard_cache, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityKeyboardCacheBinding bind(View rootView) {
        int id = R.id.Logging1;
        Button Logging1 = (Button) ViewBindings.findChildViewById(rootView, id);
        if (Logging1 != null) {
            id = R.id.password;
            EditText password = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (password != null) {
                id = R.id.userName;
                EditText userName = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (userName != null) {
                    return new ActivityKeyboardCacheBinding((LinearLayout) rootView, Logging1, password, userName);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
