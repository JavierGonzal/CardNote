package com.javier.cardnote.detail;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.javier.cardnote.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by javiergonzalezcabezas on 13/1/18.
 */

public class DetailFragment extends Fragment implements DetailContract.View {


    GradientDrawable gradientDrawable;

    DetailContract.Presenter presenter;

    public static int corner = 195;
    public static int margin = 0;

    public static String color = "#7D9067";

    @BindView(R.id.detail_base_view)
    ViewGroup parent;

    @BindView(R.id.detail_imageView)
    ImageView imageView;
    boolean isCircle;

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
        gradientDrawable.setCornerRadius(30.0f);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        parent.setBackground(gradientDrawable);

        return view;
    }

    @OnClick(R.id.detail_imageView)
    void change(){
        if (isCircle) {
            makeSquare();
        }
        else {
            makeCircle();
        }
        isCircle = !isCircle;
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

        Animator shiftAnimation = AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_right_down);
        shiftAnimation.setTarget(parent);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(cornerAnimation, shiftAnimation);
        animatorSet.start();
    }

    private void makeSquare() {
        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", 200.0f, 30f);

        Animator shiftAnimation = AnimatorInflater.loadAnimator(getActivity(), R.animator.slide_left_up);
        shiftAnimation.setTarget(parent);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(cornerAnimation, shiftAnimation);
        animatorSet.start();
    }
}
