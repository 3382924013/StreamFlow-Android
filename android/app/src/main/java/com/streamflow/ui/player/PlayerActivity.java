package com.streamflow.ui.player;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.data.MockData;
import com.streamflow.model.Comment;
import com.streamflow.model.Movie;
import com.streamflow.utils.AppState;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private Movie movie;
    private boolean isPlaying = false;
    private int currentTime = 0;
    private int duration = 7200;
    private Handler playHandler;
    private Runnable playRunnable;
    private String currentQuality = "1080P";
    private float currentSpeed = 1.0f;
    private List<Comment> comments = new ArrayList<>();
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        String movieId = getIntent().getStringExtra("movie_id");
        for (Movie m : MockData.getMovies()) {
            if (m.getId().equals(movieId)) {
                movie = m;
                break;
            }
        }
        if (movie == null) {
            Toast.makeText(this, "影片不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupVideoPlayer();
        setupMovieInfo();
        setupComments();
        setupActions();
    }

    private void setupVideoPlayer() {
        ImageView videoCover = findViewById(R.id.video_cover);
        Glide.with(this).load(movie.getCover()).centerCrop().into(videoCover);

        View playOverlay = findViewById(R.id.play_overlay);
        ImageView btnPlayPause = findViewById(R.id.btn_play_pause);
        SeekBar seekBar = findViewById(R.id.seek_bar);
        TextView tvCurrentTime = findViewById(R.id.tv_current_time);
        TextView tvDuration = findViewById(R.id.tv_duration);

        seekBar.setMax(duration);
        tvDuration.setText(formatTime(duration));

        playHandler = new Handler(Looper.getMainLooper());
        playRunnable = () -> {
            if (isPlaying) {
                currentTime += currentSpeed;
                if (currentTime >= duration) currentTime = 0;
                seekBar.setProgress(currentTime);
                tvCurrentTime.setText(formatTime(currentTime));
            }
            playHandler.postDelayed(playRunnable, 1000);
        };
        playHandler.postDelayed(playRunnable, 1000);

        playOverlay.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            btnPlayPause.setImageResource(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play_circle);
        });

        btnPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            btnPlayPause.setImageResource(isPlaying ? R.drawable.ic_pause : R.drawable.ic_play_circle);
        });

        findViewById(R.id.btn_back_player).setOnClickListener(v -> finish());

        findViewById(R.id.btn_quality).setOnClickListener(v -> showQualityDialog());
        findViewById(R.id.btn_speed).setOnClickListener(v -> showSpeedDialog());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) currentTime = progress;
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void showQualityDialog() {
        String[] qualities = {"480P", "720P", "1080P", "4K"};
        new AlertDialog.Builder(this)
            .setTitle("选择清晰度")
            .setItems(qualities, (dialog, which) -> {
                if (qualities[which].equals("4K") && !AppState.getInstance().getCurrentUser().isVip()) {
                    showVipDialog();
                    return;
                }
                currentQuality = qualities[which];
                ((TextView) findViewById(R.id.btn_quality)).setText(currentQuality);
                Toast.makeText(this, "已切换至 " + currentQuality, Toast.LENGTH_SHORT).show();
            })
            .show();
    }

    private void showSpeedDialog() {
        String[] speeds = {"0.5x", "0.75x", "1.0x", "1.25x", "1.5x", "2.0x"};
        float[] speedValues = {0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f};
        new AlertDialog.Builder(this)
            .setTitle("播放速度")
            .setItems(speeds, (dialog, which) -> {
                currentSpeed = speedValues[which];
                ((TextView) findViewById(R.id.btn_speed)).setText(speeds[which]);
            })
            .show();
    }

    private void showVipDialog() {
        new AlertDialog.Builder(this)
            .setTitle("升级VIP会员")
            .setMessage("解锁4K超清画质，享受极致观影体验")
            .setPositiveButton("立即开通", (dialog, which) -> {
                Toast.makeText(this, "支付功能演示", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("取消", null)
            .show();
    }

    private void setupMovieInfo() {
        ((TextView) findViewById(R.id.player_title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.player_meta)).setText(movie.getYear() + " | " + movie.getRegion() + " | " + movie.getDuration());
        ((TextView) findViewById(R.id.player_desc)).setText(movie.getDescription());

        LinearLayout genreContainer = findViewById(R.id.genre_container);
        for (String g : movie.getGenre()) {
            TextView chip = new TextView(this);
            chip.setText(g);
            chip.setTextSize(11);
            chip.setTextColor(getColor(R.color.gray_600));
            chip.setBackgroundResource(R.drawable.bg_chip);
            chip.setPadding(16, 6, 16, 6);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 12, 0);
            chip.setLayoutParams(params);
            genreContainer.addView(chip);
        }

        findViewById(R.id.vip_badge_player).setVisibility(movie.isVip() ? View.VISIBLE : View.GONE);
    }

    private void setupActions() {
        ImageView btnFavorite = findViewById(R.id.btn_favorite);
        updateFavoriteIcon(btnFavorite);
        btnFavorite.setOnClickListener(v -> {
            if (!AppState.getInstance().isLoggedIn()) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                return;
            }
            AppState.getInstance().toggleFavorite(movie.getId());
            updateFavoriteIcon(btnFavorite);
            Toast.makeText(this, AppState.getInstance().isFavorite(movie.getId()) ? "收藏成功" : "已取消收藏", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_share).setOnClickListener(v ->
            Toast.makeText(this, "分享功能演示", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btn_download).setOnClickListener(v ->
            Toast.makeText(this, "下载功能演示", Toast.LENGTH_SHORT).show());
    }

    private void updateFavoriteIcon(ImageView btnFavorite) {
        boolean isFav = AppState.getInstance().isFavorite(movie.getId());
        btnFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart);
        btnFavorite.setColorFilter(getColor(isFav ? R.color.coral : R.color.gray_600));
    }

    private void setupComments() {
        comments.addAll(MockData.getComments());
        RecyclerView recyclerComments = findViewById(R.id.recycler_comments);
        recyclerComments.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(comments, comment -> {
            if (!AppState.getInstance().isLoggedIn()) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                return;
            }
            comment.setLiked(!comment.isLiked());
            comment.setLikes(comment.isLiked() ? comment.getLikes() + 1 : comment.getLikes() - 1);
            commentAdapter.notifyDataSetChanged();
        });
        recyclerComments.setAdapter(commentAdapter);

        findViewById(R.id.btn_write_comment).setOnClickListener(v ->
            Toast.makeText(this, "评论功能演示", Toast.LENGTH_SHORT).show());
    }

    private String formatTime(int seconds) {
        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        int s = seconds % 60;
        if (h > 0) return String.format("%d:%02d:%02d", h, m, s);
        return String.format("%d:%02d", m, s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playHandler != null) playHandler.removeCallbacks(playRunnable);
    }
}
