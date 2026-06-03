import TopNav from '@/components/TopNav';
import HeroCarousel from '@/components/HeroCarousel';
import MovieCard from '@/components/MovieCard';
import AdCard from '@/components/AdCard';
import { movies } from '@/data/mockData';
import { currentUser } from '@/data/mockData';
import { ChevronRight, Flame, Clock, Film } from 'lucide-react';

const categories = [
  { name: '动作', icon: Flame, movies: movies.filter((m) => m.genre.includes('动作')) },
  { name: '喜剧', icon: Film, movies: movies.filter((m) => m.genre.includes('喜剧')) },
  { name: '悬疑', icon: Clock, movies: movies.filter((m) => m.genre.includes('悬疑')) },
  { name: '动漫', icon: Film, movies: movies.filter((m) => m.genre.includes('动画')) },
];

export default function Home() {
  const hotMovies = movies.slice(0, 6);
  const continueWatching = currentUser.watchHistory;

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      <TopNav />

      <main className="max-w-[430px] mx-auto pt-14 px-4">
        <div className="mt-4">
          <HeroCarousel />
        </div>

        <section className="mt-6">
          <div className="flex items-center justify-between mb-3">
            <div className="flex items-center gap-2">
              <Flame className="w-5 h-5 text-coral" />
              <h2 className="text-lg font-bold text-charcoal font-display">热门推荐</h2>
            </div>
            <button className="flex items-center gap-0.5 text-xs text-gray-400 hover:text-coral transition-colors">
              全部 <ChevronRight className="w-3.5 h-3.5" />
            </button>
          </div>
          <div className="flex gap-3 overflow-x-auto pb-2 -mx-4 px-4 scrollbar-hide">
            {hotMovies.slice(0, 3).map((movie) => (
              <MovieCard key={movie.id} movie={movie} />
            ))}
            <AdCard />
            {hotMovies.slice(3, 6).map((movie) => (
              <MovieCard key={movie.id} movie={movie} />
            ))}
          </div>
        </section>

        {continueWatching.length > 0 && (
          <section className="mt-6">
            <div className="flex items-center justify-between mb-3">
              <div className="flex items-center gap-2">
                <Clock className="w-5 h-5 text-mint" />
                <h2 className="text-lg font-bold text-charcoal font-display">继续观看</h2>
              </div>
            </div>
            <div className="flex gap-3 overflow-x-auto pb-2 -mx-4 px-4 scrollbar-hide">
              {continueWatching.map((movie) => (
                <MovieCard key={movie.id} movie={movie} showProgress />
              ))}
            </div>
          </section>
        )}

        {categories.map((category) => (
          <section key={category.name} className="mt-6">
            <div className="flex items-center justify-between mb-3">
              <div className="flex items-center gap-2">
                <category.icon className="w-5 h-5 text-coral" />
                <h2 className="text-lg font-bold text-charcoal font-display">{category.name}</h2>
              </div>
              <button className="flex items-center gap-0.5 text-xs text-gray-400 hover:text-coral transition-colors">
                全部 <ChevronRight className="w-3.5 h-3.5" />
              </button>
            </div>
            <div className="flex gap-3 overflow-x-auto pb-2 -mx-4 px-4 scrollbar-hide">
              {category.movies.length > 0 ? (
                category.movies.map((movie) => <MovieCard key={movie.id} movie={movie} />)
              ) : (
                <p className="text-sm text-gray-400 py-4">暂无{category.name}影片</p>
              )}
            </div>
          </section>
        ))}

        <div className="mt-8 text-center pb-4">
          <p className="text-xs text-gray-300">已经到底了</p>
        </div>
      </main>
    </div>
  );
}
