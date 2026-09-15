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
public final class ActivityTrafficBinding implements ViewBinding {
    public final Button PinningButton;
    public final Button PinningButton1;
    public final Button httpButton;
    public final Button httpsButton;
    private final LinearLayout rootView;

    private ActivityTrafficBinding(LinearLayout rootView, Button PinningButton, Button PinningButton1, Button httpButton, Button httpsButton) {
        this.rootView = rootView;
        this.PinningButton = PinningButton;
        this.PinningButton1 = PinningButton1;
        this.httpButton = httpButton;
        this.httpsButton = httpsButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityTrafficBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityTrafficBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_traffic, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityTrafficBinding bind(View rootView) {
        int id = R.id.PinningButton;
        Button PinningButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (PinningButton != null) {
            id = R.id.PinningButton1;
            Button PinningButton1 = (Button) ViewBindings.findChildViewById(rootView, id);
            if (PinningButton1 != null) {
                id = R.id.httpButton;
                Button httpButton = (Button) ViewBindings.findChildViewById(rootView, id);
                if (httpButton != null) {
                    id = R.id.httpsButton;
                    Button httpsButton = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (httpsButton != null) {
                        return new ActivityTrafficBinding((LinearLayout) rootView, PinningButton, PinningButton1, httpButton, httpsButton);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
