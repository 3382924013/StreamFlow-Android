package com.streamflow.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.data.MockData;
import com.streamflow.model.Movie;
import com.streamflow.ui.player.PlayerActivity;
import com.streamflow.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 heroPager;
    private Handler heroHandler;
    private Runnable heroRunnable;
    private LinearLayout heroIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_search).setOnClickListener(v ->
            startActivity(new Intent(requireContext(), SearchActivity.class)));

        setupHeroCarousel(view);
        setupSection(view, R.id.recycler_hot, R.id.tv_hot_title, "热门推荐", MockData.getMovies().subList(0, 6), false);
        setupSection(view, R.id.recycler_continue, R.id.tv_continue_title, "继续观看", MockData.getCurrentUser().getWatchHistory(), true);
        setupCategorySection(view, R.id.recycler_action, "动作", filterByGenre("动作"));
        setupCategorySection(view, R.id.recycler_comedy, "喜剧", filterByGenre("喜剧"));
        setupCategorySection(view, R.id.recycler_suspense, "悬疑", filterByGenre("悬疑"));
        setupCategorySection(view, R.id.recycler_animation, "动漫", filterByGenre("动画"));
    }

    private List<Movie> filterByGenre(String genre) {
        List<Movie> result = new ArrayList<>();
        for (Movie m : MockData.getMovies()) {
            if (m.getGenre().contains(genre)) result.add(m);
        }
        return result;
    }

    private void setupHeroCarousel(View view) {
        heroPager = view.findViewById(R.id.hero_pager);
        heroIndicator = view.findViewById(R.id.hero_indicator);
        List<Movie> heroes = MockData.getHeroMovies();

        HeroAdapter adapter = new HeroAdapter(heroes, movie -> {
            Intent intent = new Intent(requireContext(), PlayerActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        });
        heroPager.setAdapter(adapter);

        for (int i = 0; i < heroes.size(); i++) {
            View dot = new View(requireContext());
            int size = (int) (6 * getResources().getDisplayMetrics().density);
            int margin = (int) (4 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(i == 0 ? size * 3 : size, size);
            params.setMargins(margin, 0, margin, 0);
            dot.setLayoutParams(params);
            dot.setBackgroundResource(i == 0 ? R.drawable.dot_active : R.drawable.dot_inactive);
            heroIndicator.addView(dot);
        }

        heroPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateIndicator(position);
            }
        });

        heroHandler = new Handler(Looper.getMainLooper());
        heroRunnable = () -> {
            int next = (heroPager.getCurrentItem() + 1) % heroes.size();
            heroPager.setCurrentItem(next, true);
            heroHandler.postDelayed(heroRunnable, 4000);
        };
        heroHandler.postDelayed(heroRunnable, 4000);
    }

    private void updateIndicator(int position) {
        for (int i = 0; i < heroIndicator.getChildCount(); i++) {
            View dot = heroIndicator.getChildAt(i);
            int size = (int) (6 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dot.getLayoutParams();
            params.width = i == position ? size * 3 : size;
            dot.setLayoutParams(params);
            dot.setBackgroundResource(i == position ? R.drawable.dot_active : R.drawable.dot_inactive);
        }
    }

    private void setupSection(View view, int recyclerId, int titleId, String title, List<Movie> movies, boolean showProgress) {
        TextView tvTitle = view.findViewById(titleId);
        if (tvTitle != null) tvTitle.setText(title);
        RecyclerView recycler = view.findViewById(recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new MovieAdapter(movies, showProgress, movie -> {
            Intent intent = new Intent(requireContext(), PlayerActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        }));
    }

    private void setupCategorySection(View view, int recyclerId, String title, List<Movie> movies) {
        RecyclerView recycler = view.findViewById(recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(new MovieAdapter(movies, false, movie -> {
            Intent intent = new Intent(requireContext(), PlayerActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (heroHandler != null) heroHandler.removeCallbacks(heroRunnable);
    }
}
