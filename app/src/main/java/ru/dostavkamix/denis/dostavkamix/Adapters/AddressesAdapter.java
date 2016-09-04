package ru.dostavkamix.denis.dostavkamix.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Objects.Address;
import ru.dostavkamix.denis.dostavkamix.Objects.User;
import ru.dostavkamix.denis.dostavkamix.R;

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
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: remove");
                    removeAt(position);
                }
            });
        }

        holder.street.setText(addresses.get(position).getStreet());
        holder.number.setText(addresses.get(position).getNumber());
        holder.apartment.setText(addresses.get(position).getApartment());
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View remove_lay;
        private View remove;

        private View street_frame;
        private View number_frame;
        private View apartment_frame;

        private EditText street;
        private EditText number;
        private EditText apartment;

        ViewHolder(View itemView) {
            super(itemView);

            remove_lay = itemView.findViewById(R.id.remove_lay);
            remove = itemView.findViewById(R.id.remove);

            street_frame = itemView.findViewById(R.id.street_frame);
            number_frame = itemView.findViewById(R.id.number_frame);
            apartment_frame = itemView.findViewById(R.id.apartment_frame);

            street = (EditText) itemView.findViewById(R.id.street);
            number = (EditText) itemView.findViewById(R.id.number);
            apartment = (EditText) itemView.findViewById(R.id.apartment);

            street_frame.setOnClickListener(this);
            number_frame.setOnClickListener(this);
            apartment_frame.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.street_frame:
                    street.requestFocus();
                    street.setSelection(street.getText().length());
                    break;
                case R.id.number_frame:
                    number.requestFocus();
                    number.setSelection(number.getText().length());
                    break;
                case R.id.apartment_frame:
                    apartment.requestFocus();
                    apartment.setSelection(apartment.getText().length());
                    break;
            }
        }

        public Address getAddress() {
            Address address = new Address();

            address.setStreet(street.getText().toString());
            address.setNumber(number.getText().toString());
            address.setApartment(apartment.getText().toString());

            return address;
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
