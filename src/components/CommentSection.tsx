import { useState } from 'react';
import { useStore } from '@/store/useStore';
import { Heart, Star, MessageCircle } from 'lucide-react';
import { comments as initialComments } from '@/data/mockData';
import type { Comment } from '@/types';

export default function CommentSection() {
  const [comments, setComments] = useState<Comment[]>(initialComments);
  const { isLoggedIn, showToast, setShowLoginModal } = useStore();

  const handleLike = (commentId: string) => {
    if (!isLoggedIn) {
      setShowLoginModal(true);
      return;
    }
    setComments((prev) =>
      prev.map((c) =>
        c.id === commentId
          ? { ...c, isLiked: !c.isLiked, likes: c.isLiked ? c.likes - 1 : c.likes + 1 }
          : c
      )
    );
  };

  return (
    <div className="bg-white rounded-2xl p-4 mt-4">
      <div className="flex items-center gap-2 mb-4">
        <MessageCircle className="w-5 h-5 text-coral" />
        <h3 className="font-semibold text-charcoal">观众评论</h3>
        <span className="text-xs text-gray-400">({comments.length})</span>
      </div>

      <div className="space-y-4">
        {comments.map((comment) => (
          <div key={comment.id} className="flex gap-3">
            <img
              src={comment.avatar}
              alt={comment.nickname}
              className="w-9 h-9 rounded-full object-cover flex-shrink-0"
            />
            <div className="flex-1 min-w-0">
              <div className="flex items-center gap-2 mb-1">
                <span className="text-sm font-medium text-charcoal">{comment.nickname}</span>
                <div className="flex items-center gap-0.5">
                  {Array.from({ length: 5 }).map((_, i) => (
                    <Star
                      key={i}
                      className={`w-3 h-3 ${
                        i < comment.rating ? 'text-gold fill-gold' : 'text-gray-200'
                      }`}
                    />
                  ))}
                </div>
              </div>
              <p className="text-sm text-gray-600 leading-relaxed">{comment.content}</p>
              <div className="flex items-center gap-4 mt-2">
                <span className="text-xs text-gray-400">{comment.createdAt}</span>
                <button
                  onClick={() => handleLike(comment.id)}
                  className={`flex items-center gap-1 text-xs transition-colors ${
                    comment.isLiked ? 'text-coral' : 'text-gray-400 hover:text-coral'
                  }`}
                >
                  <Heart className={`w-3.5 h-3.5 ${comment.isLiked ? 'fill-coral' : ''}`} />
                  {comment.likes}
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      <button
        onClick={() => {
          if (!isLoggedIn) {
            setShowLoginModal(true);
            return;
          }
          showToast('评论功能演示', 'info');
        }}
        className="w-full mt-4 py-2.5 bg-gray-50 rounded-xl text-sm text-gray-500 hover:bg-gray-100 transition-colors"
      >
        写评论...
      </button>
    </div>
  );
}
