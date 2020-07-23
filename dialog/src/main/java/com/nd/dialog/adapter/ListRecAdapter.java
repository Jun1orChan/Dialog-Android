package com.nd.dialog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.nd.dialog.R;
import com.nd.dialog.listener.OnItemClickListener;

/**
 * @author cwj
 */
public class ListRecAdapter extends RecyclerView.Adapter<ListRecAdapter.ListItemViewHolder> {


    private CharSequence[] mItemArray;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setItemList(CharSequence[] itemArray) {
        this.mItemArray = itemArray;
        notifyDataSetChanged();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialoglib_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ListItemViewHolder holder, final int position) {
        holder.tvItemName.setText(mItemArray[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemArray == null ? 0 : mItemArray.length;
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);
        }
    }
}
