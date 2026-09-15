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
public final class ActivityHardcodeViewBinding implements ViewBinding {
    public final Button CloudButton;
    public final Button aiChatButton;
    public final Button buyProductButton;
    private final LinearLayout rootView;

    private ActivityHardcodeViewBinding(LinearLayout rootView, Button CloudButton, Button aiChatButton, Button buyProductButton) {
        this.rootView = rootView;
        this.CloudButton = CloudButton;
        this.aiChatButton = aiChatButton;
        this.buyProductButton = buyProductButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityHardcodeViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityHardcodeViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_hardcode_view, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityHardcodeViewBinding bind(View rootView) {
        int id = R.id.CloudButton;
        Button CloudButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (CloudButton != null) {
            id = R.id.aiChatButton;
            Button aiChatButton = (Button) ViewBindings.findChildViewById(rootView, id);
            if (aiChatButton != null) {
                id = R.id.buyProductButton;
                Button buyProductButton = (Button) ViewBindings.findChildViewById(rootView, id);
                if (buyProductButton != null) {
                    return new ActivityHardcodeViewBinding((LinearLayout) rootView, CloudButton, aiChatButton, buyProductButton);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
