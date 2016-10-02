package ru.dostavkamix.denis.dostavkamix.buy;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Objects.Dish;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.base.BaseMvpActivity;
import ru.dostavkamix.denis.dostavkamix.model.account.Account;
import ru.dostavkamix.denis.dostavkamix.model.content.pojo.Item;
import ru.dostavkamix.denis.dostavkamix.model.order.pojo.Order;
import ru.dostavkamix.denis.dostavkamix.utils.Utils;
import ru.dostavkamix.denis.dostavkamix.utils.ViewUtils;

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
    private int sum = 0;
    BuyDialog dialog = new BuyDialog();

    @BindView(R.id.delivery) TextView delivery;
    @BindView(R.id.pickup) TextView pickup;

    @BindView(R.id.scrollView) ScrollView scrollView;

    @OnClick(R.id.name_frame) void click_name_frame() {
        focus(name);
    }
    @OnClick(R.id.phone_frame) void click_phone_frame() {
        focus(phone);
    }
    @OnClick(R.id.email_frame) void click_email_frame() {
        focus(email);
    }

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

    @OnClick(R.id.now_frame) void click_now_frame() {
        fade(now, true);
        fade(at_time, false);
    }

    @OnClick(R.id.at_time_frame) void click_at_time_frame() {
        if(at_time.getVisibility() == View.GONE) {
            if(ViewUtils.isEmpty(at_time))
                dateDialog.show(getFragmentManager(), "dateDialog");
        } else dateDialog.show(getFragmentManager(), "dateDialog");

        fade(at_time, true);
        fade(now, false);
    }

    @BindView(R.id.now) View now;
    @BindView(R.id.at_time) TextView at_time;

    @BindView(R.id.cash) View cash;
    @BindView(R.id.cash_count_frame) View cash_count_frame;
    @BindView(R.id.cash_count) EditText cash_count;
    @BindView(R.id.card) View card;
    @BindView(R.id.points) View points;
    @BindView(R.id.points_count_frame) View points_count_frame;
    @BindView(R.id.points_seekbar_frame) View points_seekbar_frame;
    @BindView(R.id.points_count) EditText points_count;
    @BindView(R.id.points_min) TextView points_min;
    @BindView(R.id.points_max) TextView points_max;
    @BindView(R.id.points_seekbar) SeekBar points_seekbar;

    @BindView(R.id.buy) TextView buy;

    @OnClick(R.id.street_frame) void click_street_frame() {
        focus(street);
    }
    @OnClick(R.id.number_frame) void click_number_frame() {
        focus(number);
    }
    @OnClick(R.id.apartment_frame) void click_apartment_frame() {
        focus(apartment);
    }
    @OnClick(R.id.cash_frame) void click_cash_frame() {
        fade(cash, true);
        fade(card, false);
        cash_count_frame.setVisibility(cash.getVisibility());
    }

    @OnClick(R.id.card_frame) void click_card_frame() {
        fade(cash, false);
        fade(card, true);
        cash_count_frame.setVisibility(cash.getVisibility());
    }
    @OnClick(R.id.points_frame) void click_points_frame() {
        fade(points, points.getVisibility() != View.VISIBLE);
        points_count_frame.setVisibility(points.getVisibility());
        points_seekbar_frame.setVisibility(points.getVisibility());

        scrollView.fullScroll(View.FOCUS_DOWN);
    }
    @OnClick(R.id.cash_count_frame)void click_cash_count_frame() {
        focus(cash_count);
    }
    @OnClick(R.id.points_count_frame) void click_points_count_frame() {
        focus(points_count);
    }
    @OnClick({R.id.delivery, R.id.pickup}) void select_method(TextView selected) {
        if (selected.getTag() != null) return;
        selected.setTag("selected");
        ((TransitionDrawable) selected.getBackground()).startTransition(100);
        setTextColorAnim(selected, getResources().getColor(android.R.color.black));

        TextView unselected = selected.getId() == R.id.delivery ? pickup : delivery;
        if (unselected.getTag() != null)
            ((TransitionDrawable) unselected.getBackground()).reverseTransition(100);
        setTextColorAnim(unselected, getResources().getColor(android.R.color.white));
        unselected.setTag(null);

        label_address.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
        street_frame.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
        number_frame.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);
        apartment_frame.setVisibility(selected.getId() == R.id.delivery ? View.VISIBLE : View.GONE);

        if(selected.getId() != delivery.getId()) {
            if (AppController.getInstance().getSale() != 0) {
                if (AppController.getInstance().getSale() == 5) {
                    sum = (int) (AppController.getInstance().getWithoutSale() * 0.85);
                } else if (AppController.getInstance().getSale() == 10) {
                    sum = (int) (AppController.getInstance().getWithoutSale() * 0.85);
                } else if (AppController.getInstance().getSale() == 15) {
                    sum = (int) (AppController.getInstance().getWithoutSale() * 0.8);
                }
            } else
                sum = (int) ((AppController.getInstance().getWithoutSale()) * 0.90);
        } else {
            if (AppController.getInstance().getSale() == 0) {
                sum = AppController.getInstance().getWithoutSale() + 150;
            } else sum = AppController.getInstance().getWithSale();
        }

        buy.setText(getString(R.string.value_buy, sum));
    }
    @OnClick(R.id.buy_frame) void click_buy() {
        presenter.buying(buildBuyer());
    }

    TimePickerDialog timeDialog = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> {
                at_time.setText(at_time.getText().toString() + " " + Utils.formatTime(String.valueOf(hourOfDay) + ":" + String.valueOf(minute),
                        "h:m", "HH:MM"));
                if (!presenter.validOrderTime(hourOfDay, minute))
                    showLightError(R.string.msg_invalid_time);

            },
            Utils.getCurrentHourOfDay(),
            Utils.getCurrentMinute(),
            true);

    DatePickerDialog dateDialog = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
                at_time.setText(Utils.formatTime(String.valueOf(monthOfYear) + "." + String.valueOf(dayOfMonth), "M.d", "dd MMMM"));
                if (Utils.isCurrentDate(year, monthOfYear, dayOfMonth))
                    timeDialog.setMinTime(Utils.getCurrentHourOfDay(), Utils.getCurrentMinute(), 0);
                else timeDialog.setMinTime(0, 0, 0);
                timeDialog.show(getFragmentManager(), "timeDialog");
            },
            Utils.getCurrentYear(),
            Utils.getCurrentMonth(),
            Utils.getCurrentDayOfMonth());

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
        points_seekbar.setMax(account.getPoints());
        points_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                points_count.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        points_count.setText(String.valueOf(points_seekbar.getProgress()));
        points_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                points_seekbar.setProgress(TextUtils.isEmpty(s) ? 0 : Integer.valueOf(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
    public void showError(Throwable throwable) {
        throwable.printStackTrace();
        dialog.showError(throwable);
        new Handler().postDelayed(() -> dialog.dismiss(), 2000);
    }

    private Order buildBuyer() {
        Order order = new Order();

        order.setName(name.getText().toString());
        order.setPhone(phone.getText().toString());
        order.setStreet(street.getText().toString());
        order.setHouse(number.getText().toString());
        order.setApartment(apartment.getText().toString());
        order.setType(label_address.getVisibility() == View.VISIBLE
                ? "доставка"
                : "самовывоз");
        order.setMoney(cash.getVisibility() == View.VISIBLE
                ? "наличка, сдача с: " + cash_count.getText().toString()
                : card.getVisibility() == View.VISIBLE
                ? "безнал"
                : "баллами, их аж: " + points_count.getText().toString());
        //order.setItems(getIntent().getExtras().getParcelableArrayList(""));
        List<Item> items = new ArrayList<>();
        for (Dish dish :
                AppController.getInstance().getInBag()) {
            items.add(new Item(dish.getNameDish(), dish.getIdDish(), dish.getCountOrder()));
        }
        order.setItems(items);

        return order;
    }

    void showLightError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    void showLightError(@StringRes int id) {
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
    }

    private void startup() {
        select_method(delivery);
        click_cash_frame();
        presenter.loadAccount();

        if (AppController.getInstance().getSale() == 0) {
            sum = AppController.getInstance().getWithoutSale() + 150;
        }

        buy.setText(getString(R.string.value_buy, sum));
    }
}
