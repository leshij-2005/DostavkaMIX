package ru.dostavkamix.denis.dostavkamix.content.profile.orders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseLceFragment;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public class OrdersFragment extends BaseLceFragment<RecyclerView, List<Order>, OrdersView, OrdersPresenter> implements OrdersView {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_profile_orders;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        e.printStackTrace();
        return null;
    }

    @NonNull
    @Override
    public OrdersPresenter createPresenter() {
        return new OrdersPresenter();
    }

    @Override
    public void setData(List<Order> data) {
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.setAdapter(new OrdersAdapter(data));
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadOrders();
    }
}
