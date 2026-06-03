import { create } from 'zustand';
import type { Movie, User, ToastMessage } from '@/types';
import { currentUser } from '@/data/mockData';

interface AppState {
  user: User | null;
  isLoggedIn: boolean;
  currentMovie: Movie | null;
  isPlaying: boolean;
  playbackQuality: '480P' | '720P' | '1080P' | '4K';
  playbackSpeed: number;
  currentTime: number;
  duration: number;
  toast: ToastMessage | null;
  showVipModal: boolean;
  showLoginModal: boolean;
  searchHistory: string[];
  favorites: string[];
  
  setUser: (user: User | null) => void;
  login: () => void;
  logout: () => void;
  setCurrentMovie: (movie: Movie | null) => void;
  setIsPlaying: (playing: boolean) => void;
  setPlaybackQuality: (quality: '480P' | '720P' | '1080P' | '4K') => void;
  setPlaybackSpeed: (speed: number) => void;
  setCurrentTime: (time: number | ((prev: number) => number)) => void;
  setDuration: (duration: number) => void;
  showToast: (message: string, type?: 'success' | 'error' | 'info') => void;
  hideToast: () => void;
  setShowVipModal: (show: boolean) => void;
  setShowLoginModal: (show: boolean) => void;
  addSearchHistory: (query: string) => void;
  clearSearchHistory: () => void;
  toggleFavorite: (movieId: string) => void;
  isFavorite: (movieId: string) => boolean;
}

export const useStore = create<AppState>((set, get) => ({
  user: currentUser,
  isLoggedIn: true,
  currentMovie: null,
  isPlaying: false,
  playbackQuality: '1080P',
  playbackSpeed: 1,
  currentTime: 0,
  duration: 0,
  toast: null,
  showVipModal: false,
  showLoginModal: false,
  searchHistory: ['科幻电影', '宫崎骏', '悬疑'],
  favorites: currentUser.favorites.map(m => m.id),

  setUser: (user) => set({ user }),
  login: () => set({ isLoggedIn: true, user: currentUser }),
  logout: () => set({ isLoggedIn: false, user: null, favorites: [] }),
  setCurrentMovie: (movie) => set({ currentMovie: movie }),
  setIsPlaying: (playing) => set({ isPlaying: playing }),
  setPlaybackQuality: (quality) => set({ playbackQuality: quality }),
  setPlaybackSpeed: (speed) => set({ playbackSpeed: speed }),
  setCurrentTime: (time) => set((state) => ({ currentTime: typeof time === 'function' ? time(state.currentTime) : time })),
  setDuration: (duration) => set({ duration }),
  showToast: (message, type = 'info') => {
    set({ toast: { message, type } });
    setTimeout(() => set({ toast: null }), 2000);
  },
  hideToast: () => set({ toast: null }),
  setShowVipModal: (show) => set({ showVipModal: show }),
  setShowLoginModal: (show) => set({ showLoginModal: show }),
  addSearchHistory: (query) => {
    if (!query.trim()) return;
    set((state) => ({
      searchHistory: [query, ...state.searchHistory.filter(h => h !== query)].slice(0, 10)
    }));
  },
  clearSearchHistory: () => set({ searchHistory: [] }),
  toggleFavorite: (movieId) => {
    const state = get();
    if (!state.isLoggedIn) {
      set({ showLoginModal: true });
      return;
    }
    const newFavorites = state.favorites.includes(movieId)
      ? state.favorites.filter(id => id !== movieId)
      : [...state.favorites, movieId];
    set({ favorites: newFavorites });
    state.showToast(
      state.favorites.includes(movieId) ? '已取消收藏' : '收藏成功',
      'success'
    );
  },
  isFavorite: (movieId) => get().favorites.includes(movieId),
}));
