package com.portfolio.portfoliooutput;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {


    //ListView에 출력하기 위한 데이터를 저장하는 것이 ArrayList
    private ArrayList<ADInsertData> mList = null;
    //Adapter는 ListView 와 ArrayList를 연결시켜서 출력
    //ListView는 실제 보여지는 화면
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<ADInsertData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView adex;
        protected TextView startdate;
        protected TextView enddate;


        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.textView_list_title);
            this.adex = (TextView) view.findViewById(R.id.textView_list_adex);
            this.startdate = (TextView) view.findViewById(R.id.textView_list_startdate);
            this.enddate = (TextView) view.findViewById(R.id.textView_list_enddate);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.title.setText(mList.get(position).getMember_title());
        viewholder.adex.setText(mList.get(position).getMember_adex());
        viewholder.startdate.setText(mList.get(position).getMember_startdate());
        viewholder.enddate.setText(mList.get(position).getMember_enddate());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}