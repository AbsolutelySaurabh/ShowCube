package com.appsomniac.showbox.adapter.tv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsomniac.showbox.R;
import com.appsomniac.showbox.activity.more.more.MoreActivity;
import com.appsomniac.showbox.adapter.tv.viewHolder.Tv_ItemRowHolder;
import com.appsomniac.showbox.model.section.Tv_SectionDataModel;

import java.util.ArrayList;

public class Tv_RecyclerViewDataAdapter extends RecyclerView.Adapter<Tv_ItemRowHolder> {

    private ArrayList<Tv_SectionDataModel> dataList;
    private Context mContext;
    public static String EXTRA_MESSAGE = "";

    public Tv_RecyclerViewDataAdapter(Context context, ArrayList<Tv_SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public Tv_ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        Tv_ItemRowHolder mh = new Tv_ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(Tv_ItemRowHolder itemRowHolder, final int position) {

        final String sectionName = dataList.get(position).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(position).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        Tv_SectionListDataAdapter itemListDataAdapter = new Tv_SectionListDataAdapter(mContext, singleSectionItems, position);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);

//        itemRowHolder.textMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext, MoreActivity.class);
//
//                if(sectionName.equals("Top Rated")){
//                    intent.putExtra(EXTRA_MESSAGE, "topRated");
//                    intent.putExtra("position", position);
//
//                }else
//                    if(sectionName.equals("Upcoming")){
//                        intent.putExtra(EXTRA_MESSAGE, "upcoming");
//                        intent.putExtra("position", (position));
//                    }else
//                        if(sectionName.equals("Now Playing")){
//                            intent.putExtra(EXTRA_MESSAGE, "nowPlaying");
//                            intent.putExtra("position", (position));
//
//                        }else
//                            if(sectionName.equals("Popular")){
//                                intent.putExtra(EXTRA_MESSAGE, "popular");
//                                intent.putExtra("position", (position));
//
//                            }
//
//                mContext.startActivity(intent);
//
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }
}