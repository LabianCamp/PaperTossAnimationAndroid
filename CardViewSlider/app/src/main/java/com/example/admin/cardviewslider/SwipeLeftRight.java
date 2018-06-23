package com.example.admin.cardviewslider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class SwipeLeftRight extends AppCompatActivity {

    Context context;
    RelativeLayout parentView;
    int windowWidth, windowHeight;
    int screenCenterWidth, screenCenterHeight;
    float alphaValue = 0;
    ArrayList<UserDataModel> userDataModelArrayList;
    int x_cord, y_cord, x, y;
    int Likes = 0;
    ImageView userIMG;
    int[] location;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_left_right);
        context = SwipeLeftRight.this;
        parentView = findViewById(R.id.main_layoutview);
        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenCenterWidth = windowWidth / 2;
        screenCenterHeight = windowHeight / 2;
        userDataModelArrayList = new ArrayList<>();
        getArrayData();
        setupSwipe();
    }

    private void setupSwipe() {
        for (int i = 0; i < userDataModelArrayList.size(); i++) {
            LayoutInflater inflate = (LayoutInflater) SwipeLeftRight.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View containerView = inflate.inflate(R.layout.tinder_swipe, null);
            userIMG = containerView.findViewById(R.id.userIMG);
            RelativeLayout relativeLayoutContainer = containerView.findViewById(R.id.relative_container);
            ViewGroup.LayoutParams layoutParams = new
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            containerView.setLayoutParams(layoutParams);
            userIMG.setBackgroundResource(userDataModelArrayList.get(i).getPhoto());
            ViewGroup.LayoutParams layoutTvParams = new
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // ADD dynamically like TextView on image.
            final TextView tvLike = customTextViewLike(layoutTvParams);
            relativeLayoutContainer.addView(tvLike);

            // ADD dynamically dislike TextView on image.
            final TextView tvUnLike = customTextViewLiked(layoutTvParams);
            relativeLayoutContainer.addView(tvUnLike);

            //set text
            TextView tvName = containerView.findViewById(R.id.tvName);
            TextView tvTotLikes = containerView.findViewById(R.id.tvTotalLikes);
            tvName.setText(userDataModelArrayList.get(i).getName());
            tvTotLikes.setText(userDataModelArrayList.get(i).getTotalLikes());

            // Touch listener on the image layout to swipe image right or left.
            relativeLayoutContainer.setOnTouchListener(new MySwipeTouch(tvLike, tvUnLike, containerView));
            parentView.addView(containerView);
        }
    }

    @NonNull
    private TextView customTextViewLiked(ViewGroup.LayoutParams layoutTvParams) {
        final TextView tvUnLike = new TextView(context);
        tvUnLike.setLayoutParams(layoutTvParams);
        tvUnLike.setPadding(20, 20, 20, 20);
        tvUnLike.setBackground(getResources().getDrawable(R.drawable.btnlikedback));
        tvUnLike.setText("WRONG");
        tvUnLike.setGravity(Gravity.CENTER);
        tvUnLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvUnLike.setTextSize(25);
        tvUnLike.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        tvUnLike.setX(460);
        tvUnLike.setY(100);
        tvUnLike.setRotation(50);
        tvUnLike.setAlpha(alphaValue);
        return tvUnLike;
    }

    @NonNull
    private TextView customTextViewLike(ViewGroup.LayoutParams layoutTvParams) {
        final TextView tvLike = new TextView(context);
        tvLike.setLayoutParams(layoutTvParams);
        tvLike.setPadding(20, 20, 20, 20);
        tvLike.setBackground(getResources().getDrawable(R.drawable.btnlikeback));
        tvLike.setText("RIGHT");
        tvLike.setGravity(Gravity.CENTER);
        tvLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvLike.setTextSize(25);
        tvLike.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        tvLike.setX(15);
        tvLike.setY(100);
        tvLike.setRotation(-50);
        tvLike.setAlpha(alphaValue);
        return tvLike;
    }


    private void getArrayData() {
        UserDataModel model = new UserDataModel();
        model.setName("Nimblechapps 1 ");
        model.setTotalLikes("3 M");
        model.setPhoto(R.drawable.cute);
        userDataModelArrayList.add(model);

        UserDataModel model2 = new UserDataModel();
        model2.setName("Nimblechapps 2 ");
        model2.setTotalLikes("2 M");
        model2.setPhoto(R.drawable.cute2);
        userDataModelArrayList.add(model2);

        UserDataModel model3 = new UserDataModel();
        model3.setName("Nimblechapps 3 ");
        model3.setTotalLikes("3 M");
        model3.setPhoto(R.drawable.cute3);
        userDataModelArrayList.add(model3);

        UserDataModel model4 = new UserDataModel();
        model4.setName("Nimblechapps 4 ");
        model4.setTotalLikes("4 M");
        model4.setPhoto(R.drawable.cute);
        userDataModelArrayList.add(model4);

        UserDataModel model5 = new UserDataModel();
        model5.setName("Nimblechapps 5 ");
        model5.setTotalLikes("5 M");
        model5.setPhoto(R.drawable.cute2);
        userDataModelArrayList.add(model5);

        UserDataModel model6 = new UserDataModel();
        model6.setName("Nimblechapps 6 ");
        model6.setTotalLikes("6 M");
        model6.setPhoto(R.drawable.cute3);
        userDataModelArrayList.add(model6);

        Collections.reverse(userDataModelArrayList);
    }

    private class MySwipeTouch implements View.OnTouchListener {
        TextView tvLike;
        TextView tvUnLike;
        View containerView;

        MySwipeTouch(TextView tvLike, TextView tvUnLike, View containerView) {
            this.tvLike = tvLike;
            this.tvUnLike = tvUnLike;
            this.containerView = containerView;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            x_cord = (int) event.getRawX();
            y_cord = (int) event.getRawY();
            location = new int[2];
            containerView.getLocationInWindow(location);
            containerView.setX(0);
            containerView.setY((windowHeight - userIMG.getHeight()) / 2 - heightOfStatus());
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    x = (int) event.getX();
                    y = (int) event.getY();

                    break;
                case MotionEvent.ACTION_MOVE:

                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();
                    // smoother animation
                    containerView.getLocationInWindow(location);
                    containerView.setX(0);
                    containerView.setY((windowHeight - userIMG.getHeight()) / 2 - heightOfStatus());
//                    containerView.setX(x_cord - x);
//                    containerView.setY(y_cord - y - heightOfStatus());
                    //rotate right
                    if (x_cord >= screenCenterWidth) {
                        containerView.setPivotX(containerView.getWidth()/2);
                        containerView.setPivotY(userIMG.getHeight()+ heightOfStatus() + (windowHeight - userIMG.getHeight()) / 2 - heightOfStatus());
                        containerView.setRotation((float) ((x_cord - screenCenterWidth) * (Math.PI / 32)));
                        if (x_cord > (screenCenterWidth + (screenCenterWidth / 2))) {
                            tvLike.setAlpha(1);
                            if (x_cord > (windowWidth - (screenCenterWidth / 4))) {
                                Likes = 2;
                            } else {
                                Likes = 0;
                            }
                        } else {
                            Likes = 0;
                            tvLike.setAlpha(0);
                        }
                        tvUnLike.setAlpha(0);
                    } else {
                        // rotate left
                        containerView.setPivotX(containerView.getWidth()/2);
                        containerView.setPivotY(userIMG.getHeight()+ heightOfStatus() + (windowHeight - userIMG.getHeight()) / 2 - heightOfStatus());
                        containerView.setRotation((float) ((x_cord - screenCenterWidth) * (Math.PI / 32)));
                        if (x_cord < (screenCenterWidth / 2)) {
                            tvUnLike.setAlpha(1);
                            if (x_cord < screenCenterWidth / 4) {
                                Likes = 1;
                            } else {
                                Likes = 0;
                            }
                        } else {
                            Likes = 0;
                            tvUnLike.setAlpha(0);
                        }
                        tvLike.setAlpha(0);
                        if (y_cord <= (screenCenterHeight / 6)) {
                            Likes = 3;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    tvUnLike.setAlpha(0);
                    tvLike.setAlpha(0);

                    if (Likes == 0) {
                        containerView.setY((windowHeight - userIMG.getHeight()) / 2 - heightOfStatus());
                        containerView.setX(0);
                        containerView.setRotation(0);
                    } else if (Likes == 1) {
                        parentView.removeView(containerView);
                    } else if (Likes == 2) {
                        parentView.removeView(containerView);
                    } else if (Likes == 3) {
                        Toast.makeText(context, "REMOVE", Toast.LENGTH_SHORT).show();
                        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(SwipeLeftRight.this, R.animator.swing_up_left);
                        containerView.setAnimation(animation);
                        parentView.removeView(containerView);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private int heightOfStatus() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = getBaseContext().getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return statusBarHeight + actionBarHeight;
    }
}
