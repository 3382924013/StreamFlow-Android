import { useStore } from '@/store/useStore';
import { vipPlans } from '@/data/mockData';
import {
  Crown,
  Check,
  Sparkles,
  Monitor,
  Headphones,
  Zap,
  Gift,
} from 'lucide-react';

const benefits = [
  { icon: Zap, title: '免广告', desc: '全程无广告打扰' },
  { icon: Monitor, title: '4K超清', desc: '极致画质体验' },
  { icon: Sparkles, title: '抢先观看', desc: '最新影片优先看' },
  { icon: Headphones, title: '专属客服', desc: '7x24小时服务' },
  { icon: Gift, title: '限量周边', desc: '年度会员专享' },
];

export default function VipCenter() {
  const { user, showToast } = useStore();

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      <div className="bg-white">
        <div className="max-w-[430px] mx-auto px-4 pt-6 pb-8">
          <div className="flex items-center gap-2 mb-6">
            <Crown className="w-6 h-6 text-gold" />
            <h1 className="text-xl font-bold text-charcoal font-display">VIP会员中心</h1>
          </div>

          <div className="bg-charcoal rounded-2xl p-5 text-white">
            <div className="flex items-center gap-3 mb-4">
              <img
                src={user?.avatar || 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200&h=200&fit=crop&crop=face'}
                alt="avatar"
                className="w-14 h-14 rounded-full object-cover ring-2 ring-gold"
              />
              <div>
                <h2 className="font-semibold">{user?.nickname || '游客'}</h2>
                {user?.isVip ? (
                  <div className="flex items-center gap-1 mt-0.5">
                    <Crown className="w-3.5 h-3.5 text-gold" />
                    <span className="text-xs text-gold">VIP会员</span>
                  </div>
                ) : (
                  <span className="text-xs text-gray-400">未开通会员</span>
                )}
              </div>
            </div>
            {user?.isVip && user.vipExpireDate && (
              <div className="bg-white/10 rounded-xl p-3">
                <p className="text-xs text-gray-300">会员到期时间</p>
                <p className="text-lg font-bold text-gold mt-0.5">{user.vipExpireDate}</p>
              </div>
            )}
          </div>
        </div>
      </div>

      <div className="max-w-[430px] mx-auto px-4 py-6">
        <h2 className="text-sm font-semibold text-charcoal mb-3">会员权益</h2>
        <div className="grid grid-cols-3 gap-3 mb-6">
          {benefits.map((b) => (
            <div key={b.title} className="bg-white rounded-xl p-3 text-center">
              <div className="w-10 h-10 bg-gold/10 rounded-xl flex items-center justify-center mx-auto mb-2">
                <b.icon className="w-5 h-5 text-gold" />
              </div>
              <p className="text-xs font-semibold text-charcoal">{b.title}</p>
              <p className="text-[10px] text-gray-400 mt-0.5">{b.desc}</p>
            </div>
          ))}
        </div>

        <h2 className="text-sm font-semibold text-charcoal mb-3">选择套餐</h2>
        <div className="space-y-3">
          {vipPlans.map((plan) => (
            <div
              key={plan.id}
              className={`relative bg-white rounded-2xl p-4 border-2 transition-all ${
                plan.recommended
                  ? 'border-gold shadow-card'
                  : 'border-transparent shadow-card'
              }`}
            >
              {plan.recommended && (
                <div className="absolute -top-2 left-4 px-2 py-0.5 bg-gold text-white text-xs font-bold rounded-full">
                  推荐
                </div>
              )}
              <div className="flex items-start justify-between mb-3">
                <div>
                  <h3 className="font-semibold text-charcoal">{plan.name}</h3>
                  <p className="text-xs text-gray-400 mt-0.5">{plan.duration}</p>
                </div>
                <div className="text-right">
                  <span className="text-2xl font-bold text-coral">¥{plan.price}</span>
                  <span className="text-sm text-gray-400 line-through ml-1">
                    ¥{plan.originalPrice}
                  </span>
                </div>
              </div>
              <div className="space-y-1.5">
                {plan.features.map((f) => (
                  <div key={f} className="flex items-center gap-2">
                    <Check className="w-3.5 h-3.5 text-mint" />
                    <span className="text-xs text-gray-600">{f}</span>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>

        <button
          onClick={() => showToast('支付功能演示', 'info')}
          className="w-full mt-6 py-4 bg-coral text-white font-semibold rounded-2xl hover:bg-coral-dark active:scale-[0.98] transition-all shadow-lg shadow-coral/25"
        >
          立即开通
        </button>

        <p className="text-center text-xs text-gray-400 mt-4">
          开通即代表同意《会员服务协议》
        </p>
      </div>
    </div>
  );
}
