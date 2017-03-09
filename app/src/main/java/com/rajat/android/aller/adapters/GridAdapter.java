package com.rajat.android.aller.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajat.android.aller.R;
import com.rajat.android.aller.data.TableColumns;

/**
 * Created by rajat on 3/8/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    Cursor cursor;

    public GridAdapter(Context context) {
        this.context = context;
    }

    public GridAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_layout, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        cursor.moveToPosition(position);
        Log.d("............", position+" ..."+cursor.getString(cursor.getColumnIndex(TableColumns._ID)));
        GridViewHolder gridViewHolder = (GridViewHolder) holder;
        gridViewHolder.textView.setText(cursor.getString(cursor.getColumnIndex(TableColumns._ID)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public GridViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
