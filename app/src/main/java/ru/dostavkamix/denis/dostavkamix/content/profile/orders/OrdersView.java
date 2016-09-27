package ru.dostavkamix.denis.dostavkamix.content.profile.orders;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;

/**
 * Created by den on 13.09.16.
 *
 * @author Denis Tkachenko
 */

public interface OrdersView extends MvpView, MvpLceView<List<Order>> {
}
