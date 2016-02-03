package ru.dostavkamix.denis.dostavkamix;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

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
    
    

    static Calendar now = Calendar.getInstance();
    String time = null;
    String date = null;


    private void initialise()
    {
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
        billing_card = (RelativeLayout) findViewById(R.id.billing_card);
            check_card = (ImageView) findViewById(R.id.check_card);
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

        String stringDate = "Сегодня";
        String stringTime = (String.valueOf(now.get(Calendar.HOUR)) + ":" + String.valueOf(now.get(Calendar.MINUTE)));
        order_time.setHint(stringDate + " " + stringTime);
        view_now.setOnClickListener(this);
        view_time.setOnClickListener(this);
        billing_card.setOnClickListener(this);
        billing_wallet.setOnClickListener(this);


        selectOnButton(select_left);

        select_left.setOnClickListener(this);
        select_right.setOnClickListener(this);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        return;
                    }
                }, now.get(Calendar.HOUR), now.get(Calendar.MINUTE), true
        );
        //tpd.show(getFragmentManager(), "TimeC");


    }


    public void setDateTame()
    {
        int hourResult = now.get(Calendar.HOUR);
        int minuteResult = now.get(Calendar.MINUTE);

        final DatePickerDialog dateDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Log.d("json", "Month: " + monthOfYear);
                        Log.d("json", "Day: " + dayOfMonth);
                        String oldText = (String) order_time.getText();
                        order_time.setText(formatDate(String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth)) + " " + oldText);

                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
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
                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);

                    if(v.getId() == R.id.select_right)
                    {
                        findViewById(R.id.row_address).setVisibility(View.GONE);
                        findViewById(R.id.view_street).setVisibility(View.GONE);
                        findViewById(R.id.view_house).setVisibility(View.GONE);
                        findViewById(R.id.view_apartament).setVisibility(View.GONE);
                        findViewById(R.id.scroll_l).requestLayout();

                    } else
                    {
                        findViewById(R.id.row_address).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_street).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_house).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_apartament).setVisibility(View.VISIBLE);
                        findViewById(R.id.scroll_l).requestLayout();
                    }
                }
                break;
            case R.id.select_right:
                if (OnSelect != v) {
                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);

                    if(v.getId() == R.id.select_right)
                    {
                        findViewById(R.id.row_address).setVisibility(View.GONE);
                        findViewById(R.id.view_street).setVisibility(View.GONE);
                        findViewById(R.id.view_house).setVisibility(View.GONE);
                        findViewById(R.id.view_apartament).setVisibility(View.GONE);

                    } else
                    {
                        findViewById(R.id.row_address).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_street).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_house).setVisibility(View.VISIBLE);
                        findViewById(R.id.view_apartament).setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.view_time:
                setDateTame();
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
        }

    }


}
