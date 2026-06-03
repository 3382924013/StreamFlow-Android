import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Play, Crown, ChevronRight } from 'lucide-react';
import { heroMovies } from '@/data/mockData';

export default function HeroCarousel() {
  const [current, setCurrent] = useState(0);
  const navigate = useNavigate();

  const nextSlide = useCallback(() => {
    setCurrent((prev) => (prev + 1) % heroMovies.length);
  }, []);

  useEffect(() => {
    const timer = setInterval(nextSlide, 4000);
    return () => clearInterval(timer);
  }, [nextSlide]);

  return (
    <div className="relative w-full h-[280px] rounded-2xl overflow-hidden">
      {heroMovies.map((movie, index) => (
        <div
          key={movie.id}
          className={`absolute inset-0 transition-opacity duration-700 ${
            index === current ? 'opacity-100' : 'opacity-0'
          }`}
        >
          <img
            src={movie.poster}
            alt={movie.title}
            className="w-full h-full object-cover"
          />
          <div className="absolute inset-0 bg-black/40" />
          <div className="absolute bottom-0 left-0 right-0 p-5">
            <div className="flex items-center gap-2 mb-2">
              {movie.isVip && (
                <span className="flex items-center gap-1 px-2 py-0.5 bg-gold/90 rounded-md">
                  <Crown className="w-3 h-3 text-white" />
                  <span className="text-[10px] font-bold text-white">VIP专享</span>
                </span>
              )}
              <span className="px-2 py-0.5 bg-white/20 rounded-md text-[10px] text-white">
                {movie.genre[0]}
              </span>
            </div>
            <h2 className="text-xl font-bold text-white font-display mb-1">{movie.title}</h2>
            <p className="text-xs text-white/80 line-clamp-2 mb-3">{movie.description}</p>
            <button
              onClick={() => navigate(`/player/${movie.id}`)}
              className="flex items-center gap-2 px-5 py-2.5 bg-coral rounded-xl text-white text-sm font-semibold hover:bg-coral-dark active:scale-[0.98] transition-all"
            >
              <Play className="w-4 h-4 fill-white" />
              立即播放
              <ChevronRight className="w-4 h-4" />
            </button>
          </div>
        </div>
      ))}

      <div className="absolute bottom-5 right-5 flex items-center gap-1.5">
        {heroMovies.map((_, index) => (
          <button
            key={index}
            onClick={() => setCurrent(index)}
            className={`h-1.5 rounded-full transition-all ${
              index === current ? 'w-6 bg-coral' : 'w-1.5 bg-white/50'
            }`}
          />
        ))}
      </div>
    </div>
  );
}
