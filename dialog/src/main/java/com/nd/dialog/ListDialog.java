package com.nd.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nd.dialog.adapter.ListRecAdapter;
import com.nd.dialog.base.BaseDialogFragment;


import com.nd.dialog.listener.OnItemClickListener;
import com.nd.widget.divider.DividerItemDecoration;

/**
 * @author Administrator
 */
public class ListDialog extends BaseDialogFragment {


    private static final String KEY_SAVE_STATE_ITEM_ARRAY = "save_state_item_array";


    private ListRecAdapter mListRecAdapter;
    private CharSequence[] mItemArray;


    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mItemArray = savedInstanceState.getCharSequenceArray(KEY_SAVE_STATE_ITEM_ARRAY);
        }
        View view = inflater.inflate(R.layout.dialoglib_list, null, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        RecyclerView recList = view.findViewById(R.id.recList);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recList.addItemDecoration(
                new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL,
                        R.drawable.dialoglib_line_gray, false));
        if (mItemArray != null) {
            itemArray(mItemArray);
        }
        recList.setAdapter(getListRecAdapter());
    }

    private ListRecAdapter getListRecAdapter() {
        if (mListRecAdapter == null) {
            mListRecAdapter = new ListRecAdapter();
        }
        return mListRecAdapter;
    }

    public ListDialog itemArray(CharSequence[] itemArray) {
        this.mItemArray = itemArray;
        getListRecAdapter().setItemList(mItemArray);
        return this;
    }

    public ListDialog itemClickListener(OnItemClickListener onItemClickListener) {
        getListRecAdapter().setOnItemClickListener(onItemClickListener);
        return this;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequenceArray(KEY_SAVE_STATE_ITEM_ARRAY, mItemArray);
    }
}
