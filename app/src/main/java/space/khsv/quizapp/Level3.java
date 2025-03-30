package space.khsv.quizapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level3 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<Card> cards;
    private Integer firstSelectedIndex = null;
    private Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pexes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        setupGame();
    }

    private void setupGame() {
        int[] images = {
                R.drawable.c9, R.drawable.s1, R.drawable.s2, R.drawable.s3,
                R.drawable.s4, R.drawable.s5, R.drawable.s6, R.drawable.s7
        };

        cards = new ArrayList<>();
        for (int image : images) {
            cards.add(new Card(cards.size(), image));
            cards.add(new Card(cards.size(), image));
        }

        Collections.shuffle(cards);
        adapter = new CardAdapter(cards, this::onCardClicked);
        recyclerView.setAdapter(adapter);
    }

    private void onCardClicked(int position) {
        Card clickedCard = cards.get(position);

        if (clickedCard.isFaceUp() || clickedCard.isMatched()) return;

        clickedCard.setFaceUp(true);
        adapter.notifyItemChanged(position);

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
                    adapter.notifyItemChanged(previousIndex);
                    adapter.notifyItemChanged(position);
                }, 1000);
            }
            firstSelectedIndex = null;
        }
    }
}
