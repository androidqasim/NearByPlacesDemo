package com.waleedsarwar.nearbyplaces.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

/**
 * @author Waleed Sarwar
 * @since February 23, 2016 03:44 PM
 */
public class NearByPlacesAdapter extends RecyclerView.Adapter<NearByPlacesAdapter.ViewHolder> {
    private Context context;
    private List<NearbyPlace> mNearbyPlaces;

    public NearByPlacesAdapter(Context context, List<NearbyPlace> mNearbyPlaces) {
        this.context = context;
        this.mNearbyPlaces = mNearbyPlaces;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nearby_place, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NearbyPlace nearbyPlace = getItem(position);
        holder.mNameTextView.setText(nearbyPlace.getName());
        holder.mAddressTextView.setText(nearbyPlace.getAddress());
        holder.mLikleHoodTextView.setText(String.valueOf(nearbyPlace.getLikelihood()));
    }

    @Override
    public int getItemCount() {
        return mNearbyPlaces.size();
    }

    public NearbyPlace getItem(int position) {
        return mNearbyPlaces.get(position);
    }

    public void addPlaces(List<NearbyPlace> nearbyPlaces) {
        mNearbyPlaces.clear();
        mNearbyPlaces.addAll(nearbyPlaces);
        notifyDataSetChanged();
    }

    public void removeNearByPlaces() {
        mNearbyPlaces.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mLikleHoodTextView;
        public TextView mAddressTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
            mLikleHoodTextView = (TextView) itemView.findViewById(R.id.likely_hood_text_view);
            mAddressTextView = (TextView) itemView.findViewById(R.id.address_text);
        }
    }
}
