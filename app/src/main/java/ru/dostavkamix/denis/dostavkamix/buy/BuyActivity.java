package ru.dostavkamix.denis.dostavkamix.buy;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import ru.dostavkamix.denis.dostavkamix.base.BaseViewStateActivity;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class BuyActivity extends BaseViewStateActivity<BuyView, BuyPresenter> implements BuyView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public ViewState<BuyView> createViewState() {
        return null;
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @NonNull
    @Override
    public BuyPresenter createPresenter() {
        return null;
    }
}
