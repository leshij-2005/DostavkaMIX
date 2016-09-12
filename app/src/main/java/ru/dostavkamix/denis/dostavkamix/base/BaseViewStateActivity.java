package ru.dostavkamix.denis.dostavkamix.base;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import ru.dostavkamix.denis.dostavkamix.Custom.blurbehind.BlurBehind;


/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

public abstract class BaseViewStateActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpViewStateActivity<V, P> {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        injectDependencies();
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected void injectDependencies() {

    }
}
