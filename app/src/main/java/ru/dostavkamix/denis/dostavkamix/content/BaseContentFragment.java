package ru.dostavkamix.denis.dostavkamix.content;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import ru.dostavkamix.denis.dostavkamix.base.BaseViewStateFragment;

/**
 * Created by Денис on 06.09.2016.
 *
 * @author Denis Tkachenko
 */

public abstract class BaseContentFragment<V extends MvpView, P extends MvpPresenter<V>> extends BaseViewStateFragment<V, P> {
}
