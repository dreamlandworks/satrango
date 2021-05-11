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
import com.satrangolimitless.model.MedicinALLList;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteCountryAdapter extends ArrayAdapter<MedicinALLList> {
    Context context;
    private final List<MedicinALLList> countryListFull;
    private final Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<MedicinALLList> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(countryListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (MedicinALLList item : countryListFull) {
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
            return ((MedicinALLList) resultValue).getName();
        }
    };

    public AutoCompleteCountryAdapter(@NonNull Context context, @NonNull List<MedicinALLList> countryList) {
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

        MedicinALLList countryItem = getItem(position);
        if (countryItem != null) {
            mMedicine_name.setText(countryItem.getName());


        }
        return convertView;
    }
}
