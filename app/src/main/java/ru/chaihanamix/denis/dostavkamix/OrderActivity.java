package ru.chaihanamix.denis.dostavkamix;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.drakeet.materialdialog.MaterialDialog;
import ru.chaihanamix.denis.dostavkamix.CustomView.TextViewPlus;
import ru.chaihanamix.denis.dostavkamix.Fragments.BuyDialog;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnDismissListener {

    //Dialogs
    MaterialDialog invalidDialog;
    MaterialDialog errorDialog;
    DialogFragment buyDialog;
    ProgressDialog progressDialog;

    // Toolbar
    private ImageView arrow_down;
    private ImageView mix_logo;
    
    // Select delivery method
    private Button select_left;
    private Button select_right;
    private Button OnSelect;
    
    // Person
    private EditText order_name;
    private EditText order_phone;
    private EditText order_email;

    // Address
    private RelativeLayout row_address;
        private RelativeLayout view_street;
            private EditText order_street;
        private RelativeLayout view_house;
            private EditText order_house;
        private RelativeLayout view_apartament;
            private EditText order_apartament;

    // Time cooking
    private RelativeLayout view_now;
        private ImageView order_now;
    private RelativeLayout view_time;
        private TextView order_time;
    
    // Billing
    private RelativeLayout billing_wallet;
        private ImageView check_wallet;
        private EditText order_renting;
    private RelativeLayout billing_card;
        private ImageView check_card;
    private RelativeLayout view_renting;

    //Button order
    RelativeLayout but_to_order;
    TextViewPlus text_but_order;
    
    // Other
    static Calendar now = Calendar.getInstance();
    int hourResult;
    int sum_button = AppController.getInstance().getWithSale();
    String selectedDate = "";


    private void initialise()
    {
        //Root lay
        //root_lay = (RelativeLayout) findViewById(R.id.root_lay);
        //inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //softKeyboard = new SoftKeyboard(root_lay, inputManager);

        buyDialog = new BuyDialog();
        progressDialog = new ProgressDialog(this);
        errorDialog = new MaterialDialog(this);

        // Toolbar
        arrow_down = (ImageView) findViewById(R.id.arrow_down);
        mix_logo = (ImageView) findViewById(R.id.mix_logo);
        // Select delivery method
        select_left = (Button) findViewById(R.id.select_left);
        select_right = (Button) findViewById(R.id.select_right);
        OnSelect = null;
        // Person
        order_name = (EditText) findViewById(R.id.order_name);
        order_phone = (EditText) findViewById(R.id.order_phone);
        order_email = (EditText) findViewById(R.id.order_email);
        // Address
        row_address = (RelativeLayout) findViewById(R.id.row_address);
            view_street = (RelativeLayout) findViewById(R.id.view_street);
                order_street = (EditText) findViewById(R.id.order_street);
            view_house = (RelativeLayout) findViewById(R.id.view_house);
                order_house = (EditText) findViewById(R.id.order_house);
            view_apartament = (RelativeLayout) findViewById(R.id.view_apartament);
                order_apartament = (EditText) findViewById(R.id.order_apartament);
        // Time cooking
        view_now = (RelativeLayout) findViewById(R.id.view_now);
            order_now = (ImageView) findViewById(R.id.order_now);
        view_time = (RelativeLayout) findViewById(R.id.view_time);
            order_time = (TextView) findViewById(R.id.order_time);
        // Billing
        billing_wallet = (RelativeLayout) findViewById(R.id.billing_wallet);
            check_wallet = (ImageView) findViewById(R.id.check_wallet);
            view_renting = (RelativeLayout) findViewById(R.id.view_renting);
            order_renting = (EditText) findViewById(R.id.order_renting);
        billing_card = (RelativeLayout) findViewById(R.id.billing_card);
            check_card = (ImageView) findViewById(R.id.check_card);

        // Button order
        but_to_order = (RelativeLayout) findViewById(R.id.but_to_order);
        text_but_order = (TextViewPlus) findViewById(R.id.text_but_order);


        selectOnButton(select_left);

        if(AppController.getInstance().getSale() == 0)
        {
            sum_button = AppController.getInstance().getBagPrice() + 150;
        }

        text_but_order.setText("Оформить за " + addR(String.valueOf(sum_button)));
        order_time.setHint("Сегодня" + " " + (String.valueOf(now.get(Calendar.HOUR)) + ":" + String.valueOf(now.get(Calendar.MINUTE))));

        but_to_order.setOnClickListener(this);
        arrow_down.setOnClickListener(this);
        view_now.setOnClickListener(this);
        view_time.setOnClickListener(this);
        billing_card.setOnClickListener(this);
        billing_wallet.setOnClickListener(this);
        select_left.setOnClickListener(this);
        select_right.setOnClickListener(this);

        order_name.setText(AppController.getInstance().preferences.getString("order_name", ""));
        order_phone.setText(AppController.getInstance().preferences.getString("order_phone", ""));
        order_email.setText(AppController.getInstance().preferences.getString("order_email", ""));
        order_street.setText(AppController.getInstance().preferences.getString("order_street", ""));
        order_house.setText(AppController.getInstance().preferences.getString("order_house", ""));
        order_apartament.setText(AppController.getInstance().preferences.getString("order_apartament", ""));

        OnFocusChangeListener focusListener = new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String value = order_phone.getText().toString().trim();

                    if (value.matches("")) {
                        order_phone.setText("+7");
                        order_phone.setSelection(order_phone.getText().length());
                    }
                }
            }
        };

        order_phone.setOnFocusChangeListener(focusListener);
    }

    private String addR(String s)
    {
        String result = "";
        if(s.length() < 4) result = s + " руб.";
        else result = s.substring(0, 1 + (s.length() - 4)) + " " + s.substring(1 + (s.length() - 4)) + " руб.";

        return result;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initialise();
        order_name.requestFocus();
    }


    public void setDateTime()
    {
        hourResult = now.get(Calendar.HOUR);
        int minuteResult = now.get(Calendar.MINUTE);
        int currentDate = now.get(Calendar.DAY_OF_MONTH);

        if (hourResult > 23) {
            now.add(Calendar.DATE, 1);
            currentDate = currentDate + 1;
        }

        final DatePickerDialog dateDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String oldText = (String) order_time.getText();
                        order_time.setText(formatDate(String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth)) + " " + oldText);

                        selectedDate = String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear + 1) + "." + String.valueOf(year) + " " + oldText;
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), currentDate
        );

        TimePickerDialog timeDialog = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        order_time.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                        dateDialog.show(getFragmentManager(), "timeDialog");
                    }
                }, hourResult, minuteResult, true
        );

        if (hourResult < 11) {
            timeDialog.setMinTime(12, minuteResult, 0);
        } else {
            timeDialog.setMinTime(hourResult + 1, minuteResult, 0);
        }

        dateDialog.setMinDate(now);

        timeDialog.setMaxTime(23, 0, 0);

        timeDialog.show(getFragmentManager(), "dateDialog");
        order_now.setVisibility(View.INVISIBLE);
    }

    public String formatDate(String dateS)
    {
        String result = "";

            Locale locale = new Locale("ru");
            SimpleDateFormat originalFormat = new SimpleDateFormat("Md");
            SimpleDateFormat newFormat = new SimpleDateFormat("dd (MMMM)", locale);
            try {
                Date formatDate = originalFormat.parse((String.valueOf(dateS)));
                result =  newFormat.format(formatDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        return result;
    }

    public void selectOnButton(Button button)
    {
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.startTransition(100);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                button,
                "textColor",
                Color.WHITE,
                Color.BLACK);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();

        OnSelect = button;
        text_but_order.setText("Оформить за " + addR(String.valueOf(sum_button)));
    }

    public void selectOffButton(Button button)
    {
        TransitionDrawable drawable = (TransitionDrawable) button.getBackground();
        drawable.reverseTransition(100);

        ObjectAnimator colorAnim = ObjectAnimator.ofInt(
                button,
                "textColor",
                Color.BLACK,
                Color.WHITE);
        colorAnim.setDuration(100);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }

    @Override
    public void onClick(View v) {
        Log.d("json", "click");

        switch (v.getId())
        {
            case R.id.select_left:
                if (OnSelect != v) {
                    AppController.getInstance().setDelivery("courier");

                    AppController.getInstance().getMainActivity().updateBagPrice();

                    if(AppController.getInstance().getSale() == 0) {
                        sum_button = AppController.getInstance().getBagPrice() + 150;
                    } else {
                        sum_button = AppController.getInstance().getWithSale();
                    }

                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);

                    row_address.setVisibility(View.VISIBLE);
                    view_street.setVisibility(View.VISIBLE);
                    view_house.setVisibility(View.VISIBLE);
                    view_apartament.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.select_right:
                if (OnSelect != v) {
                    AppController.getInstance().setDelivery("self");

                    AppController.getInstance().getMainActivity().updateBagPrice();

                    sum_button = AppController.getInstance().getWithSale();

                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);

                    row_address.setVisibility(View.GONE);
                    view_street.setVisibility(View.GONE);
                    view_house.setVisibility(View.GONE);
                    view_apartament.setVisibility(View.GONE);
                }
                break;
            case R.id.view_time:
                setDateTime();
                order_now.setVisibility(View.GONE);
                break;
            case R.id.view_now:
                order_now.setVisibility(View.VISIBLE);
                if(order_time.getText() != "") {
                    order_time.setHint(order_time.getText());
                    order_time.setText("");
                }
                break;
            case R.id.billing_wallet:
                view_renting.setVisibility(View.VISIBLE);
                check_card.setVisibility(View.GONE);
                check_wallet.setVisibility(View.VISIBLE);

                break;
            case R.id.billing_card:
                view_renting.setVisibility(View.GONE);
                check_card.setVisibility(View.VISIBLE);
                check_wallet.setVisibility(View.GONE);
                break;
            case R.id.arrow_down:
                finish();
                break;
            case R.id.but_to_order:
                if(validEditText()) {
                    Log.d("json", "Заказываю...");
                    Buy b = new Buy();

                    progressDialog.setMessage("Заказ отправляется...");
                    progressDialog.show();

                    b.setUpdateListener(new Buy.OnUpdateListener() {
                        public void onUpdate(JSONObject result) {
                            progressDialog.dismiss();

                            try {
                                if (result.get("orderId") != null) {
                                    buyDialog.show(getFragmentManager(), "dialogInBag");

                                    AppController.getInstance().inBag.clear();

                                    MainActivity mainActivity = AppController.getInstance().getMainActivity();

                                    mainActivity.updateBagPrice();
                                    mainActivity.bagFrag.updateFragPrice();
                                } else {
                                    errorDialog
                                        .setMessage(result.get("message").toString())
                                        .setPositiveButton("Закрыть", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                            errorDialog.dismiss();
                                            }
                                        })
                                        .show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    b.execute(new Buyer(
                            order_name.getText().toString(),
                            order_phone.getText().toString(),
                            order_street.getText().toString(),
                            order_house.getText().toString(),
                            order_apartament.getText().toString(),
                            OnSelect.getId() == R.id.select_left ? "courier" : "self",
                            check_wallet.getVisibility() == View.VISIBLE ? "CASH" : "CARD",
                            order_renting.getText().toString(),
                            String.valueOf(sum_button),
                            selectedDate,
                            AppController.getInstance().getInBag()
                    ));

                    commitEditData();
                } else {
                    Log.d("json", "invalid edit text");
                }
        }

    }

    public void onDismiss(final DialogInterface dialog) {
        Intent i = new Intent(OrderActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void commitEditData() {
        SharedPreferences.Editor editPref = AppController.getInstance().editPref;

        editPref.putString("order_name", order_name.getText().toString());
        editPref.putString("order_phone", order_phone.getText().toString());
        editPref.putString("order_email", order_email.getText().toString());
        editPref.putString("order_street", order_street.getText().toString());
        editPref.putString("order_house", order_house.getText().toString());
        editPref.putString("order_apartament", order_apartament.getText().toString());
        editPref.commit();
    }

    private boolean validEditText()
    {
        String phone = order_phone.getText().toString().trim();
        boolean valid = true;
        String message = "";

        if (order_now.getVisibility() == View.VISIBLE && (hourResult > 23 || hourResult < 11)) {
            message = "С 00:00 до 11:00 заказы не принимаются. Пожалуйста оформите заказ на другое время";
            valid = false;
        }

        if (order_name.getText().toString().trim().matches(""))
        {
            message = "Поле \"Имя \" - обязательно для заполнения";
            valid = false;
        }

        if (phone.matches(""))
        {
            message = "Поле \"Телефон \" - обязательно для заполнения";
            valid = false;
        } else if (phone.length() != 12) {
            message = "Поле \"Телефон \" - не соответствует формату";
            valid = false;
        }

        if (OnSelect.getId() == R.id.select_left) {
            if (order_street.getText().toString().trim().matches("")) {
                message = "Поле \"Улица \" - обязательно для заполнения";
                valid = false;
            }

            if (order_house.getText().toString().trim().matches("")) {
                message = "Поле \"Дом \" - обязательно для заполнения";
                valid = false;
            }
        }

        if (!valid) {
            invalidDialog = new MaterialDialog(this)
                .setMessage(message)
                .setPositiveButton("Закрыть", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    invalidDialog.dismiss();
                    }
                });
            invalidDialog.show();
        }

        return valid;
    }
}
