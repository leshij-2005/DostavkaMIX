package ru.dostavkamix.denis.dostavkamix.content.profile.orders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Constants;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Item;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import ru.dostavkamix.denis.dostavkamix.utils.ViewUtils;

/**
 * Created by denis on 26.09.16.
 *
 * @author Denis Tkachenko
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> orders = new ArrayList<>();

    @Inject Context context;
    @Inject ImageLoader imageLoader;

    OrdersAdapter(List<Order> orders) {
        AppController.getComponent().inject(this);
        this.orders = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orders, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.orders.setLayoutManager(new LinearLayoutManager(holder.orders.getContext()));
        holder.orders.setAdapter(new OrderAdapter(order.getItems()));
        holder.created_at.setText(order.getCreated_at());
        holder.ball.setText(String.format(context.getString(R.string.value_ball), order.getBall() != null
                ? Integer.valueOf(order.getBall())
                : 0));
        holder.sum.setText(String.format(context.getString(R.string.value_price), order.getSum() != null
                ? Integer.valueOf(order.getSum())
                : 0));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;

        @BindView(R.id.created_at) TextView created_at;
        @BindView(R.id.orders) RecyclerView orders;
        @BindView(R.id.ball) TextView ball;
        @BindView(R.id.sum) TextView sum;

        ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

        private List<Item> orders = new ArrayList<>();


        OrderAdapter(List<Item> orders) {
            this.orders = orders;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Item order = orders.get(position);

            holder.image.setImageUrl(Constants.getBase_url() + order.getImage(), imageLoader);
            holder.caption.setText(order.getCaption());
            holder.sumTotal.setText(ViewUtils.addRuble(String.valueOf(order.getPrice())));
            holder.weight.setText(String.format(context.getString(R.string.value_weight), Integer.valueOf(order.getWeight())));
            holder.count.setText(String.valueOf(order.getCount()));
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            Unbinder unbinder;

            @BindView(R.id.image) NetworkImageView image;
            @BindView(R.id.caption) TextView caption;
            @BindView(R.id.sum) TextView sumTotal;
            @BindView(R.id.weight) TextView weight;
            @BindView(R.id.count) TextView count;

            ViewHolder(View itemView) {
                super(itemView);
                unbinder = ButterKnife.bind(this, itemView);
            }
        }
    }
}
