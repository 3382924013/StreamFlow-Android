import { useStore } from '@/store/useStore';
import { X, User } from 'lucide-react';

export default function LoginModal() {
  const { showLoginModal, setShowLoginModal, login, showToast } = useStore();

  if (!showLoginModal) return null;

  return (
    <div className="fixed inset-0 z-[90] flex items-center justify-center animate-fade-in">
      <div className="absolute inset-0 bg-black/50" onClick={() => setShowLoginModal(false)} />
      <div className="relative w-full max-w-sm bg-white rounded-3xl p-6 mx-4 animate-slide-up">
        <button
          onClick={() => setShowLoginModal(false)}
          className="absolute top-4 right-4 p-2 rounded-full hover:bg-gray-100 transition-colors"
        >
          <X className="w-5 h-5 text-gray-500" />
        </button>

        <div className="flex flex-col items-center mb-6">
          <div className="w-16 h-16 bg-coral/10 rounded-full flex items-center justify-center mb-3">
            <User className="w-8 h-8 text-coral" />
          </div>
          <h2 className="text-xl font-bold text-charcoal font-display">登录账号</h2>
          <p className="text-sm text-gray-500 mt-1">登录后即可收藏影片和发表评论</p>
        </div>

        <div className="space-y-3 mb-6">
          <input
            type="text"
            placeholder="请输入手机号"
            className="w-full px-4 py-3 bg-gray-50 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-coral/30"
          />
          <input
            type="password"
            placeholder="请输入验证码"
            className="w-full px-4 py-3 bg-gray-50 rounded-xl text-sm focus:outline-none focus:ring-2 focus:ring-coral/30"
          />
        </div>

        <button
          onClick={() => {
            login();
            showToast('登录成功', 'success');
            setShowLoginModal(false);
          }}
          className="w-full py-3.5 bg-coral text-white font-semibold rounded-2xl hover:bg-coral-dark active:scale-[0.98] transition-all"
        >
          立即登录
        </button>

        <p className="text-center text-xs text-gray-400 mt-4">
          登录即代表同意《用户协议》和《隐私政策》
        </p>
      </div>
    </div>
  );
}
