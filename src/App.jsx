import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import UserScreen from './screens/user-screen';
import MembershipScreen from './screens/membership-screen';
import LoginScreen from './screens/login-screen';
import SignupScreen from './screens/signup-screen';
import LoadingScreen from './components/LoadingScreen';
import { useData } from './lib/data-provider';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ErrorPage from './404';
import StatisticsPage from './pages/statistics-page';
import AbonamentePage from './pages/abonamente-page';
import ClasePage from './pages/clase-page';
import AntrenoriPage from './pages/antrenori-page';
import CalendarPage from './pages/calendar-page';
import ClientiPage from './pages/clienti-page';
import InventarPage from './pages/inventar-page';
import AdminLayout from './pages/admin-layout';
import ConfirmModal from './components/ConfirmModal';
import TimerPage from "./pages/timer-page.tsx";

function App() {
  const { isLoading, loadingMessage, isAuthenticated } = useData();

  const RequireAuth = ({ children }) => {
    return isAuthenticated() ? children : <Navigate to="/login" />;
  };

  const { confirmModalOptions } = useData();

  return (
    <Router>
      <div id='root'>
        {confirmModalOptions.show && <ConfirmModal />}

        {isLoading && <LoadingScreen message={loadingMessage} />}
        <Routes>
          <Route path="/admin" element={
            <RequireAuth>
              <AdminLayout />
            </RequireAuth>
          }>
            <Route index element={<StatisticsPage />} />
            <Route path="abonamente" element={<AbonamentePage />} />
            <Route path="clase" element={<ClasePage />} />
            <Route path="antrenori" element={<AntrenoriPage />} />
            <Route path="calendar" element={<CalendarPage />} />
            <Route path="clienti" element={<ClientiPage />} />
            <Route path="inventar" element={<InventarPage />} />
          </Route>
          <Route path="/user" element={<UserScreen />} />
          <Route path="/membership" element={<MembershipScreen />} />
            <Route path="/payment-redirect" element={<TimerPage />} />
          <Route path="/login" element={<LoginScreen />} />
          <Route path="/signup" element={<SignupScreen />} />
          {/* Add a default route */}
          <Route path="/" element={<LoginScreen />} />
          <Route path="*" element={<ErrorPage />} />
        </Routes>

        <ToastContainer />
      </div>
    </Router>
  );
}

export default App;
