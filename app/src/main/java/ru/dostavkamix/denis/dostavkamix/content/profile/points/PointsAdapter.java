package ru.dostavkamix.denis.dostavkamix.content.profile.points;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Transaction;

/**
 * Created by denis on 28.09.16.
 *
 * @author Denis Tkachenko
 */

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {

    @Inject
    Context context;

    private List<Transaction> data = new ArrayList<>();

    public PointsAdapter(List<Transaction> data) {
        AppController.getComponent().inject(this);
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction transaction = data.get(position);

        holder.point.setText(String.format(context.getString(R.string.value_ball), transaction.getPoint()));
        holder.type.setText(context.getString(transaction.getType() == Transaction.Type.POINT
                ? R.string.transaction_point
                : R.string.transaction_shot));
        holder.type.setTextColor(context.getResources().getColor(transaction.getType() == Transaction.Type.POINT
                ? R.color.green_point
                : R.color.red_point));
        holder.created_at.setText(transaction.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;

        @BindView(R.id.point) TextView point;
        @BindView(R.id.type) TextView type;
        @BindView(R.id.created_at) TextView created_at;

        public ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
