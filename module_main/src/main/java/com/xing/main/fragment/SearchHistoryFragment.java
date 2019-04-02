package com.xing.main.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.commonbase.widget.LinearItemDecoration;
import com.xing.commonbase.widget.flow.FlowAdapter;
import com.xing.commonbase.widget.flow.FlowLayout;
import com.xing.main.R;
import com.xing.main.adapter.SearchHistoryAdapter;
import com.xing.main.bean.SearchHotKey;
import com.xing.main.bean.db.SearchHistory;
import com.xing.main.contract.SearchHistoryContract;
import com.xing.main.presenter.SearchHistoryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史 Fragment
 */
public class SearchHistoryFragment extends BaseMVPFragment<SearchHistoryPresenter>
        implements SearchHistoryContract.View {

    private RecyclerView recyclerView;
    private List<SearchHistory> searchHistoryList = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;
    private FlowLayout flowLayout;
    private View searchHeaderView;
    private ImageView deleteAllSearchImgView;

    public SearchHistoryFragment() {
    }

    public static SearchHistoryFragment newInstance() {
        return new SearchHistoryFragment();
    }

    @Override
    protected SearchHistoryPresenter createPresenter() {
        return new SearchHistoryPresenter(mContext);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initView(View rootView) {
        searchHeaderView = LayoutInflater.from(mContext).inflate(R.layout.layout_search_history_header, null);
        deleteAllSearchImgView = searchHeaderView.findViewById(R.id.iv_search_history_del_all);
        flowLayout = searchHeaderView.findViewById(R.id.fl_search_history);
        recyclerView = rootView.findViewById(R.id.rv_search_history);

    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.8f)    // 0.5dp
                .color(Color.parseColor("#aacccccc"))  // color 的 int 值，不是 R.color 中的值
                .jumpPositions(new int[]{0});
        recyclerView.addItemDecoration(itemDecoration);
        presenter.getSearchHistory();
        presenter.getSearchHotKey();
        deleteAllSearchImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除所有搜索历史
                presenter.deleteAllHistory();
            }
        });
    }

    @Override
    public void onSearchHotKey(final List<SearchHotKey> searchHotKeys) {
        if (searchHotKeys == null || searchHotKeys.size() == 0) {
            return;
        }
        flowLayout.setAdapter(new FlowAdapter() {
            @Override
            public int getCount() {
                return searchHotKeys.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView textView = new TextView(mContext);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                textView.setBackgroundResource(R.drawable.shape_search_history_bg);
                textView.setText(searchHotKeys.get(position).getName());
                textView.setTextColor(getResources().getColor(R.color.black_333));
                return textView;
            }
        });
    }

    @Override
    public void onSearchHistory(final List<SearchHistory> searchHistories) {
        if (searchHistories == null) {
            return;
        }
        searchHistoryList = searchHistories;
        if (searchHistoryAdapter == null) {
            searchHistoryAdapter = new SearchHistoryAdapter(
                    R.layout.item_search_history_content, searchHistoryList);
            searchHistoryAdapter.addHeaderView(searchHeaderView);
            searchHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SearchHistory searchHistory = searchHistoryList.get(position);
                    String keyword = searchHistory.getKeyword();
                    if (onDataListener != null) {
                        onDataListener.onData(keyword);
                    }
                }
            });
            searchHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    searchHistoryList.remove(position);
                    searchHistoryAdapter.notifyDataSetChanged();
                }
            });
            recyclerView.setAdapter(searchHistoryAdapter);
        } else {
            searchHistoryAdapter.setNewData(searchHistoryList);
        }
    }

    @Override
    public void onDeleteAllHistory() {
        searchHistoryList.clear();
        if (searchHistoryAdapter != null) {
            searchHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnDataListener) {
            onDataListener = (OnDataListener) activity;
        }
    }

    public interface OnDataListener {
        void onData(String content);
    }

    private OnDataListener onDataListener;

}
