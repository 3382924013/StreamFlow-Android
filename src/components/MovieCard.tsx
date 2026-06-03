import { useNavigate } from 'react-router-dom';
import { Crown, Star } from 'lucide-react';
import type { Movie } from '@/types';

interface MovieCardProps {
  movie: Movie;
  size?: 'small' | 'medium' | 'large';
  showProgress?: boolean;
}

export default function MovieCard({ movie, size = 'medium', showProgress = false }: MovieCardProps) {
  const navigate = useNavigate();

  const sizeClasses = {
    small: 'w-[100px]',
    medium: 'w-[140px]',
    large: 'w-full',
  };

  const imgHeights = {
    small: 'h-[140px]',
    medium: 'h-[200px]',
    large: 'h-[220px]',
  };

  return (
    <div
      className={`${sizeClasses[size]} flex-shrink-0 cursor-pointer group`}
      onClick={() => {
        navigate(`/player/${movie.id}`);
      }}
    >
      <div className={`relative ${imgHeights[size]} rounded-xl overflow-hidden mb-2`}>
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
        {showProgress && movie.progress !== undefined && (
          <div className="absolute bottom-0 left-0 right-0 h-1 bg-black/30">
            <div
              className="h-full bg-coral rounded-full"
              style={{ width: `${movie.progress}%` }}
            />
          </div>
        )}
      </div>
      <h3 className="text-sm font-semibold text-charcoal truncate">{movie.title}</h3>
      <div className="flex items-center gap-1 mt-0.5">
        <Star className="w-3 h-3 text-gold fill-gold" />
        <span className="text-xs text-gray-500">{movie.rating}</span>
        <span className="text-xs text-gray-400 ml-1">{movie.year}</span>
      </div>
    </div>
  );
}
