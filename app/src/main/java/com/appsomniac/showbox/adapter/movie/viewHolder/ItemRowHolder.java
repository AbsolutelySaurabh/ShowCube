package com.appsomniac.showbox.adapter.movie.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.appsomniac.showbox.R;

public class ItemRowHolder extends RecyclerView.ViewHolder {

    public TextView itemTitle;
    public RecyclerView recycler_view_list;
    public TextView textMore;

    public ItemRowHolder(View view) {
        super(view);

        this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
        this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        this.textMore= (TextView) view.findViewById(R.id.more_text);

    }
}