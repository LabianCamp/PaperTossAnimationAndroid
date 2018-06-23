package com.example.admin.cardviewslider;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    int displayWidth;
    int[] location;
    int dregree = -1;
    RelativeLayout linearLayout;
    RotateAnimation anim = new RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        View left = findViewById(R.id.left);
        View right = findViewById(R.id.right);
//        left.setOnDragListener(new TrashDragListener());
//        right.setOnDragListener(new TrashDragListener());
        imageView = findViewById(R.id.image_card);
         linearLayout = findViewById(R.id.main_layoutview);
        linearLayout.setOnDragListener(new TrashDragListener());
        imageView.setOnTouchListener(new onTouch());
        Context context = getBaseContext();
        WindowManager mWinMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        assert mWinMgr != null;
        displayWidth = mWinMgr.getDefaultDisplay().getWidth();

    }

    private class TrashDragListener implements View.OnDragListener {
        private boolean hit;

        @SuppressLint("ResourceType")
        @Override
        public boolean onDrag(final View v, DragEvent event) {
            Log.d("TAGGG", "onDragx: "+event.getX());
            Log.d("TAGGG", "onDragy: "+event.getY());
            final ImageView draggedView = (ImageView) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    hit = false;
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    draggedView.setVisibility(View.INVISIBLE);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    //location of view parent
                    location = new int[2];
                    v.getLocationInWindow(location);
                    return true;
                case DragEvent.ACTION_DROP:
                    draggedView.setVisibility(View.INVISIBLE);
                    View view = (View) event.getLocalState();
                    if (event.getX() == 0) {
                        Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                        view.clearAnimation();
                        Animation anima = AnimationUtils.loadAnimation(MainActivity.this, R.animator.swing_up_left);
                        anim.setFillAfter(true);
                        anim.setRepeatCount(0);
                        anim.setDuration(1000);
                        view.startAnimation(anima);
                        hit = false;
                        return false;
                    } else if (event.getY() >= linearLayout.getWidth() -draggedView.getWidth()/2) {
                        Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                        view.clearAnimation();
                        Animation anima = AnimationUtils.loadAnimation(MainActivity.this, R.animator.swing_up_right);
                        anim.setFillAfter(true);
                        anim.setRepeatCount(0);
                        anim.setDuration(1000);
                        view.startAnimation(anima);
                        hit = false;
                        return false;
                    } else
                        hit = false;
                    return false;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (!hit) {
                        draggedView.post(new Runnable() {
                            @Override
                            public void run() {
                                draggedView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

    private final class onTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(final View v, final MotionEvent event) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(v){
                @Override
                public void onDrawShadow(Canvas canvas) {
                    if(event.getX() <= displayWidth/2) {
                        Log.d("TAGGG", "onDrawShadowx:"+event.getX());
                        Log.d("TAGGG", "onDrawShadowy:"+event.getY());
                            canvas.rotate(0, (v.getWidth()) / 2, v.getHeight());
                        if(dregree == 30){
                            dregree = 0;
                        }
                        invalidateOptionsMenu();
                    }
                    super.onDrawShadow(canvas);
                }

                @Override
                public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
                    Log.d("TAGGG", "onProvideShadowMetricsx: "+outShadowTouchPoint.x);
                    Log.d("TAGGG", "onProvideShadowMetricsy: "+outShadowTouchPoint.y);
                    super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
                }
            };
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, dragShadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
            } else {
                v.startDrag(data, dragShadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
            }
                Log.d("TAGGG", "onTouchx: "+event.getX());
                Log.d("TAGGG", "onTouchy: "+event.getY());
            return true;
        }
    }
    

}

