import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import { useEffect } from 'react';
import SplashScreen from '@/pages/SplashScreen';
import Home from '@/pages/Home';
import Discover from '@/pages/Discover';
import Search from '@/pages/Search';
import Player from '@/pages/Player';
import Profile from '@/pages/Profile';
import VipCenter from '@/pages/VipCenter';
import BottomNav from '@/components/BottomNav';
import Toast from '@/components/Toast';
import VipModal from '@/components/VipModal';
import LoginModal from '@/components/LoginModal';

function ScrollToTop() {
  const { pathname } = useLocation();
  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);
  return null;
}

function AppLayout() {
  const location = useLocation();
  const showNav = ['/home', '/discover', '/vip', '/profile'].includes(location.pathname);

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-[430px] mx-auto min-h-screen bg-gray-50 shadow-xl relative">
        <Routes>
          <Route path="/" element={<SplashScreen />} />
          <Route path="/home" element={<Home />} />
          <Route path="/discover" element={<Discover />} />
          <Route path="/search" element={<Search />} />
          <Route path="/player/:id" element={<Player />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/vip" element={<VipCenter />} />
        </Routes>
        {showNav && <BottomNav />}
        <Toast />
        <VipModal />
        <LoginModal />
      </div>
    </div>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <ScrollToTop />
      <AppLayout />
    </BrowserRouter>
  );
}
