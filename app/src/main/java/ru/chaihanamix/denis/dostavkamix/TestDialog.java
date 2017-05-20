package ru.chaihanamix.denis.dostavkamix;

import android.os.Bundle;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * Created by den on 06.02.2016.
 */
public class TestDialog extends BlurDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected float getDownScaleFactor() {
        return super.getDownScaleFactor();
    }

    @Override
    protected boolean isActionBarBlurred() {
        return super.isActionBarBlurred();
    }

    @Override
    protected boolean isDimmingEnable() {
        return super.isDimmingEnable();
    }

    @Override
    protected boolean isRenderScriptEnable() {
        return super.isRenderScriptEnable();
    }

    @Override
    protected int getBlurRadius() {
        return 7;
    }

    @Override
    protected boolean isDebugEnable() {
        return super.isDebugEnable();
    }
}
