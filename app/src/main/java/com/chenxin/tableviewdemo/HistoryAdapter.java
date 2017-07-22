package com.chenxin.tableviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenxin.tableview.TableAdapter;

import java.util.List;

/**
 * Created by chenxin on 2017/5/17.
 * O(∩_∩)O~
 */

public class HistoryAdapter extends TableAdapter<HistoryAdapter.ViewHolder> {

    private List<HistoryData> list;
    private LayoutInflater inflater;


    public HistoryAdapter(List<HistoryData> list, Context context) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_history_content, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getData());
        holder.id.setText(list.get(position).getId());
        holder.recordId.setText(list.get(position).getRecordId());
        holder.result.setText(list.get(position).getResult());
        holder.time.setText(list.get(position).getResult());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView id;
        TextView recordId;
        TextView result;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            id = (TextView) itemView.findViewById(R.id.id);
            recordId = (TextView) itemView.findViewById(R.id.recordId);
            result = (TextView) itemView.findViewById(R.id.result);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
