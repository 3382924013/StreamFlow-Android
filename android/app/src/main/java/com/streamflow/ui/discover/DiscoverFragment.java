package com.streamflow.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.streamflow.R;
import com.streamflow.data.MockData;
import com.streamflow.model.Movie;
import com.streamflow.ui.player.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvCount;
    private MovieGridAdapter adapter;
    private List<Movie> filteredMovies = new ArrayList<>();
    private String selectedGenre = "全部";
    private String selectedYear = "全部";
    private String selectedRegion = "全部";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_discover);
        tvCount = view.findViewById(R.id.tv_count);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new MovieGridAdapter(filteredMovies, movie -> {
            Intent intent = new Intent(requireContext(), PlayerActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        setupChips(view, R.id.chip_group_genre, MockData.GENRES, type -> {
            selectedGenre = type;
            applyFilter();
        });
        setupChips(view, R.id.chip_group_year, MockData.YEARS, type -> {
            selectedYear = type;
            applyFilter();
        });
        setupChips(view, R.id.chip_group_region, MockData.REGIONS, type -> {
            selectedRegion = type;
            applyFilter();
        });

        applyFilter();
    }

    private void setupChips(View view, int groupId, List<String> items, OnChipSelected listener) {
        ChipGroup group = view.findViewById(groupId);
        for (String item : items) {
            Chip chip = new Chip(requireContext());
            chip.setText(item);
            chip.setCheckable(true);
            chip.setChecked(item.equals("全部"));
            chip.setChipBackgroundColorResource(R.color.chip_background);
            chip.setTextColor(getResources().getColorStateList(R.color.chip_text, null));
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    for (int i = 0; i < group.getChildCount(); i++) {
                        Chip other = (Chip) group.getChildAt(i);
                        if (other != chip) other.setChecked(false);
                    }
                    listener.onSelected(item);
                }
            });
            group.addView(chip);
        }
    }

    private void applyFilter() {
        filteredMovies.clear();
        for (Movie m : MockData.getMovies()) {
            boolean genreMatch = selectedGenre.equals("全部") || m.getGenre().contains(selectedGenre);
            boolean yearMatch = selectedYear.equals("全部") ||
                (selectedYear.equals("2000s") ? m.getYear() >= 2000 && m.getYear() < 2010 :
                 selectedYear.equals("1990s") ? m.getYear() >= 1990 && m.getYear() < 2000 :
                 m.getYear() == Integer.parseInt(selectedYear));
            boolean regionMatch = selectedRegion.equals("全部") || m.getRegion().equals(selectedRegion);
            if (genreMatch && yearMatch && regionMatch) filteredMovies.add(m);
        }
        adapter.notifyDataSetChanged();
        tvCount.setText("共 " + filteredMovies.size() + " 部影片");
    }

    interface OnChipSelected {
        void onSelected(String value);
    }
}
