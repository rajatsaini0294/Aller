package com.rajat.android.aller.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.Util.Constants;
import com.rajat.android.aller.Util.Utilities;
import com.rajat.android.aller.data.TableColumns;
import com.rajat.android.aller.model.LocationPOJO;
import com.rajat.android.aller.ui.activities.LocationDetailsActivity;
import com.rajat.android.aller.ui.widget.BezelImageView;
import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * Created by rajat on 3/8/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    Cursor cursor = null;
    int fragment;
    public GridAdapter(Context context) {
        this.context = context;
    }

    public GridAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }
    public GridAdapter(Context context, int fragment) {
        this.context = context;
        this.fragment = fragment;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_layout, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(cursor!=null) {
            cursor.moveToPosition(position);

            GridViewHolder gridViewHolder = (GridViewHolder) holder;
            gridViewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_NAME)));

            Float ratingValue = Utilities.parseRating(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_RATING)));
            gridViewHolder.ratingBar.setRating(ratingValue);
            Log.d("............", ratingValue + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cursor.moveToPosition(position);
                    Parcelable parcelable = exportToParcel(position);
                    if (parcelable != null) {
                        Intent intent = new Intent(context, LocationDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.PARCEL_KEY, parcelable);
                        bundle.putInt(Constants.FRAGMENT_KEY, fragment);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });

            File file = Utilities.getPathToImage(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_ID)));
            if (file != null && file.exists()) {
                Picasso.with(context).load(file).placeholder(R.drawable.ic_image_placeholder).into(gridViewHolder.imageView);
            }
        }
    }

    private Parcelable exportToParcel(int position) {
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToPosition(position);

            LocationPOJO locationPOJO = new LocationPOJO();
            locationPOJO.set_id(cursor.getString(cursor.getColumnIndex(TableColumns._ID)));
            locationPOJO.setPlace_id(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_ID)));
            locationPOJO.setPlace_name(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_NAME)));
            locationPOJO.setPlace_address(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_ADDRESS)));
            locationPOJO.setPlace_phone(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_PHONE)));
            locationPOJO.setPlace_website(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_WEBSITE)));
            locationPOJO.setPlace_latitude(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_LATITUDE)));
            locationPOJO.setPlace_longitude(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_LONGITUDE)));
            locationPOJO.setPlace_rating(cursor.getString(cursor.getColumnIndex(TableColumns.PLACE_RATING)));

            return locationPOJO;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if(cursor!=null) {
            return cursor.getCount();
        } return 0;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CardView cardView;
        private RatingBar ratingBar;
        private BezelImageView imageView;

        public GridViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.place_name);
            cardView = (CardView) view.findViewById(R.id.card_view);
            ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
            imageView = (BezelImageView) view.findViewById(R.id.place_image);
        }
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
    }
}
