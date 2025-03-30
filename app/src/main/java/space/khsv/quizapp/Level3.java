package space.khsv.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.GridLayout;
import android.widget.Toast;
import android.app.Dialog;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level3 extends AppCompatActivity {
    private GridLayout gridLayout;
    private List<Card> cards;
    private Integer firstSelectedIndex = null;
    private Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexes);

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.prewiew_dialog3);
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
                Intent intent = new Intent(Level3.this, GameLevels.class);
                startActivity(intent);
            }
        });


        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level3.this, GameLevels.class);
                startActivity(intent);
            }
        });

        gridLayout = findViewById(R.id.gridLayout);

        setupGame();
    }

    private void setupGame() {
        int[] images = {
                R.drawable.c11, R.drawable.s1, R.drawable.s2, R.drawable.s3,
                R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7
        };

        cards = new ArrayList<>();
        for (int image : images) {
            cards.add(new Card(cards.size(), image));
            cards.add(new Card(cards.size(), image));
        }

        Collections.shuffle(cards);

        // Dynamicky přidání karet do GridLayout
        for (int i = 0; i < cards.size(); i++) {
            View cardView = LayoutInflater.from(this).inflate(R.layout.card_item, gridLayout, false);
            ImageButton cardButton = cardView.findViewById(R.id.cardButton);

            // Nastavení obrázku a události pro kliknutí na kartu
            final int position = i;
            cardButton.setImageResource(R.drawable.kota); // Představuje zakrytou kartu
            cardButton.setOnClickListener(v -> onCardClicked(position));

            // Přidání karty do GridLayout
            gridLayout.addView(cardView);
        }
    }

    private void onCardClicked(int position) {
        Card clickedCard = cards.get(position);

        if (clickedCard.isFaceUp() || clickedCard.isMatched()) return;

        clickedCard.setFaceUp(true);
        updateGrid();

        if (firstSelectedIndex == null) {
            firstSelectedIndex = position;
        } else {
            final int previousIndex = firstSelectedIndex;
            Card previousCard = cards.get(previousIndex);

            if (previousCard.getImageResId() == clickedCard.getImageResId()) {
                previousCard.setMatched(true);
                clickedCard.setMatched(true);
            } else {
                handler.postDelayed(() -> {
                    previousCard.setFaceUp(false);
                    clickedCard.setFaceUp(false);
                    updateGrid();
                }, 1000);
            }
            firstSelectedIndex = null;
        }

        // Zkontrolujeme, zda je hra dokončena
        if (isGameFinished()) {
            showGameFinishedDialog();
        }
    }

    // Funkce pro zjištění, zda je hra dokončena
    private boolean isGameFinished() {
        for (Card card : cards) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }



    // Funkce pro zobrazení vlastního dialogového okna po skončení hry
    private void showGameFinishedDialog() {
        // Vytvoření Dialogu s vlastním XML layoutem
        Dialog dialogEnd = new Dialog(this);
        dialogEnd.setContentView(R.layout.dialog_end3);
        dialogEnd.setCancelable(false); // Zabránit zavření dialogu kliknutím mimo něj

        Button btnconEnd = dialogEnd.findViewById(R.id.btncontinue);
        btnconEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level3.this, GameLevels.class);
                startActivity(intent);
                dialogEnd.dismiss();
            }
        });

        TextView btnclosEnd = dialogEnd.findViewById(R.id.btnclose);
        btnclosEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level3.this, GameLevels.class);
                startActivity(intent);
                dialogEnd.dismiss();
            }
        });

        dialogEnd.show(); // Zobrazíme dialog
    }

    // Funkce pro aktualizaci zobrazení karet
    private void updateGrid() {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            ImageButton cardButton = (ImageButton) gridLayout.getChildAt(i).findViewById(R.id.cardButton);
            cardButton.setImageResource(card.isFaceUp() ? card.getImageResId() : R.drawable.kota);
            cardButton.setEnabled(!card.isMatched());
        }
    }
}
