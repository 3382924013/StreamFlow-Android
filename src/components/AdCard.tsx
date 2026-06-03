import { useStore } from '@/store/useStore';
import { Sparkles } from 'lucide-react';

export default function AdCard() {
  const showToast = useStore((state) => state.showToast);

  return (
    <div
      className="w-[140px] flex-shrink-0 cursor-pointer"
      onClick={() => showToast('广告功能演示', 'info')}
    >
      <div className="relative h-[200px] rounded-xl overflow-hidden mb-2 bg-blue-50">
        <div className="absolute inset-0 flex flex-col items-center justify-center p-4">
          <Sparkles className="w-10 h-10 text-blue-400 mb-2" />
          <p className="text-sm font-semibold text-blue-600 text-center">新品推荐</p>
          <p className="text-xs text-blue-400 text-center mt-1">限时优惠 5折起</p>
        </div>
        <div className="absolute top-2 left-2 px-1.5 py-0.5 bg-blue-400/80 rounded text-[10px] text-white font-medium">
          广告
        </div>
      </div>
      <h3 className="text-sm font-semibold text-charcoal truncate">品牌特惠</h3>
      <p className="text-xs text-gray-400 mt-0.5">点击查看详情</p>
    </div>
  );
}
