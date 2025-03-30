package space.khsv.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    Dialog dialog;

    Dialog dialogEnd;
    public int numLeft;
    public int numRight;
    Array array = new Array();
    Random random = new Random();

    public int count = 0;
    private int imageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.uni2);

        final ImageView img_left = (ImageView) findViewById(R.id.img_left);
        final ImageView img_right = (ImageView) findViewById(R.id.img_right);
        final ImageView img_cat = (ImageView) findViewById(R.id.makeup0);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.prewiew_dialog2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        Button btncon = dialog.findViewById(R.id.btncontinue);
        btncon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView btnclos = dialog.findViewById(R.id.btnclose);
        btnclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2.this, GameLevels.class);
                startActivity(intent);
            }
        });

        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2.this, GameLevels.class);
                startActivity(intent);
            }
        });

        dialogEnd = new Dialog(this);
        dialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEnd.setContentView(R.layout.dialog_end2);
        dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogEnd.setCancelable(false);

        Button btnconEnd = dialogEnd.findViewById(R.id.btncontinue);
        btnconEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2.this, Level1.class);
                startActivity(intent);
                dialogEnd.dismiss();
            }
        });

        TextView btnclosEnd = dialogEnd.findViewById(R.id.btnclose);
        btnclosEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level2.this, GameLevels.class);
                startActivity(intent);
                dialogEnd.dismiss();
            }
        });

        final int[] images2 = {
                R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5, R.drawable.c6, R.drawable.c7, R.drawable.c8,
                R.drawable.c9, R.drawable.c10, R.drawable.c11

        };

        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6, R.id.point7,
                R.id.point8, R.id.point9, R.id.point10, R.id.point11
        };

        final Animation a = AnimationUtils.loadAnimation(Level2.this, R.anim.alpha);

        numLeft = random.nextInt(7);
        img_left.setImageResource(array.images3[numLeft]);


        numRight = random.nextInt(7);
//        img_right.setImageResource(array.images1[numRight]);

        while (numLeft == numRight) {
            numRight = random.nextInt(7);
        }

        img_right.setImageResource(array.images3[numRight]);

        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    img_right.setEnabled(false);
                    if (numLeft > numRight) {
                        img_left.setImageResource(R.drawable.ri);
                    } else {
                        img_left.setImageResource(R.drawable.wr);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (numLeft > numRight) {
                        if (count < 10) {
                            count = count + 1;
                            if (imageIndex < images2.length - 1) {
                                imageIndex++;
                                img_cat.setImageResource(images2[imageIndex]);
                            }
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_correct);
                        }

                    } else {

                        if (count > 0) {
                            if (count == 1) {
                                count = 0;
                            } else {
                                count = count - 0;
                            }
                        }
                        for (int i = 0; i < 9; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_correct);
                        }
                    }
                    if (count == 10) {
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(7);
                        img_left.setImageResource(array.images3[numLeft]);
                        img_left.startAnimation(a);
                        numRight = random.nextInt(7);

                        while (numLeft == numRight) {
                            numRight = random.nextInt(7);
                        }

                        img_right.setImageResource(array.images3[numRight]);
                        img_right.startAnimation(a);
                        img_right.setEnabled(true);
                    }
                }
                return true;
            }
        });




        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    img_left.setEnabled(false);
                    if (numRight > numLeft) {
                        img_right.setImageResource(R.drawable.ri);
                    } else {
                        img_right.setImageResource(R.drawable.wr);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (numLeft < numRight) {
                        if (count < 10) {
                            count = count + 1;
                            if (imageIndex < images2.length - 1) {
                                imageIndex++;
                                img_cat.setImageResource(images2[imageIndex]);
                            }
                        }
                        for (int i = 0; i < 10; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_correct);
                        }

                    } else {

                        if (count > 0) {
                            if (count == 1) {
                                count = 0;
                            } else {
                                count = count - 0;
                            }
                        }
                        for (int i = 0; i < 9; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }

                        for (int i = 0; i < count; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_correct);
                        }
                    }
                    if (count == 10) {
                        dialogEnd.show();
                    } else {
                        numLeft = random.nextInt(7);
                        img_left.setImageResource(array.images3[numLeft]);
                        img_left.startAnimation(a);
                        numRight = random.nextInt(7);

                        while (numLeft == numRight) {
                            numRight = random.nextInt(7);
                        }

                        img_right.setImageResource(array.images3[numRight]);
                        img_right.startAnimation(a);
                        img_left.setEnabled(true);
                    }
                }
                return true;
            }
        });
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.piki);
        mediaPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//}
