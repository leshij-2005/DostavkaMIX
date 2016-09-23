package ru.dostavkamix.denis.dostavkamix.buy;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseMvpActivity;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Buyer;

import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.fade;
import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.focus;
import static ru.dostavkamix.denis.dostavkamix.utils.ViewUtils.setTextColorAnim;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class BuyActivity extends BaseMvpActivity<BuyView, BuyPresenter> implements BuyView {

    private static final String TAG = "BuyActivity";
    BuyDialog dialog = new BuyDialog();

    @BindView(R.id.delivery) TextView delivery;
    @BindView(R.id.pickup) TextView pickup;

    @BindView(R.id.scrollView) NestedScrollView scrollView;

    @OnClick(R.id.name_frame) void click_name_frame() { focus(name); }
    @OnClick(R.id.phone_frame) void click_phone_frame() { focus(phone); }
    @OnClick(R.id.email_frame) void click_email_frame() { focus(email); }
    @BindView(R.id.name) EditText name;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.email) EditText email;

    @BindView(R.id.label_address) View label_address;
    @BindView(R.id.street_frame) View street_frame;
    @BindView(R.id.number_frame) View number_frame;
    @BindView(R.id.apartment_frame) View apartment_frame;
    @BindView(R.id.street) AutoCompleteTextView street;
    @BindView(R.id.number) EditText number;
    @BindView(R.id.apartment) EditText apartment;

    @OnClick(R.id.now_frame) void click_now_frame() { }
    @OnClick(R.id.at_time_frame) void click_at_time_frame() { }
    @BindView(R.id.now) View now;
    @BindView(R.id.at_time) View at_time;

    @BindView(R.id.cash) View cash;
    @BindView(R.id.cash_count_frame) View cash_count_frame;
    @BindView(R.id.cash_count) EditText cash_count;
    @BindView(R.id.card) View card;
    @BindView(R.id.points) View points;
    @BindView(R.id.points_count_frame) View points_count_frame;
    @BindView(R.id.points_seekbar_frame) View points_seekbar_frame;
    @BindView(R.id.points_count) EditText points_count;
    @BindView(R.id.points_seekbar) SeekBar points_seekbar;

    @OnClick(R.id.street_frame) void click_street_frame() { focus(street); }
    @OnClick(R.id.number_frame) void click_number_frame() { focus(number); }
    @OnClick(R.id.apartment_frame) void click_apartment_frame() { focus(apartment); }
    @OnClick(R.id.cash_frame) void click_cash_frame() {
        fade(cash, true);
        fade(card, false);
        fade(points, false);

        points_count_frame.setVisibility(View.GONE);
        points_seekbar_frame.setVisibility(View.GONE);
        cash_count_frame.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.card_frame) void click_card_frame() {
        fade(cash, false);
        fade(card, true);
        fade(points, false);

        points_count_frame.setVisibility(View.GONE);
        points_seekbar_frame.setVisibility(View.GONE);
        cash_count_frame.setVisibility(View.GONE);
    }
    @OnClick(R.id.points_frame) void click_points_frame() {
        fade(cash, false);
        fade(card, false);
        fade(points, true);

        points_count_frame.setVisibility(View.VISIBLE);
        points_seekbar_frame.setVisibility(View.VISIBLE);
        cash_count_frame.setVisibility(View.GONE);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
    @OnClick(R.id.cash_count_frame) void click_cash_count_frame() { focus(cash_count); }
    @OnClick(R.id.points_count_frame) void click_points_count_frame() { focus(points_count); }

    @OnClick({ R.id.delivery, R.id.pickup }) void select_method(TextView selected) {
        if(selected.getTag() != null) return;
        selected.setTag("selected");
        ((TransitionDrawable) selected.getBackground()).startTransition(100);
        setTextColorAnim(selected, getResources().getColor(android.R.color.black));

        TextView unselected = selected.getId() == R.id.delivery ? pickup : delivery;
        if(unselected.getTag() != null) ((TransitionDrawable) unselected.getBackground()).reverseTransition(100);
        setTextColorAnim(unselected, getResources().getColor(android.R.color.white));
        unselected.setTag(null);

        label_address.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
        street_frame.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
        number_frame.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
        apartment_frame.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.buy_frame) void click_buy() {
        presenter.buying(buildBuyer());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        startup();
    }

    @NonNull
    @Override
    public BuyPresenter createPresenter() {
        return new BuyPresenter();
    }

    @Override
    public void showAccount(Account account) {
        name.setText(account.getName());
        phone.setText(account.getPhone());
        email.setText(account.getEmail());

        street.setAdapter(new AddressAutoCompleteAdapter(account.getAddresses(), this));
        street.setOnItemClickListener((parent, view, position, id) -> {
            Account.Address address = (Account.Address) parent.getItemAtPosition(position);

            street.setText(address.getStreet());
            number.setText(address.getNumber());
            apartment.setText(address.getApartment());
        });
    }

    @Override
    public void showSuccess() {
        dialog.showSuccess();
        new Handler().postDelayed(() -> dialog.dismiss(), 2000);
    }

    @Override
    public void showBuying() {
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showError() {
        dialog.showError();
        new Handler().postDelayed(() -> dialog.dismiss(), 2000);
    }

    private Buyer buildBuyer() {
        Buyer buyer = new Buyer();

        buyer.setName(name.getText().toString());
        buyer.setPhone(phone.getText().toString());
        buyer.setStreet(street.getText().toString());
        buyer.setHouse(number.getText().toString());
        buyer.setApartment(apartment.getText().toString());
        buyer.setType(label_address.getVisibility() == View.VISIBLE
                ? "доставка"
                : "самовывоз");
        buyer.setMoney(cash.getVisibility() == View.VISIBLE
                ? "наличка, сдача с: " + cash_count.getText().toString()
                : card.getVisibility() == View.VISIBLE
                ? "безнал"
                : "баллами, их аж: " + points_count.getText().toString());
        //buyer.setItems(getIntent().getExtras().getParcelableArrayList(""));

        return buyer;
    }

    private void startup() {
        select_method(delivery);
        click_cash_frame();
        presenter.loadAccount();
    }
}
