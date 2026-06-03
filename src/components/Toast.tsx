import { useStore } from '@/store/useStore';
import { CheckCircle, XCircle, Info } from 'lucide-react';

export default function Toast() {
  const toast = useStore((state) => state.toast);

  if (!toast) return null;

  const icons = {
    success: <CheckCircle className="w-5 h-5 text-mint" />,
    error: <XCircle className="w-5 h-5 text-coral" />,
    info: <Info className="w-5 h-5 text-blue-500" />,
  };

  const bgColors = {
    success: 'bg-white border-mint/30',
    error: 'bg-white border-coral/30',
    info: 'bg-white border-blue-200',
  };

  return (
    <div className="fixed bottom-24 left-0 right-0 z-[100] flex justify-center px-4 animate-slide-up">
      <div className={`flex items-center gap-3 px-5 py-3 rounded-2xl shadow-card border ${bgColors[toast.type]}`}>
        {icons[toast.type]}
        <span className="text-sm font-medium text-charcoal">{toast.message}</span>
      </div>
    </div>
  );
}
