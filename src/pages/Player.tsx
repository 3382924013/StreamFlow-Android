import { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useStore } from '@/store/useStore';
import { movies } from '@/data/mockData';
import CommentSection from '@/components/CommentSection';
import {
  ChevronLeft,
  Play,
  Pause,
  Maximize,
  Settings,
  SkipForward,
  Heart,
  Share2,
  Crown,
  Volume2,
} from 'lucide-react';

const qualities = ['480P', '720P', '1080P', '4K'] as const;
const speeds = [0.5, 0.75, 1, 1.25, 1.5, 2];

export default function Player() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const videoRef = useRef<HTMLDivElement>(null);

  const movie = movies.find((m) => m.id === id);
  const {
    isPlaying,
    setIsPlaying,
    playbackQuality,
    setPlaybackQuality,
    playbackSpeed,
    setPlaybackSpeed,
    currentTime,
    setCurrentTime,
    user,
    showToast,
    setShowVipModal,
    toggleFavorite,
    isFavorite,
  } = useStore();

  const [showControls, setShowControls] = useState(true);
  const [showQualityMenu, setShowQualityMenu] = useState(false);
  const [showSpeedMenu, setShowSpeedMenu] = useState(false);
  const [progress, setProgress] = useState(0);
  const [liked, setLiked] = useState(false);
  const controlsTimer = useRef<ReturnType<typeof setTimeout>>();

  const duration = 7200;

  useEffect(() => {
    setProgress((currentTime / duration) * 100);
  }, [currentTime]);

  useEffect(() => {
    const timer = setInterval(() => {
      if (isPlaying) {
        setCurrentTime((prev) => {
          const next = prev + playbackSpeed;
          return next >= duration ? 0 : next;
        });
      }
    }, 1000);
    return () => clearInterval(timer);
  }, [isPlaying, playbackSpeed, setCurrentTime]);

  const handleQualityChange = (q: typeof qualities[number]) => {
    if (q === '4K' && !user?.isVip) {
      setShowVipModal(true);
      setShowQualityMenu(false);
      return;
    }
    setPlaybackQuality(q);
    setShowQualityMenu(false);
    showToast(`已切换至 ${q}`, 'success');
  };

  const handleTap = () => {
    setShowControls(true);
    if (controlsTimer.current) clearTimeout(controlsTimer.current);
    controlsTimer.current = setTimeout(() => setShowControls(false), 3000);
  };

  const formatTime = (seconds: number) => {
    const h = Math.floor(seconds / 3600);
    const m = Math.floor((seconds % 3600) / 60);
    const s = Math.floor(seconds % 60);
    if (h > 0) return `${h}:${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`;
    return `${m}:${s.toString().padStart(2, '0')}`;
  };

  if (!movie) {
    return (
      <div className="min-h-screen bg-charcoal flex items-center justify-center">
        <p className="text-white">影片不存在</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 pb-20">
      <div
        ref={videoRef}
        className="relative w-full bg-black aspect-video"
        onClick={handleTap}
      >
        <img
          src={movie.cover}
          alt={movie.title}
          className="w-full h-full object-cover"
        />

        {showControls && (
          <>
            <div className="absolute top-0 left-0 right-0 p-4 bg-black/40">
              <div className="flex items-center gap-3">
                <button
                  onClick={() => navigate(-1)}
                  className="p-2 rounded-full hover:bg-white/20 transition-colors"
                >
                  <ChevronLeft className="w-5 h-5 text-white" />
                </button>
                <h2 className="text-sm font-medium text-white truncate">{movie.title}</h2>
              </div>
            </div>

            <div className="absolute inset-0 flex items-center justify-center">
              <button
                onClick={() => setIsPlaying(!isPlaying)}
                className="w-16 h-16 bg-white/20 backdrop-blur rounded-full flex items-center justify-center hover:bg-white/30 transition-colors"
              >
                {isPlaying ? (
                  <Pause className="w-7 h-7 text-white fill-white" />
                ) : (
                  <Play className="w-7 h-7 text-white fill-white ml-1" />
                )}
              </button>
            </div>

            <div className="absolute bottom-0 left-0 right-0 p-4 bg-black/60">
              <div className="flex items-center gap-2 mb-2">
                <span className="text-xs text-white">{formatTime(currentTime)}</span>
                <div className="flex-1 h-1 bg-white/30 rounded-full cursor-pointer">
                  <div
                    className="h-full bg-coral rounded-full relative"
                    style={{ width: `${progress}%` }}
                  >
                    <div className="absolute right-0 top-1/2 -translate-y-1/2 w-3 h-3 bg-coral rounded-full shadow" />
                  </div>
                </div>
                <span className="text-xs text-white">{formatTime(duration)}</span>
              </div>

              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <button onClick={() => setIsPlaying(!isPlaying)}>
                    {isPlaying ? (
                      <Pause className="w-5 h-5 text-white fill-white" />
                    ) : (
                      <Play className="w-5 h-5 text-white fill-white" />
                    )}
                  </button>
                  <button>
                    <SkipForward className="w-5 h-5 text-white" />
                  </button>
                  <button>
                    <Volume2 className="w-5 h-5 text-white" />
                  </button>
                </div>

                <div className="flex items-center gap-3">
                  <div className="relative">
                    <button
                      onClick={() => {
                        setShowSpeedMenu(!showSpeedMenu);
                        setShowQualityMenu(false);
                      }}
                      className="text-xs text-white px-2 py-1 bg-white/20 rounded"
                    >
                      {playbackSpeed}x
                    </button>
                    {showSpeedMenu && (
                      <div className="absolute bottom-8 right-0 bg-black/90 rounded-xl overflow-hidden">
                        {speeds.map((s) => (
                          <button
                            key={s}
                            onClick={() => {
                              setPlaybackSpeed(s);
                              setShowSpeedMenu(false);
                            }}
                            className={`block w-full px-4 py-2 text-xs text-white hover:bg-white/10 ${
                              playbackSpeed === s ? 'text-coral' : ''
                            }`}
                          >
                            {s}x
                          </button>
                        ))}
                      </div>
                    )}
                  </div>

                  <div className="relative">
                    <button
                      onClick={() => {
                        setShowQualityMenu(!showQualityMenu);
                        setShowSpeedMenu(false);
                      }}
                      className="text-xs text-white px-2 py-1 bg-white/20 rounded flex items-center gap-1"
                    >
                      {playbackQuality}
                      {playbackQuality === '4K' && <Crown className="w-3 h-3 text-gold" />}
                    </button>
                    {showQualityMenu && (
                      <div className="absolute bottom-8 right-0 bg-black/90 rounded-xl overflow-hidden">
                        {qualities.map((q) => (
                          <button
                            key={q}
                            onClick={() => handleQualityChange(q)}
                            className={`flex items-center gap-1 w-full px-4 py-2 text-xs text-white hover:bg-white/10 ${
                              playbackQuality === q ? 'text-coral' : ''
                            }`}
                          >
                            {q}
                            {q === '4K' && <Crown className="w-3 h-3 text-gold" />}
                          </button>
                        ))}
                      </div>
                    )}
                  </div>

                  <button>
                    <Maximize className="w-5 h-5 text-white" />
                  </button>
                </div>
              </div>
            </div>
          </>
        )}
      </div>

      <div className="max-w-[430px] mx-auto px-4 py-4">
        <div className="bg-white rounded-2xl p-4">
          <div className="flex items-start justify-between mb-2">
            <div>
              <h1 className="text-lg font-bold text-charcoal font-display">{movie.title}</h1>
              <div className="flex items-center gap-2 mt-1">
                <span className="text-xs text-gray-500">{movie.year}</span>
                <span className="text-xs text-gray-300">|</span>
                <span className="text-xs text-gray-500">{movie.region}</span>
                <span className="text-xs text-gray-300">|</span>
                <span className="text-xs text-gray-500">{movie.duration}</span>
              </div>
            </div>
            {movie.isVip && (
              <span className="flex items-center gap-1 px-2 py-1 bg-gold/10 rounded-lg">
                <Crown className="w-3.5 h-3.5 text-gold" />
                <span className="text-xs font-bold text-gold">VIP</span>
              </span>
            )}
          </div>

          <div className="flex flex-wrap gap-1.5 mb-3">
            {movie.genre.map((g) => (
              <span key={g} className="px-2 py-0.5 bg-gray-100 rounded-md text-xs text-gray-600">
                {g}
              </span>
            ))}
          </div>

          <p className="text-sm text-gray-600 leading-relaxed">{movie.description}</p>

          <div className="flex items-center gap-3 mt-4">
            <button
              onClick={() => {
                toggleFavorite(movie.id);
              }}
              className={`flex items-center gap-2 px-4 py-2 rounded-xl text-sm font-medium transition-colors ${
                isFavorite(movie.id)
                  ? 'bg-coral/10 text-coral'
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              }`}
            >
              <Heart className={`w-4 h-4 ${isFavorite(movie.id) ? 'fill-coral' : ''}`} />
              {isFavorite(movie.id) ? '已收藏' : '收藏'}
            </button>
            <button
              onClick={() => showToast('分享功能演示', 'info')}
              className="flex items-center gap-2 px-4 py-2 bg-gray-100 rounded-xl text-sm font-medium text-gray-600 hover:bg-gray-200 transition-colors"
            >
              <Share2 className="w-4 h-4" />
              分享
            </button>
            <button
              onClick={() => showToast('下载功能演示', 'info')}
              className="flex items-center gap-2 px-4 py-2 bg-gray-100 rounded-xl text-sm font-medium text-gray-600 hover:bg-gray-200 transition-colors"
            >
              <Settings className="w-4 h-4" />
              下载
            </button>
          </div>
        </div>

        <CommentSection />
      </div>
    </div>
  );
}
