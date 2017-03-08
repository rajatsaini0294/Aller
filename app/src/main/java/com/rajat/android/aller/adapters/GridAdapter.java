package com.rajat.android.aller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajat.android.aller.R;

/**
 * Created by rajat on 3/8/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_layout, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GridViewHolder gridViewHolder = (GridViewHolder) holder;
        gridViewHolder.textView.setText("GridItem");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public GridViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
