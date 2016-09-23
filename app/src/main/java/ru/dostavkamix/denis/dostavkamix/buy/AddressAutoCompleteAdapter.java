package ru.dostavkamix.denis.dostavkamix.buy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;

/**
 * Created by den on 23.09.16.
 */

public class AddressAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private List<Account.Address> addresses;
    private List<Account.Address> result;
    private Context context;

    @BindView(R.id.text1) TextView street;
    @BindView(R.id.text2) TextView number;

    public AddressAutoCompleteAdapter(List<Account.Address> addresses, Context context) {
        this.addresses = addresses;
        this.result = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Account.Address getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        ButterKnife.bind(this, convertView);

        Account.Address address = result.get(position);
        street.setText(address.getStreet());
        number.setText(address.getNumber());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    List<Account.Address> addresses = findAddresses(constraint.toString());

                    filterResults.values = addresses;
                    filterResults.count = addresses.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    result = (List<Account.Address>) results.values;
                    notifyDataSetChanged();
                } else notifyDataSetInvalidated();
            }
        };
        return filter;
    }

    private List<Account.Address> findAddresses(String street) {
        List<Account.Address> addresses = new ArrayList<>();
        for (Account.Address address: this.addresses) {
            if (address.getStreet().toLowerCase().contains(street.toLowerCase())) addresses.add(address);
        }

        return addresses;
    }
}
