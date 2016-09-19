package ru.dostavkamix.denis.dostavkamix.buy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseMvpActivity;

import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.focus;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class BuyActivity extends BaseMvpActivity<BuyView, BuyPresenter> implements BuyView {

    @OnClick(R.id.name_frame) void click_name_frame() { focus(name); }
    @OnClick(R.id.phone_frame) void click_phone_frame() { focus(phone); }
    @OnClick(R.id.email_frame) void click_email_frame() { focus(email); }
    @BindView(R.id.name) EditText name;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.email) EditText email;

    @OnClick(R.id.street_frame) void click_street_frame() { focus(street); }
    @OnClick(R.id.number_frame) void click_number_frame() { focus(number); }
    @OnClick(R.id.apartment_frame) void click_apartment_frame() { focus(apartment); }
    @BindView(R.id.street) EditText street;
    @BindView(R.id.number) EditText number;
    @BindView(R.id.apartment) EditText apartment;

    @OnClick(R.id.now_frame) void click_now_frame() { }
    @OnClick(R.id.at_time_frame) void click_at_time_frame() { }
    @BindView(R.id.now) View now;
    @BindView(R.id.at_time) View at_time;

    @OnClick(R.id.cash_frame) void click_cash_frame() { showCashCountForm(); }
    @OnClick(R.id.cash_count_frame) void click_cash_count_frame() { focus(cash_count); }
    @OnClick(R.id.card_frame) void click_card_frame() {  }
    @OnClick(R.id.points_frame) void click_points_frame() { showPointsCountForm(); }
    @OnClick(R.id.points_count_frame) void click_points_count_frame() { focus(points_count); }
    @BindView(R.id.cash) View cash;
    @BindView(R.id.cash_count) EditText cash_count;
    @BindView(R.id.card) View card;
    @BindView(R.id.points) View points;
    @BindView(R.id.points_count) EditText points_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
    }

    @NonNull
    @Override
    public BuyPresenter createPresenter() {
        return new BuyPresenter();
    }

    @Override
    public void showDeliveryForm() {

    }

    @Override
    public void showPickupForm() {

    }

    @Override
    public void showCashCountForm() {

    }

    @Override
    public void showPointsCountForm() {

    }

    @Override
    public void showSelectTimeDialog() {

    }

    @Override
    public void showError() {

    }
}
