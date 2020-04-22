package org.jun1or.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jun1or.dialog.adapter.ListRecAdapter;
import org.jun1or.dialog.base.BaseDialogFragment;
import org.jun1or.dialog.listener.OnItemClickListener;
import org.jun1or.widget.divider.DividerItemDecoration;

public class ListDialog extends BaseDialogFragment {

    private ListRecAdapter mListRecAdapter;
    private CharSequence[] mItemArray;

    @Override
    public View getDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialoglib_list, null, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.recList);
        recList.setHasFixedSize(true);
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recList.addItemDecoration(
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL,
                        R.drawable.dialoglib_line_gray, false));
        recList.setAdapter(getListRecAdapter());
    }

    private ListRecAdapter getListRecAdapter() {
        if (mListRecAdapter == null)
            mListRecAdapter = new ListRecAdapter();
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

}
