package ru.dostavkamix.denis.dostavkamix.content.profile.edit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.model.account.Account.Address;
import ru.dostavkamix.denis.dostavkamix.R;

import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.focus;


/**
 * Created by den on 16.08.16.
 * <p>
 * Special for Android School GDG
 */

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private static final String TAG = "AddressesAdapter";
    
    private List<Address> addresses;

    public AddressesAdapter(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(position > 0) {
            holder.remove_lay.setVisibility(View.VISIBLE);
        }

        holder.street.setText(addresses.get(position).getStreet());
        holder.number.setText(addresses.get(position).getNumber());
        holder.apartment.setText(addresses.get(position).getApartment());
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.remove_lay) View remove_lay;
        @OnClick(R.id.remove) void remove_click() { if(getAdapterPosition() > 0) removeAt(getAdapterPosition());}

        @OnClick(R.id.street_frame) void street_frame_click() { focus(street);}
        @OnClick(R.id.number_frame) void number_frame_click() { focus(number);}
        @OnClick(R.id.apartment_frame) void apartment_frame_click() { focus(apartment);}

        @BindView(R.id.street) EditText street;
        @BindView(R.id.number) EditText number;
        @BindView(R.id.apartment) EditText apartment;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public Address getAddress() {
            return new Address(street.getText().toString(), number.getText().toString(), apartment.getText().toString());
        }
    }

    private void removeAt(int position) {
        addresses.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addresses.size());
    }

    public void updateAddresses(List<Address> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }
}
