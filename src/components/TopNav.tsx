import { useNavigate } from 'react-router-dom';
import { Search, Crown } from 'lucide-react';
import { useStore } from '@/store/useStore';

export default function TopNav() {
  const navigate = useNavigate();
  const user = useStore((state) => state.user);

  return (
    <header className="fixed top-0 left-0 right-0 z-40 bg-white/90 backdrop-blur-lg border-b border-gray-50">
      <div className="max-w-[430px] mx-auto flex items-center justify-between h-14 px-4">
        <div className="flex items-center gap-1.5">
          <div className="w-8 h-8 bg-coral rounded-lg flex items-center justify-center">
            <span className="text-white font-bold text-sm font-display">S</span>
          </div>
          <span className="font-display font-bold text-lg text-charcoal">StreamFlow</span>
        </div>

        <div className="flex items-center gap-3">
          <button
            onClick={() => navigate('/search')}
            className="p-2 rounded-full hover:bg-gray-100 transition-colors"
          >
            <Search className="w-5 h-5 text-gray-600" />
          </button>
          <button
            onClick={() => navigate('/vip')}
            className="flex items-center gap-1 px-3 py-1.5 bg-gold/10 rounded-full"
          >
            <Crown className="w-3.5 h-3.5 text-gold" />
            <span className="text-xs font-semibold text-gold-dark">VIP</span>
          </button>
          <button
            onClick={() => navigate('/profile')}
            className="relative"
          >
            <img
              src={user?.avatar || 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100&h=100&fit=crop&crop=face'}
              alt="avatar"
              className={`w-8 h-8 rounded-full object-cover ${user?.isVip ? 'ring-2 ring-gold' : ''}`}
            />
          </button>
        </div>
      </div>
    </header>
  );
}
