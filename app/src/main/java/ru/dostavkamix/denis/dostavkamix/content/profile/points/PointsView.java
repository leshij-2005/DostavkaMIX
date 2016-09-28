package ru.dostavkamix.denis.dostavkamix.content.profile.points;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.dostavkamix.denis.dostavkamix.model.account.api.pojo.Transaction;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public interface PointsView extends MvpView, MvpLceView<List<Transaction>> {
}
