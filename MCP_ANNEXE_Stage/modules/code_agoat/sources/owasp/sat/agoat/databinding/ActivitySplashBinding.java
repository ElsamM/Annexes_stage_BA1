package owasp.sat.agoat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import owasp.sat.agoat.R;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivitySplashBinding implements ViewBinding {
    public final ImageView imageView;
    private final LinearLayout rootView;

    private ActivitySplashBinding(LinearLayout rootView, ImageView imageView) {
        this.rootView = rootView;
        this.imageView = imageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_splash, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivitySplashBinding bind(View rootView) {
        int id = R.id.imageView;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (imageView != null) {
            return new ActivitySplashBinding((LinearLayout) rootView, imageView);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
