package ru.dostavkamix.denis.dostavkamix.content.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) NetworkImageView img;
        @BindView(R.id.progress) ProgressBar progress;

        @BindView(R.id.name) TextView name;
        @BindView(R.id.description) TextView description;

        @BindView(R.id.price) TextView price;
        @BindView(R.id.check) ImageView check;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
