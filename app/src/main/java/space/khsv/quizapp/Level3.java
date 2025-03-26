package space.khsv.quizapp;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Level3 extends AppCompatActivity {
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexes);

        paintView = findViewById(R.id.paintView);

        Button btnRed = findViewById(R.id.btnRed);
        Button btnBlue = findViewById(R.id.btnBlue);
        Button btnGreen = findViewById(R.id.btnGreen);
        Button btnClear = findViewById(R.id.btnClear);

        btnRed.setOnClickListener(v -> paintView.changeColor(Color.RED));
        btnBlue.setOnClickListener(v -> paintView.changeColor(Color.BLUE));
        btnGreen.setOnClickListener(v -> paintView.changeColor(Color.GREEN));
        btnClear.setOnClickListener(v -> paintView.clearCanvas());
    }
}