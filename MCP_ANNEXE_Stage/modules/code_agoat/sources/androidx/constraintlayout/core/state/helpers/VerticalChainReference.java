package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;

/* JADX INFO: loaded from: classes2.dex */
public class VerticalChainReference extends ChainReference {
    public VerticalChainReference(State state) {
        super(state, State.Helper.VERTICAL_CHAIN);
    }

    @Override // androidx.constraintlayout.core.state.HelperReference, androidx.constraintlayout.core.state.ConstraintReference, androidx.constraintlayout.core.state.Reference
    public void apply() {
        ConstraintReference first = null;
        ConstraintReference previous = null;
        for (Object key : this.mReferences) {
            this.mState.constraints(key).clearVertical();
        }
        for (Object key2 : this.mReferences) {
            ConstraintReference reference = this.mState.constraints(key2);
            if (first == null) {
                first = reference;
                if (this.mTopToTop != null) {
                    first.topToTop(this.mTopToTop).margin(this.mMarginTop).marginGone(this.mMarginTopGone);
                } else if (this.mTopToBottom != null) {
                    first.topToBottom(this.mTopToBottom).margin(this.mMarginTop).marginGone(this.mMarginTopGone);
                } else {
                    first.topToTop(State.PARENT);
                }
            }
            if (previous != null) {
                previous.bottomToTop(reference.getKey());
                reference.topToBottom(previous.getKey());
            }
            previous = reference;
        }
        if (previous != null) {
            if (this.mBottomToTop != null) {
                previous.bottomToTop(this.mBottomToTop).margin(this.mMarginBottom).marginGone(this.mMarginBottomGone);
            } else if (this.mBottomToBottom != null) {
                previous.bottomToBottom(this.mBottomToBottom).margin(this.mMarginBottom).marginGone(this.mMarginBottomGone);
            } else {
                previous.bottomToBottom(State.PARENT);
            }
        }
        if (first == null) {
        }
        if (this.mBias != 0.5f) {
            first.verticalBias(this.mBias);
        }
        switch (this.mStyle) {
            case SPREAD:
                first.setVerticalChainStyle(0);
                break;
            case SPREAD_INSIDE:
                first.setVerticalChainStyle(1);
                break;
            case PACKED:
                first.setVerticalChainStyle(2);
                break;
        }
    }
}
