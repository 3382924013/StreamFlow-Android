package com.streamflow.ui.vip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.streamflow.R;
import com.streamflow.data.MockData;
import com.streamflow.model.User;
import com.streamflow.model.VipPlan;
import com.streamflow.utils.AppState;

import de.hdodenhof.circleimageview.CircleImageView;

public class VipFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User user = AppState.getInstance().getCurrentUser();

        CircleImageView avatar = view.findViewById(R.id.vip_avatar);
        Glide.with(this).load(user.getAvatar()).into(avatar);

        ((TextView) view.findViewById(R.id.vip_name)).setText(user.getNickname());

        TextView vipStatus = view.findViewById(R.id.vip_status);
        if (user.isVip()) {
            vipStatus.setText("VIP会员");
            vipStatus.setTextColor(getColor(R.color.gold));
            ((TextView) view.findViewById(R.id.vip_expire)).setText("到期时间 " + user.getVipExpireDate());
        } else {
            vipStatus.setText("未开通会员");
            vipStatus.setTextColor(getColor(R.color.gray_400));
            view.findViewById(R.id.vip_expire).setVisibility(View.GONE);
        }

        LinearLayout plansContainer = view.findViewById(R.id.plans_container);
        for (VipPlan plan : MockData.getVipPlans()) {
            View planView = createPlanView(plan);
            plansContainer.addView(planView);
        }

        view.findViewById(R.id.btn_subscribe).setOnClickListener(v ->
            Toast.makeText(requireContext(), "支付功能演示", Toast.LENGTH_SHORT).show());
    }

    private View createPlanView(VipPlan plan) {
        CardView card = new CardView(requireContext());
        int margin = (int) (8 * getResources().getDisplayMetrics().density);
        CardView.LayoutParams params = new CardView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, margin, 0, margin);
        card.setLayoutParams(params);
        card.setRadius(16 * getResources().getDisplayMetrics().density);
        card.setCardElevation(0);
        card.setContentPadding(24, 24, 24, 24);

        if (plan.isRecommended()) {
            card.setStrokeWidth(2);
            card.setStrokeColor(getColor(R.color.gold));
            card.setCardBackgroundColor(getColor(R.color.vip_badge_bg));
        } else {
            card.setCardBackgroundColor(getColor(R.color.white));
        }

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout header = new LinearLayout(requireContext());
        header.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout nameSection = new LinearLayout(requireContext());
        nameSection.setOrientation(LinearLayout.VERTICAL);
        nameSection.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        TextView name = new TextView(requireContext());
        name.setText(plan.getName());
        name.setTextColor(getColor(R.color.charcoal));
        name.setTextSize(16);
        name.setTextStyle(android.graphics.Typeface.BOLD);
        nameSection.addView(name);

        TextView duration = new TextView(requireContext());
        duration.setText(plan.getDuration());
        duration.setTextColor(getColor(R.color.gray_400));
        duration.setTextSize(12);
        nameSection.addView(duration);

        header.addView(nameSection);

        LinearLayout priceSection = new LinearLayout(requireContext());
        priceSection.setOrientation(LinearLayout.HORIZONTAL);
        priceSection.setGravity(android.view.Gravity.CENTER_VERTICAL);

        TextView price = new TextView(requireContext());
        price.setText("¥" + plan.getPrice());
        price.setTextColor(getColor(R.color.coral));
        price.setTextSize(24);
        price.setTextStyle(android.graphics.Typeface.BOLD);
        priceSection.addView(price);

        TextView original = new TextView(requireContext());
        original.setText("¥" + plan.getOriginalPrice());
        original.setTextColor(getColor(R.color.gray_400));
        original.setTextSize(14);
        original.setPaintFlags(original.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        original.setPadding(8, 0, 0, 0);
        priceSection.addView(original);

        header.addView(priceSection);
        layout.addView(header);

        if (plan.isRecommended()) {
            TextView badge = new TextView(requireContext());
            badge.setText("推荐");
            badge.setTextColor(getColor(R.color.white));
            badge.setTextSize(10);
            badge.setTextStyle(android.graphics.Typeface.BOLD);
            badge.setBackgroundResource(R.drawable.bg_gold_rounded);
            badge.setPadding(16, 4, 16, 4);
            LinearLayout.LayoutParams badgeParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            badgeParams.setMargins(0, 8, 0, 0);
            badge.setLayoutParams(badgeParams);
            layout.addView(badge);
        }

        for (String feature : plan.getFeatures()) {
            TextView featureView = new TextView(requireContext());
            featureView.setText("✓ " + feature);
            featureView.setTextColor(getColor(R.color.gray_600));
            featureView.setTextSize(12);
            featureView.setPadding(0, 8, 0, 0);
            layout.addView(featureView);
        }

        card.addView(layout);
        return card;
    }

    private int getColor(int resId) {
        return requireContext().getColor(resId);
    }
}
