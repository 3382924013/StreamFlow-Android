import { useNavigate } from 'react-router-dom';
import { useStore } from '@/store/useStore';
import {
  ChevronRight,
  Clock,
  Heart,
  Download,
  Settings,
  Crown,
  LogOut,
  Film,
} from 'lucide-react';

const menuItems = [
  { icon: Clock, label: '观看历史', path: '/profile/history' },
  { icon: Heart, label: '我的收藏', path: '/profile/favorites' },
  { icon: Download, label: '我的下载', path: '/profile/downloads' },
  { icon: Settings, label: '设置', path: '/profile/settings' },
];

export default function Profile() {
  const navigate = useNavigate();
  const { user, logout, showToast } = useStore();

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      <header className="fixed top-0 left-0 right-0 z-40 bg-white/90 backdrop-blur-lg border-b border-gray-50">
        <div className="max-w-[430px] mx-auto flex items-center justify-between h-14 px-4">
          <h1 className="text-lg font-bold text-charcoal font-display">我的</h1>
          <button
            onClick={() => {
              logout();
              showToast('已退出登录', 'info');
            }}
            className="p-2 rounded-full hover:bg-gray-100 transition-colors"
          >
            <LogOut className="w-5 h-5 text-gray-500" />
          </button>
        </div>
      </header>

      <main className="max-w-[430px] mx-auto pt-14">
        <div className="bg-white p-6">
          <div className="flex items-center gap-4">
            <div className="relative">
              <img
                src={user?.avatar || 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200&h=200&fit=crop&crop=face'}
                alt="avatar"
                className={`w-20 h-20 rounded-full object-cover ${user?.isVip ? 'ring-3 ring-gold' : ''}`}
              />
              {user?.isVip && (
                <div className="absolute -bottom-1 -right-1 w-7 h-7 bg-gold rounded-full flex items-center justify-center border-2 border-white">
                  <Crown className="w-4 h-4 text-white" />
                </div>
              )}
            </div>
            <div className="flex-1">
              <h2 className="text-lg font-bold text-charcoal">
                {user?.nickname || '游客'}
              </h2>
              {user?.isVip ? (
                <div className="flex items-center gap-1 mt-1">
                  <Crown className="w-3.5 h-3.5 text-gold" />
                  <span className="text-xs text-gold font-medium">
                    VIP会员 · 到期时间 {user.vipExpireDate}
                  </span>
                </div>
              ) : (
                <button
                  onClick={() => navigate('/vip')}
                  className="mt-1 px-3 py-1 bg-gold/10 rounded-full text-xs text-gold font-medium"
                >
                  开通VIP会员
                </button>
              )}
            </div>
          </div>

          <div className="grid grid-cols-3 gap-4 mt-6">
            <div className="text-center">
              <p className="text-xl font-bold text-charcoal">{user?.watchHistory.length || 0}</p>
              <p className="text-xs text-gray-400 mt-0.5">观看历史</p>
            </div>
            <div className="text-center">
              <p className="text-xl font-bold text-charcoal">{user?.favorites.length || 0}</p>
              <p className="text-xs text-gray-400 mt-0.5">我的收藏</p>
            </div>
            <div className="text-center">
              <p className="text-xl font-bold text-charcoal">{user?.downloads.length || 0}</p>
              <p className="text-xs text-gray-400 mt-0.5">我的下载</p>
            </div>
          </div>
        </div>

        <div className="mt-3 bg-white rounded-2xl overflow-hidden">
          {menuItems.map((item, index) => (
            <button
              key={item.label}
              onClick={() => {
                if (item.label === '设置') {
                  showToast('设置功能演示', 'info');
                  return;
                }
                showToast(`${item.label}功能演示`, 'info');
              }}
              className={`w-full flex items-center gap-3 px-4 py-4 hover:bg-gray-50 transition-colors ${
                index < menuItems.length - 1 ? 'border-b border-gray-50' : ''
              }`}
            >
              <div className="w-9 h-9 bg-gray-100 rounded-xl flex items-center justify-center">
                <item.icon className="w-5 h-5 text-gray-600" />
              </div>
              <span className="flex-1 text-left text-sm font-medium text-charcoal">
                {item.label}
              </span>
              <ChevronRight className="w-4 h-4 text-gray-300" />
            </button>
          ))}
        </div>

        {user?.watchHistory && user.watchHistory.length > 0 && (
          <div className="mt-4 px-4">
            <div className="flex items-center justify-between mb-3">
              <h3 className="text-sm font-semibold text-charcoal">最近观看</h3>
              <button className="text-xs text-gray-400">查看全部</button>
            </div>
            <div className="flex gap-3 overflow-x-auto pb-2 -mx-4 px-4 scrollbar-hide">
              {user.watchHistory.map((movie) => (
                <div
                  key={movie.id}
                  className="w-[120px] flex-shrink-0 cursor-pointer"
                  onClick={() => navigate(`/player/${movie.id}`)}
                >
                  <div className="relative h-[160px] rounded-xl overflow-hidden mb-2">
                    <img
                      src={movie.cover}
                      alt={movie.title}
                      className="w-full h-full object-cover"
                      loading="lazy"
                    />
                    {movie.progress !== undefined && (
                      <div className="absolute bottom-0 left-0 right-0 h-1 bg-black/30">
                        <div
                          className="h-full bg-coral rounded-full"
                          style={{ width: `${movie.progress}%` }}
                        />
                      </div>
                    )}
                  </div>
                  <h4 className="text-xs font-medium text-charcoal truncate">{movie.title}</h4>
                </div>
              ))}
            </div>
          </div>
        )}

        <div className="mt-6 text-center pb-4">
          <div className="flex items-center justify-center gap-2 text-xs text-gray-300">
            <Film className="w-3 h-3" />
            <span>StreamFlow v1.0.0</span>
          </div>
        </div>
      </main>
    </div>
  );
}
