import { useLocation, useNavigate } from 'react-router-dom';
import { Home, Compass, Crown, User } from 'lucide-react';

const tabs = [
  { path: '/home', label: '首页', icon: Home },
  { path: '/discover', label: '发现', icon: Compass },
  { path: '/vip', label: 'VIP', icon: Crown },
  { path: '/profile', label: '我的', icon: User },
];

export default function BottomNav() {
  const location = useLocation();
  const navigate = useNavigate();

  return (
    <nav className="fixed bottom-0 left-0 right-0 z-50 bg-white/90 backdrop-blur-lg border-t border-gray-100">
      <div className="max-w-[430px] mx-auto flex items-center justify-around h-16">
        {tabs.map((tab) => {
          const isActive = location.pathname === tab.path;
          const Icon = tab.icon;
          return (
            <button
              key={tab.path}
              onClick={() => navigate(tab.path)}
              className={`flex flex-col items-center gap-1 px-4 py-1 transition-all ${
                isActive ? 'text-coral' : 'text-gray-400'
              }`}
            >
              <Icon className={`w-5 h-5 transition-transform ${isActive ? 'scale-110' : ''}`} />
              <span className={`text-[10px] font-medium ${isActive ? 'font-semibold' : ''}`}>
                {tab.label}
              </span>
            </button>
          );
        })}
      </div>
    </nav>
  );
}
