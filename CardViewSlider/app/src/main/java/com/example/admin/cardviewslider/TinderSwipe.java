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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class TinderSwipe extends AppCompatActivity {
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
        setContentView(R.layout.activity_tinder_swipe);
        context = TinderSwipe.this;
        parentView = findViewById(R.id.main_layoutview);
        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenCenterWidth = windowWidth / 2;
        screenCenterHeight = windowHeight / 2;
        userDataModelArrayList = new ArrayList<>();
        getArrayData();
        for (int i = 0; i < userDataModelArrayList.size(); i++) {

            LayoutInflater inflate = (LayoutInflater) TinderSwipe.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

            //set text like & liked
            TextView tvName = containerView.findViewById(R.id.tvName);
            TextView tvTotLikes = containerView.findViewById(R.id.tvTotalLikes);
            tvName.setText(userDataModelArrayList.get(i).getName());
            tvTotLikes.setText(userDataModelArrayList.get(i).getTotalLikes());

            // Touch listener on the image layout to swipe image right or left.
            relativeLayoutContainer.setOnTouchListener(new MyTouch(tvLike, tvUnLike, containerView));
            parentView.addView(containerView);
        }
    }

    @NonNull
    private TextView customTextViewLiked(ViewGroup.LayoutParams layoutTvParams) {
        final TextView tvUnLike = new TextView(context);
        tvUnLike.setLayoutParams(layoutTvParams);
        tvUnLike.setPadding(10, 10, 10, 10);
        tvUnLike.setBackground(getResources().getDrawable(R.drawable.btnlikeback));
        tvUnLike.setText("UNLIKE");
        tvUnLike.setGravity(Gravity.CENTER);
        tvUnLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvUnLike.setTextSize(25);
        tvUnLike.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        tvUnLike.setX(500);
        tvUnLike.setY(150);
        tvUnLike.setRotation(50);
        tvUnLike.setAlpha(alphaValue);
        return tvUnLike;
    }

    @NonNull
    private TextView customTextViewLike(ViewGroup.LayoutParams layoutTvParams) {
        final TextView tvLike = new TextView(context);
        tvLike.setLayoutParams(layoutTvParams);
        tvLike.setPadding(10, 10, 10, 10);
        tvLike.setBackground(getResources().getDrawable(R.drawable.btnlikeback));
        tvLike.setText("LIKE");
        tvLike.setGravity(Gravity.CENTER);
        tvLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvLike.setTextSize(25);
        tvLike.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        tvLike.setX(20);
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

    private class MyTouch implements View.OnTouchListener {
        TextView tvLike;
        TextView tvUnLike;
        View containerView;

        MyTouch(TextView tvLike, TextView tvUnLike, View containerView) {
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
                    containerView.setX(x_cord - x);
                    containerView.setY(y_cord - y - heightOfStatus());
                    //rotate right
                    if (x_cord >= screenCenterWidth) {
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
                    }
                    if (y_cord <= screenCenterHeight / 3) {
                        containerView.setRotation((float) ((y_cord - screenCenterHeight) * (Math.PI / 2)));
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
                        Toast.makeText(context, "UNLIKE", Toast.LENGTH_SHORT).show();
                        parentView.removeView(containerView);
                    } else if (Likes == 2) {
                        Toast.makeText(context, "LIKED", Toast.LENGTH_SHORT).show();
                        parentView.removeView(containerView);
                    } else if (Likes == 3) {
                        Toast.makeText(context, "REMOVE", Toast.LENGTH_SHORT).show();
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