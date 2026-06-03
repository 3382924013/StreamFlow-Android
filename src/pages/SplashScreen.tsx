import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Film } from 'lucide-react';

export default function SplashScreen() {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate('/home', { replace: true });
    }, 2500);
    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="fixed inset-0 z-[200] bg-white flex flex-col items-center justify-center">
      <div className="flex flex-col items-center">
        <div className="w-20 h-20 bg-coral rounded-2xl flex items-center justify-center mb-6 animate-fade-in">
          <Film className="w-10 h-10 text-white" />
        </div>
        <h1 className="text-2xl font-bold text-charcoal font-display mb-2 animate-fade-in">
          StreamFlow
        </h1>
        <p className="text-sm text-gray-400 mb-8 animate-fade-in">
          畅享极致观影体验
        </p>

        <div className="w-48 h-1 bg-gray-100 rounded-full overflow-hidden">
          <div className="h-full bg-coral rounded-full animate-[loading_2s_ease-in-out_forwards]" />
        </div>
      </div>

      <style>{`
        @keyframes loading {
          0% { width: 0%; }
          100% { width: 100%; }
        }
      `}</style>
    </div>
  );
}
