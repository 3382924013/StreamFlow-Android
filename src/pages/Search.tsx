import { useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useStore } from '@/store/useStore';
import { movies } from '@/data/mockData';
import { Search, X, Clock, TrendingUp, Crown, Star } from 'lucide-react';

const hotSearches = ['星际穿越', '千与千寻', '盗梦空间', '肖申克的救赎', '让子弹飞'];

export default function SearchPage() {
  const navigate = useNavigate();
  const [query, setQuery] = useState('');
  const { searchHistory, addSearchHistory, clearSearchHistory } = useStore();

  const results = useMemo(() => {
    if (!query.trim()) return [];
    return movies.filter(
      (m) =>
        m.title.toLowerCase().includes(query.toLowerCase()) ||
        m.genre.some((g) => g.includes(query))
    );
  }, [query]);

  const handleSearch = (q: string) => {
    setQuery(q);
    if (q.trim()) {
      addSearchHistory(q);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      <header className="fixed top-0 left-0 right-0 z-40 bg-white/90 backdrop-blur-lg border-b border-gray-50">
        <div className="max-w-[430px] mx-auto flex items-center gap-3 h-14 px-4">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
            <input
              type="text"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="搜索影片、演员、导演..."
              className="w-full pl-9 pr-9 py-2.5 bg-gray-100 rounded-2xl text-sm focus:outline-none focus:ring-2 focus:ring-coral/30"
              autoFocus
            />
            {query && (
              <button
                onClick={() => setQuery('')}
                className="absolute right-3 top-1/2 -translate-y-1/2"
              >
                <X className="w-4 h-4 text-gray-400" />
              </button>
            )}
          </div>
          <button onClick={() => navigate(-1)} className="text-sm text-gray-500">
            取消
          </button>
        </div>
      </header>

      <main className="max-w-[430px] mx-auto pt-14 px-4">
        {query ? (
          <div className="py-4">
            <p className="text-xs text-gray-400 mb-3">
              找到 {results.length} 个结果
            </p>
            {results.length > 0 ? (
              <div className="grid grid-cols-2 gap-3">
                {results.map((movie) => (
                  <div
                    key={movie.id}
                    className="cursor-pointer group"
                    onClick={() => navigate(`/player/${movie.id}`)}
                  >
                    <div className="relative h-[220px] rounded-xl overflow-hidden mb-2">
                      <img
                        src={movie.cover}
                        alt={movie.title}
                        className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
                        loading="lazy"
                      />
                      {movie.isVip && (
                        <div className="absolute top-2 right-2 flex items-center gap-0.5 px-1.5 py-0.5 bg-gold/90 rounded-md">
                          <Crown className="w-3 h-3 text-white" />
                          <span className="text-[10px] font-bold text-white">VIP</span>
                        </div>
                      )}
                    </div>
                    <h3 className="text-sm font-semibold text-charcoal truncate">{movie.title}</h3>
                    <div className="flex items-center gap-1 mt-0.5">
                      <Star className="w-3 h-3 text-gold fill-gold" />
                      <span className="text-xs text-gray-500">{movie.rating}</span>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <div className="flex flex-col items-center justify-center py-20">
                <Search className="w-12 h-12 text-gray-200 mb-3" />
                <p className="text-sm text-gray-400">未找到相关影片</p>
              </div>
            )}
          </div>
        ) : (
          <div className="py-4 space-y-6">
            {searchHistory.length > 0 && (
              <div>
                <div className="flex items-center justify-between mb-3">
                  <div className="flex items-center gap-2">
                    <Clock className="w-4 h-4 text-gray-400" />
                    <h3 className="text-sm font-semibold text-charcoal">搜索历史</h3>
                  </div>
                  <button
                    onClick={clearSearchHistory}
                    className="text-xs text-gray-400 hover:text-coral"
                  >
                    清空
                  </button>
                </div>
                <div className="flex flex-wrap gap-2">
                  {searchHistory.map((h) => (
                    <button
                      key={h}
                      onClick={() => handleSearch(h)}
                      className="px-3 py-1.5 bg-white rounded-lg text-xs text-gray-600 hover:bg-coral/10 hover:text-coral transition-colors"
                    >
                      {h}
                    </button>
                  ))}
                </div>
              </div>
            )}

            <div>
              <div className="flex items-center gap-2 mb-3">
                <TrendingUp className="w-4 h-4 text-coral" />
                <h3 className="text-sm font-semibold text-charcoal">热门搜索</h3>
              </div>
              <div className="flex flex-wrap gap-2">
                {hotSearches.map((h, i) => (
                  <button
                    key={h}
                    onClick={() => handleSearch(h)}
                    className="px-3 py-1.5 bg-white rounded-lg text-xs text-gray-600 hover:bg-coral/10 hover:text-coral transition-colors"
                  >
                    <span className={`mr-1 ${i < 3 ? 'text-coral font-bold' : 'text-gray-400'}`}>
                      {i + 1}
                    </span>
                    {h}
                  </button>
                ))}
              </div>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}
