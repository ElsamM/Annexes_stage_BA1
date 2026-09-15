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
public final class ActivityInsecureStorageSharedPrefs1Binding implements ViewBinding {
    public final Button getScore;
    public final TextView level;
    private final LinearLayout rootView;
    public final TextView score;

    private ActivityInsecureStorageSharedPrefs1Binding(LinearLayout rootView, Button getScore, TextView level, TextView score) {
        this.rootView = rootView;
        this.getScore = getScore;
        this.level = level;
        this.score = score;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityInsecureStorageSharedPrefs1Binding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityInsecureStorageSharedPrefs1Binding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_insecure_storage_shared_prefs1, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityInsecureStorageSharedPrefs1Binding bind(View rootView) {
        int id = R.id.getScore;
        Button getScore = (Button) ViewBindings.findChildViewById(rootView, id);
        if (getScore != null) {
            id = R.id.level;
            TextView level = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (level != null) {
                id = R.id.score;
                TextView score = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (score != null) {
                    return new ActivityInsecureStorageSharedPrefs1Binding((LinearLayout) rootView, getScore, level, score);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
