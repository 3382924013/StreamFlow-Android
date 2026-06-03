import { useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { movies, genres, years, regions } from '@/data/mockData';
import { Crown, Star, SlidersHorizontal, Loader2 } from 'lucide-react';

export default function Discover() {
  const navigate = useNavigate();
  const [selectedGenre, setSelectedGenre] = useState('全部');
  const [selectedYear, setSelectedYear] = useState('全部');
  const [selectedRegion, setSelectedRegion] = useState('全部');
  const [showFilters, setShowFilters] = useState(false);
  const [loading, setLoading] = useState(false);
  const [displayCount, setDisplayCount] = useState(8);

  const filteredMovies = useMemo(() => {
    return movies.filter((movie) => {
      const genreMatch = selectedGenre === '全部' || movie.genre.includes(selectedGenre);
      const yearMatch =
        selectedYear === '全部' ||
        (selectedYear === '2000s'
          ? movie.year >= 2000 && movie.year < 2010
          : selectedYear === '1990s'
          ? movie.year >= 1990 && movie.year < 2000
          : movie.year === parseInt(selectedYear));
      const regionMatch = selectedRegion === '全部' || movie.region === selectedRegion;
      return genreMatch && yearMatch && regionMatch;
    });
  }, [selectedGenre, selectedYear, selectedRegion]);

  const displayedMovies = filteredMovies.slice(0, displayCount);
  const hasMore = displayCount < filteredMovies.length;

  const loadMore = () => {
    setLoading(true);
    setTimeout(() => {
      setDisplayCount((prev) => prev + 4);
      setLoading(false);
    }, 500);
  };

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      <header className="fixed top-0 left-0 right-0 z-40 bg-white/90 backdrop-blur-lg border-b border-gray-50">
        <div className="max-w-[430px] mx-auto flex items-center justify-between h-14 px-4">
          <h1 className="text-lg font-bold text-charcoal font-display">发现</h1>
          <button
            onClick={() => setShowFilters(!showFilters)}
            className={`p-2 rounded-full transition-colors ${showFilters ? 'bg-coral/10 text-coral' : 'hover:bg-gray-100'}`}
          >
            <SlidersHorizontal className="w-5 h-5" />
          </button>
        </div>
      </header>

      <main className="max-w-[430px] mx-auto pt-14">
        {showFilters && (
          <div className="bg-white border-b border-gray-100 p-4 space-y-3 animate-fade-in">
            <div>
              <p className="text-xs font-medium text-gray-500 mb-2">类型</p>
              <div className="flex flex-wrap gap-2">
                {genres.map((g) => (
                  <button
                    key={g}
                    onClick={() => setSelectedGenre(g)}
                    className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors ${
                      selectedGenre === g
                        ? 'bg-coral text-white'
                        : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                    }`}
                  >
                    {g}
                  </button>
                ))}
              </div>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-500 mb-2">年份</p>
              <div className="flex flex-wrap gap-2">
                {years.map((y) => (
                  <button
                    key={y}
                    onClick={() => setSelectedYear(y)}
                    className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors ${
                      selectedYear === y
                        ? 'bg-coral text-white'
                        : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                    }`}
                  >
                    {y}
                  </button>
                ))}
              </div>
            </div>
            <div>
              <p className="text-xs font-medium text-gray-500 mb-2">地区</p>
              <div className="flex flex-wrap gap-2">
                {regions.map((r) => (
                  <button
                    key={r}
                    onClick={() => setSelectedRegion(r)}
                    className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors ${
                      selectedRegion === r
                        ? 'bg-coral text-white'
                        : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
                    }`}
                  >
                    {r}
                  </button>
                ))}
              </div>
            </div>
          </div>
        )}

        <div className="p-4">
          <p className="text-xs text-gray-400 mb-3">
            共 {filteredMovies.length} 部影片
          </p>
          <div className="grid grid-cols-2 gap-3">
            {displayedMovies.map((movie) => (
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
                  <div className="absolute bottom-2 right-2 px-1.5 py-0.5 bg-black/60 rounded text-[10px] text-white">
                    {movie.duration}
                  </div>
                </div>
                <h3 className="text-sm font-semibold text-charcoal truncate">{movie.title}</h3>
                <div className="flex items-center gap-1 mt-0.5">
                  <Star className="w-3 h-3 text-gold fill-gold" />
                  <span className="text-xs text-gray-500">{movie.rating}</span>
                  <span className="text-xs text-gray-400 ml-1">{movie.year}</span>
                </div>
              </div>
            ))}
          </div>

          {hasMore && (
            <button
              onClick={loadMore}
              disabled={loading}
              className="w-full mt-4 py-3 bg-white rounded-xl text-sm text-gray-500 hover:bg-gray-50 transition-colors flex items-center justify-center gap-2"
            >
              {loading ? (
                <>
                  <Loader2 className="w-4 h-4 animate-spin" />
                  加载中...
                </>
              ) : (
                '加载更多'
              )}
            </button>
          )}

          {!hasMore && displayedMovies.length > 0 && (
            <p className="text-center text-xs text-gray-300 mt-4">已经到底了</p>
          )}

          {displayedMovies.length === 0 && (
            <div className="flex flex-col items-center justify-center py-20">
              <p className="text-sm text-gray-400">暂无符合条件的影片</p>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}
