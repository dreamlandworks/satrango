package com.satrangolimitless.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.satrangolimitless.R;
import com.satrangolimitless.model.Search_suggestion_vendor_register;

import java.util.ArrayList;
import java.util.List;

public class AutoCompletevendor_register_adapter extends ArrayAdapter<Search_suggestion_vendor_register> {
    Context context;
    private final List<Search_suggestion_vendor_register> countryListFull;
    private final Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Search_suggestion_vendor_register> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Search_suggestion_vendor_register item : countryListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Search_suggestion_vendor_register) resultValue).getName();
        }
    };

    public AutoCompletevendor_register_adapter(@NonNull Context context, @NonNull List<Search_suggestion_vendor_register> countryList) {
        super(context, 0, countryList);
        this.context = context;
        countryListFull = new ArrayList<>(countryList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.country_autocomplete_row, parent, false
            );
        }
        TextView mMedicine_name = convertView.findViewById(R.id.mMedicine_name);

        Search_suggestion_vendor_register countryItem = getItem(position);
        if (countryItem != null) {
            mMedicine_name.setText(countryItem.getName());


        }
        return convertView;
    }
}
