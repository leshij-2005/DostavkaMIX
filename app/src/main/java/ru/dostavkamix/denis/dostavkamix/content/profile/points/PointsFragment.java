package ru.dostavkamix.denis.dostavkamix.content.profile.points;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseLceFragment;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Transaction;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class PointsFragment extends BaseLceFragment<RecyclerView, List<Transaction>, PointsView, PointsPresenter> implements PointsView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profile_points;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        e.printStackTrace();
        return null;
    }

    @NonNull
    @Override
    public PointsPresenter createPresenter() {
        return new PointsPresenter();
    }

    @Override
    public void setData(List<Transaction> data) {
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.setAdapter(new PointsAdapter(data));
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadTransactions();
    }


}
