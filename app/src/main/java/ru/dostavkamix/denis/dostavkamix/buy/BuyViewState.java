package ru.dostavkamix.denis.dostavkamix.buy;

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

/**
 * Created by Денис on 17.09.2016.
 *
 * @author Denis Tkachenko
 */

public class BuyViewState implements ViewState<BuyView> {

    static final int STATE_SHOW_DELIVERY_FORM = 0;
    static final int STATE_SHOW_PICKUP_FORM = 1;
    static final int STATE_SHOW_CASH_COUNT_FORM = 2;
    static final int STATE_SHOW_POINTS_COUNT_FORM = 3;
    static final int STATE_SHOW_SELECT_TIME_DIALOG = 4;
    static final int STATE_SHOW_ERROR = 5;

    int state = STATE_SHOW_DELIVERY_FORM;

    @Override
    public void apply(BuyView view, boolean retained) {
        switch (state) {
            case STATE_SHOW_DELIVERY_FORM:
                view.showDeliveryForm();
                break;

            case STATE_SHOW_PICKUP_FORM:
                view.showPickupForm();
                break;

            case STATE_SHOW_CASH_COUNT_FORM:
                view.showCashCountForm();
                break;
            case STATE_SHOW_POINTS_COUNT_FORM:
                view.showPointsCountForm();
                break;
            case STATE_SHOW_SELECT_TIME_DIALOG:
                view.showSelectTimeDialog();
                break;
            case STATE_SHOW_ERROR:
                view.showError();
        }
    }

    void setShowDelieryForm() { state = STATE_SHOW_DELIVERY_FORM; }
    void setShowPickupForm() { state = STATE_SHOW_PICKUP_FORM; }
    void setShowCashCountForm() { state = STATE_SHOW_CASH_COUNT_FORM; }
    void setShowPointsCountForm() { state = STATE_SHOW_POINTS_COUNT_FORM; }
    void setShowSelectTimeDialog() { state = STATE_SHOW_SELECT_TIME_DIALOG; }
    void setShowError() { state = STATE_SHOW_ERROR; }
}
