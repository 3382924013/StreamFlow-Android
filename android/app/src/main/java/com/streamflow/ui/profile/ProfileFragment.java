package com.streamflow.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.data.MockData;
import com.streamflow.model.Movie;
import com.streamflow.model.User;
import com.streamflow.ui.player.PlayerActivity;
import com.streamflow.ui.vip.VipFragment;
import com.streamflow.utils.AppState;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = AppState.getInstance().getCurrentUser();

        ImageView avatar = view.findViewById(R.id.profile_avatar);
        Glide.with(this).load(user.getAvatar()).circleCrop().into(avatar);

        ((TextView) view.findViewById(R.id.profile_name)).setText(user.getNickname());

        TextView vipStatus = view.findViewById(R.id.profile_vip_status);
        if (user.isVip()) {
            vipStatus.setText("VIP会员 · 到期时间 " + user.getVipExpireDate());
            vipStatus.setTextColor(getColor(R.color.gold));
        } else {
            vipStatus.setText("未开通会员");
            vipStatus.setTextColor(getColor(R.color.gray_400));
        }

        ((TextView) view.findViewById(R.id.stat_history)).setText(String.valueOf(user.getWatchHistory().size()));
        ((TextView) view.findViewById(R.id.stat_favorites)).setText(String.valueOf(user.getFavorites().size()));
        ((TextView) view.findViewById(R.id.stat_downloads)).setText(String.valueOf(user.getDownloads().size()));

        view.findViewById(R.id.btn_history).setOnClickListener(v ->
            Toast.makeText(requireContext(), "观看历史功能演示", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.btn_favorites).setOnClickListener(v ->
            Toast.makeText(requireContext(), "我的收藏功能演示", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.btn_downloads).setOnClickListener(v ->
            Toast.makeText(requireContext(), "我的下载功能演示", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.btn_settings).setOnClickListener(v ->
            Toast.makeText(requireContext(), "设置功能演示", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.btn_logout).setOnClickListener(v -> {
            AppState.getInstance().setLoggedIn(false);
            Toast.makeText(requireContext(), "已退出登录", Toast.LENGTH_SHORT).show();
        });

        RecyclerView recyclerHistory = view.findViewById(R.id.recycler_recent_history);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerHistory.setAdapter(new RecentAdapter(user.getWatchHistory(), movie -> {
            Intent intent = new Intent(requireContext(), PlayerActivity.class);
            intent.putExtra("movie_id", movie.getId());
            startActivity(intent);
        }));
    }

    private int getColor(int resId) {
        return requireContext().getColor(resId);
    }
}
