export interface Movie {
  id: string;
  title: string;
  description: string;
  cover: string;
  poster: string;
  genre: string[];
  year: number;
  region: string;
  rating: number;
  isVip: boolean;
  duration: string;
  episodes?: number;
  progress?: number;
}

export interface User {
  id: string;
  nickname: string;
  avatar: string;
  isVip: boolean;
  vipExpireDate?: string;
  watchHistory: Movie[];
  favorites: Movie[];
  downloads: Movie[];
}

export interface Comment {
  id: string;
  userId: string;
  nickname: string;
  avatar: string;
  rating: number;
  content: string;
  likes: number;
  isLiked: boolean;
  createdAt: string;
}

export interface ToastMessage {
  message: string;
  type: 'success' | 'error' | 'info';
}
