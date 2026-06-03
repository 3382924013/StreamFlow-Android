import { useStore } from '@/store/useStore';
import { X, Crown, Check } from 'lucide-react';
import { vipPlans } from '@/data/mockData';

export default function VipModal() {
  const { showVipModal, setShowVipModal, showToast } = useStore();

  if (!showVipModal) return null;

  return (
    <div className="fixed inset-0 z-[90] flex items-end justify-center animate-fade-in">
      <div className="absolute inset-0 bg-black/50" onClick={() => setShowVipModal(false)} />
      <div className="relative w-full max-w-md bg-white rounded-t-3xl p-6 animate-slide-up">
        <button
          onClick={() => setShowVipModal(false)}
          className="absolute top-4 right-4 p-2 rounded-full hover:bg-gray-100 transition-colors"
        >
          <X className="w-5 h-5 text-gray-500" />
        </button>

        <div className="flex items-center gap-2 mb-2">
          <Crown className="w-6 h-6 text-gold" />
          <h2 className="text-xl font-bold text-charcoal font-display">升级VIP会员</h2>
        </div>
        <p className="text-sm text-gray-500 mb-6">解锁4K超清画质，享受极致观影体验</p>

        <div className="space-y-3 mb-6">
          {vipPlans.map((plan) => (
            <div
              key={plan.id}
              className={`relative p-4 rounded-2xl border-2 transition-all cursor-pointer ${
                plan.recommended
                  ? 'border-gold bg-gold/5'
                  : 'border-gray-100 hover:border-gray-200'
              }`}
              onClick={() => {
                showToast(`已选择${plan.name}`, 'success');
                setShowVipModal(false);
              }}
            >
              {plan.recommended && (
                <div className="absolute -top-2 left-4 px-2 py-0.5 bg-gold text-white text-xs font-bold rounded-full">
                  推荐
                </div>
              )}
              <div className="flex justify-between items-start mb-2">
                <div>
                  <h3 className="font-semibold text-charcoal">{plan.name}</h3>
                  <p className="text-xs text-gray-400">{plan.duration}</p>
                </div>
                <div className="text-right">
                  <span className="text-2xl font-bold text-coral">¥{plan.price}</span>
                  <span className="text-sm text-gray-400 line-through ml-1">¥{plan.originalPrice}</span>
                </div>
              </div>
              <div className="flex flex-wrap gap-1.5">
                {plan.features.map((feature) => (
                  <span key={feature} className="flex items-center gap-1 text-xs text-gray-500">
                    <Check className="w-3 h-3 text-mint" />
                    {feature}
                  </span>
                ))}
              </div>
            </div>
          ))}
        </div>

        <button
          onClick={() => {
            showToast('支付功能演示', 'info');
            setShowVipModal(false);
          }}
          className="w-full py-3.5 bg-coral text-white font-semibold rounded-2xl hover:bg-coral-dark active:scale-[0.98] transition-all"
        >
          立即开通
        </button>
      </div>
    </div>
  );
}
