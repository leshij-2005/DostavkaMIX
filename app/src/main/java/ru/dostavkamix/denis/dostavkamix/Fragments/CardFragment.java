package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.Adapters.BoxAdapter;
import ru.dostavkamix.denis.dostavkamix.Adapters.CardAdapter;
import ru.dostavkamix.denis.dostavkamix.Dialogs.InOrderDialog;
import ru.dostavkamix.denis.dostavkamix.Objects.Dish;
import ru.dostavkamix.denis.dostavkamix.Activitys.MainActivity;
import ru.dostavkamix.denis.dostavkamix.R;

/**
 * Created by den on 21.01.16.
 */
public class CardFragment extends Fragment implements View.OnClickListener {
    private Button selectDishCount = null;
    private Dish onDish;
    private MainActivity mainActivity;
    private RelativeLayout inBag;
    private DialogFragment dialogInBag;

    int mCurrentPage = 0;
    ViewPager mViewPager;
    CardAdapter mAdapter;

    @Override
    public void onResume() {
        super.onResume();

        mAdapter = new CardAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        mViewPager = (ViewPager) rootView.findViewById(R.id.pager_card);
        mAdapter = new CardAdapter();
        mViewPager.setAdapter(mAdapter);

        inBag = (RelativeLayout) rootView.findViewById(R.id.inBag);

        rootView.findViewById(R.id.count_button1).setOnClickListener(this);
        rootView.findViewById(R.id.count_button2).setOnClickListener(this);
        rootView.findViewById(R.id.count_button3).setOnClickListener(this);
        rootView.findViewById(R.id.count_button4).setOnClickListener(this);
        rootView.findViewById(R.id.count_button5).setOnClickListener(this);
        rootView.findViewById(R.id.count_button6).setOnClickListener(this);
        rootView.findViewById(R.id.count_button7).setOnClickListener(this);
        rootView.findViewById(R.id.count_button8).setOnClickListener(this);
        rootView.findViewById(R.id.count_button9).setOnClickListener(this);
        rootView.findViewById(R.id.count_button10).setOnClickListener(this);
        inBag.setOnClickListener(this);
        dialogInBag = new InOrderDialog();


        switch (BoxAdapter.object.get(mViewPager.getCurrentItem()).getCountOrder()) {
            case 0:
                selectOnButton((Button) rootView.findViewById(R.id.count_button1));
            case 1:
                selectOnButton((Button) rootView.findViewById(R.id.count_button1));
                break;
            case 2:
                selectOnButton((Button) rootView.findViewById(R.id.count_button2));
                break;
            case 3:
                selectOnButton((Button) rootView.findViewById(R.id.count_button3));
                break;
            case 4:
                selectOnButton((Button) rootView.findViewById(R.id.count_button4));
                break;
            case 5:
                selectOnButton((Button) rootView.findViewById(R.id.count_button5));
                break;
            case 6:
                selectOnButton((Button) rootView.findViewById(R.id.count_button6));
                break;
            case 7:
                selectOnButton((Button) rootView.findViewById(R.id.count_button7));
                break;
            case 8:
                selectOnButton((Button) rootView.findViewById(R.id.count_button8));
                break;
            case 9:
                selectOnButton((Button) rootView.findViewById(R.id.count_button9));
                break;
            default:
                selectOnButton((Button) rootView.findViewById(R.id.count_button10));
                break;
        }

        //selectOnButton((Button) rootView.findViewById(R.id.count_button1));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectOffButton(selectDishCount);
                switch (BoxAdapter.object.get(position).getCountOrder()) {
                    case 0:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button1));
                        break;
                    case 1:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button1));
                        break;
                    case 2:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button2));
                        break;
                    case 3:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button3));
                        break;
                    case 4:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button4));
                        break;
                    case 5:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button5));
                        break;
                    case 6:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button6));
                        break;
                    case 7:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button7));
                        break;
                    case 8:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button8));
                        break;
                    case 9:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button9));
                        break;
                    default:
                        selectOnButton((Button) rootView.findViewById(R.id.count_button10));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return rootView;
    }

    private void updateDish(int position) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() != R.id.inBag) {
            if (selectDishCount != v) {
                selectOffButton(selectDishCount);
                selectOnButton((Button) v);
            }
        } else {
            BoxAdapter.object.get(mViewPager.getCurrentItem()).setCountOrder(Integer.valueOf(String.valueOf(selectDishCount.getTag())));
            AppController.getInstance().addInBag(BoxAdapter.object.get(mViewPager.getCurrentItem()));
            mainActivity.updateBagPrice();

            dialogInBag.show(mainActivity.getFragmentManager(), "dialogInBag");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialogInBag.dismiss();
                }
            }).start();
        }
    }

    public void selectOnButton(Button button) {
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

        selectDishCount = button;
    }

    public void selectOffButton(Button button) {
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

    public void setOnDish(Dish onDish) {
        this.onDish = onDish;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setCurrentItem(int currentItem) {
        mCurrentPage = currentItem;

        mViewPager.setCurrentItem(mCurrentPage);
    }
}
