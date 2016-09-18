package ru.dostavkamix.denis.dostavkamix.content.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import butterknife.BindView;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseLceFragment;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Item;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class ListFragment extends BaseLceFragment<RecyclerView, List<Item>, ListView, ListPresenter> implements ListView {

    @BindView(R.id.search) EditText search;
    @BindView(R.id.list) RecyclerView list;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_content_list;
    }


    @NonNull
    @Override
    public LceViewState<List<Item>, ListView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Item> getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @NonNull
    @Override
    public ListPresenter createPresenter() {
        return new ListPresenter();
    }

    @Override
    public void setData(List<Item> data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
