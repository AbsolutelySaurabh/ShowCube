package com.appsomniac.movie.tv.stream.showcube.adapter.movie.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsomniac.movie.tv.stream.showcube.R;
import com.appsomniac.movie.tv.stream.showcube.adapter.movie.viewHolder.ItemRowHolder;
import com.appsomniac.movie.tv.stream.showcube.model.section.Movie_SectionDataModel;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<ItemRowHolder> {

    private ArrayList<Movie_SectionDataModel> dataList;
    private Context mContext;
    public static String EXTRA_MESSAGE = "";

    public RecyclerViewDataAdapter(Context context, ArrayList<Movie_SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int position) {

        final String sectionName = dataList.get(position).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(position).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, position);
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
//                    intent.putExtra("position", (position));
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