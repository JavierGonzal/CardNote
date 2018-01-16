package com.javier.cardnote.detail;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.javier.cardnote.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javiergonzalezcabezas on 13/1/18.
 */

public class DetailFragment extends Fragment implements DetailContract.View, View.OnTouchListener {


    GradientDrawable gradientDrawable;

    DetailContract.Presenter presenter;

    private int xDelta;

    public static String color = "#7D9067";

    @BindView(R.id.detail_base_view)
    ViewGroup parent;

    @BindView(R.id.detail_imageView)
    ImageView imageView;

    @BindView(R.id.detail_imageButton)
    ImageButton imageButton;

    @BindView(R.id.detail_relativeLayout)
    ViewGroup root;

    private int lastX = 0;
    private int onlyOne = 0;
    private int minX = 0;
    private int maxX = 0;
    private int firstDivision=0;
    private int secondDivision=0;
    private int thirdDivision=0;
    private int fourthDivision=0;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, view);
        presenter.subscribe();


        gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(0.0f);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        getDimension();
        imageButton.setOnTouchListener(this);


        return view;
    }

    private void getDimension() {
        parent.setBackground(gradientDrawable);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
        minX = layoutParams.leftMargin;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        maxX = size.x;
        firstDivision= (maxX)/5;
        secondDivision= (maxX*2)/5;
        thirdDivision= (maxX*3)/5;
        fourthDivision= (maxX*4)/5;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int x = (int) event.getRawX();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
                xDelta = x - lParams.leftMargin;
                break;
            case MotionEvent.ACTION_UP:
                changeAnimation(x);
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageButton.getLayoutParams();
                if ((minX < x - xDelta) && (maxX - 170 > x)) {
                    layoutParams.leftMargin = x - xDelta;
                }
                imageButton.setLayoutParams(layoutParams);
                break;
        }
        root.invalidate();
        return true;
    }

    void changeAnimation(int x) {
        if (lastX < x) {
            if (x > fourthDivision && onlyOne != 1) {
                makeShape(130.0f, 200.0f);
                onlyOne = 1;
            } else if ((thirdDivision < x && x < fourthDivision) && (onlyOne != 2)) {
                makeShape(80.0f, 130.0f);
                onlyOne = 2;
            } else if ((secondDivision < x && x < thirdDivision) && (onlyOne != 3)) {
                makeShape(30.0f, 80.0f);
                onlyOne = 3;
            } else if ((firstDivision < x && x < secondDivision) && (onlyOne != 4)) {
                makeShape(0.0f, 30.0f);
                onlyOne = 4;
            }
        } else {
            if ((thirdDivision < x && x < fourthDivision) && (onlyOne != 5)) {
                makeShape(200.0f, 130.0f);
                onlyOne = 5;
            } else if ((secondDivision < x && x < thirdDivision) && (onlyOne != 6)) {
                makeShape(130.0f, 80.0f);
                onlyOne = 6;
            } else if ((firstDivision < x && x < secondDivision) && (onlyOne != 7)) {
                makeShape(80.0f, 30.0f);
                onlyOne = 7;
            } else if ((firstDivision > x) && (onlyOne != 8)) {
                makeShape(30.0f, 0.0f);
                onlyOne = 8;
            }
        }

        lastX = x;

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showImage(String string) {
        Glide.with(this).load(string).into(imageView);
    }

    private void makeShape(float oldX, float newX) {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", oldX, newX);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(cornerAnimation);
        animatorSet.start();
    }

}
