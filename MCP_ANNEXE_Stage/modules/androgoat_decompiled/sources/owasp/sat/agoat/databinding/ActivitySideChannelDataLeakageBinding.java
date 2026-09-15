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
public final class ActivitySideChannelDataLeakageBinding implements ViewBinding {
    public final Button Dict;
    public final Button Logging;
    public final Button clipboard;
    private final LinearLayout rootView;

    private ActivitySideChannelDataLeakageBinding(LinearLayout rootView, Button Dict, Button Logging, Button clipboard) {
        this.rootView = rootView;
        this.Dict = Dict;
        this.Logging = Logging;
        this.clipboard = clipboard;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySideChannelDataLeakageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySideChannelDataLeakageBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_side_channel_data_leakage, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivitySideChannelDataLeakageBinding bind(View rootView) {
        int id = R.id.Dict;
        Button Dict = (Button) ViewBindings.findChildViewById(rootView, id);
        if (Dict != null) {
            id = R.id.Logging;
            Button Logging = (Button) ViewBindings.findChildViewById(rootView, id);
            if (Logging != null) {
                id = R.id.clipboard;
                Button clipboard = (Button) ViewBindings.findChildViewById(rootView, id);
                if (clipboard != null) {
                    return new ActivitySideChannelDataLeakageBinding((LinearLayout) rootView, Dict, Logging, clipboard);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
