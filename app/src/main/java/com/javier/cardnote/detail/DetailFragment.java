package com.javier.cardnote.detail;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        parent.setBackground(gradientDrawable);
        imageButton.setOnTouchListener(this);


        return view;
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
                layoutParams.leftMargin = x - xDelta;
                imageButton.setLayoutParams(layoutParams);
                break;
        }
        root.invalidate();
        return true;
    }

    void changeAnimation(int x) {

        if (x > 200) {
            makeCircle();
        } else if (150 < x && x < 200) {
            makeCorner();
        //} else if (150 > xDelta) {
          //  makeSquare();
        }
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showImage(String string) {
        Glide.with(this).load(string).into(imageView);
    }

    private void makeCircle() {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", 30f, 200.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(cornerAnimation);
        animatorSet.start();
    }
    private void makeSquare() {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", 0f, 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(cornerAnimation);
        animatorSet.start();
    }

    private void makeCorner() {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", 200.0f, 30f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(cornerAnimation);
        animatorSet.start();
    }

}
