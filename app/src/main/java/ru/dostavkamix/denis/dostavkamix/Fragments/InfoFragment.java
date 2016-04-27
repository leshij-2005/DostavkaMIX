package ru.dostavkamix.denis.dostavkamix.Fragments;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.api.IMapController;
import org.osmdroid.api.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import java.util.ArrayList;

import ru.dostavkamix.denis.dostavkamix.AppController;
import ru.dostavkamix.denis.dostavkamix.R;
import ru.dostavkamix.denis.dostavkamix.SwipeImageAdapter;

/**
 * Created by den on 04.02.2016.
 */
public class InfoFragment extends Fragment implements OnClickListener {

    ViewPager pager_view;
    SwipeImageAdapter mAdapter;
    ArrayList<Integer> image_list = new ArrayList<>();
    private static final String url = "http://www.chaihanamix.ru";

    //Containers
    private LinearLayout lay_info;
    private MapView map_view;

    //Map
    private IMapController imap;
    private final GeoPoint startPoint = new GeoPoint(51.761789, 55.10654);
    private ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
    private DefaultResourceProxyImpl resourceProxy = new DefaultResourceProxyImpl(AppController.getInstance().getApplicationContext());


    //Select button
    private Button select_left;
    private Button select_right;
    private Button OnSelect;

    //Info
    private RelativeLayout lay_map;
    private RelativeLayout lay_web;

    //Call button
    private RelativeLayout but_to_call;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        //Containers
        lay_info = (LinearLayout) v.findViewById(R.id.lay_info);
        map_view = (MapView) v.findViewById(R.id.map_view);

        //Map

        map_view.setTileSource(TileSourceFactory.MAPNIK);
        map_view.setBuiltInZoomControls(true);
        map_view.setMultiTouchControls(true);
        imap = map_view.getController();
        imap.setZoom(20);
        imap.setCenter(startPoint);
        ArrayList<OverlayItem> overlays = new ArrayList<OverlayItem>();
        //overlays.add(new OverlayItem("New Overlay", "Overlay Description", startPoint));

        OverlayItem olItem = new OverlayItem("Here", "SampleDescription", startPoint);
        Drawable newMarker = this.getResources().getDrawable(R.drawable.map_marker_icon);
        olItem.setMarker(newMarker);
        overlays.add(olItem);
        ItemizedIconOverlay<OverlayItem> iconOverlay = new ItemizedIconOverlay<OverlayItem>(overlays, null, resourceProxy);
        map_view.getOverlays().add(iconOverlay);


        // Select button
        select_left = (Button) v.findViewById(R.id.select_left);
        select_right = (Button) v.findViewById(R.id.select_right);
        OnSelect = null;
        select_left.setOnClickListener(this);
        select_right.setOnClickListener(this);
        selectOnButton(select_left);

        //Info
        lay_map = (RelativeLayout) v.findViewById(R.id.relative_1);
        lay_web = (RelativeLayout) v.findViewById(R.id.relative_4);
        lay_map.setOnClickListener(this);
        lay_web.setOnClickListener(this);

        //Cell button
        but_to_call = (RelativeLayout) v.findViewById(R.id.but_to_call);
        but_to_call.setOnClickListener(this);

        image_list.add(R.drawable.image1);
        image_list.add(R.drawable.image2);
        image_list.add(R.drawable.image3);
        image_list.add(R.drawable.image4);
        image_list.add(R.drawable.image5);
        image_list.add(R.drawable.image6);
        image_list.add(R.drawable.image7);
        image_list.add(R.drawable.image8);
        image_list.add(R.drawable.image9);
        image_list.add(R.drawable.image10);




        pager_view = (ViewPager) v.findViewById(R.id.pager_view);
        mAdapter = new SwipeImageAdapter(image_list, R.layout.info_swipe_layout);
        pager_view.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.select_left:
                if(OnSelect != v)
                {
                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);
                    lay_info.setVisibility(View.VISIBLE);
                    map_view.setVisibility(View.GONE);
                }
                break;
            case R.id.select_right:
                if(OnSelect != v)
                {
                    selectOffButton(OnSelect);
                    selectOnButton((Button) v);
                    map_view.setVisibility(View.VISIBLE);
                    lay_info.setVisibility(View.GONE);
                }
                break;
            case R.id.relative_1:
                selectOffButton(select_left);
                selectOnButton(select_right);
                map_view.setVisibility(View.VISIBLE);
                lay_info.setVisibility(View.GONE);
                break;
            case R.id.relative_4:
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browser);
                break;
            case R.id.but_to_call:
                Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "432222"));

                startActivity(call);
                break;
        }
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
}
