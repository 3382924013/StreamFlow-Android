package com.streamflow.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.streamflow.R;
import com.streamflow.data.MockData;
import com.streamflow.model.Movie;
import com.streamflow.ui.player.PlayerActivity;
import com.streamflow.utils.AppState;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView recyclerResults;
    private LinearLayout layoutHistory;
    private LinearLayout layoutHot;
    private TextView tvEmpty;
    private SearchResultAdapter adapter;
    private List<Movie> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.et_search);
        recyclerResults = findViewById(R.id.recycler_search_results);
        layoutHistory = findViewById(R.id.layout_history);
        layoutHot = findViewById(R.id.layout_hot);
        tvEmpty = findViewById(R.id.tv_empty);

        recyclerResults.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new SearchResultAdapter(results, movie -> {
            Intent intent = new Intent(this, PlayerActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        });
        recyclerResults.setAdapter(adapter);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
        findViewById(R.id.btn_clear).setOnClickListener(v -> etSearch.setText(""));

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        setupHistory();
        setupHotSearches();
    }

    private void performSearch(String query) {
        if (query.trim().isEmpty()) {
            recyclerResults.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
            layoutHistory.setVisibility(View.VISIBLE);
            layoutHot.setVisibility(View.VISIBLE);
            return;
        }

        AppState.getInstance().addSearchHistory(query);
        results.clear();
        for (Movie m : MockData.getMovies()) {
            if (m.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                m.getGenre().toString().contains(query)) {
                results.add(m);
            }
        }
        adapter.notifyDataSetChanged();

        layoutHistory.setVisibility(View.GONE);
        layoutHot.setVisibility(View.GONE);
        if (results.isEmpty()) {
            recyclerResults.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerResults.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void setupHistory() {
        LinearLayout container = findViewById(R.id.container_history);
        container.removeAllViews();
        for (String h : AppState.getInstance().getSearchHistory()) {
            TextView chip = createChip(h);
            chip.setOnClickListener(v -> {
                etSearch.setText(h);
                performSearch(h);
            });
            container.addView(chip);
        }
        findViewById(R.id.btn_clear_history).setOnClickListener(v -> {
            AppState.getInstance().clearSearchHistory();
            container.removeAllViews();
        });
    }

    private void setupHotSearches() {
        LinearLayout container = findViewById(R.id.container_hot);
        String[] hots = {"星际穿越", "千与千寻", "盗梦空间", "肖申克的救赎", "让子弹飞"};
        for (int i = 0; i < hots.length; i++) {
            TextView chip = createChip(hots[i]);
            if (i < 3) {
                chip.setTextColor(getColor(R.color.coral));
            }
            chip.setOnClickListener(v -> {
                String text = ((TextView) v).getText().toString();
                etSearch.setText(text);
                performSearch(text);
            });
            container.addView(chip);
        }
    }

    private TextView createChip(String text) {
        TextView chip = new TextView(this);
        chip.setText(text);
        chip.setTextSize(12);
        chip.setTextColor(getColor(R.color.gray_600));
        chip.setBackgroundResource(R.drawable.bg_chip);
        chip.setPadding(24, 12, 24, 12);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 16, 8);
        chip.setLayoutParams(params);
        return chip;
    }
}
