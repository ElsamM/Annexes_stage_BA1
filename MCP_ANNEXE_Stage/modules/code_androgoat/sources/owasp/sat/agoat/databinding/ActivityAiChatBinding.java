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
public final class ActivityAiChatBinding implements ViewBinding {
    public final Button postButton;
    public final EditText promptEditText;
    private final LinearLayout rootView;

    private ActivityAiChatBinding(LinearLayout rootView, Button postButton, EditText promptEditText) {
        this.rootView = rootView;
        this.postButton = postButton;
        this.promptEditText = promptEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAiChatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAiChatBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_ai_chat, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityAiChatBinding bind(View rootView) {
        int id = R.id.postButton;
        Button postButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (postButton != null) {
            id = R.id.promptEditText;
            EditText promptEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (promptEditText != null) {
                return new ActivityAiChatBinding((LinearLayout) rootView, postButton, promptEditText);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
