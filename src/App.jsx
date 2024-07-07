import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AdminDashboard from './screens/admin-screen';
import UserScreen from './screens/user-screen';
import MembershipScreen from './screens/membership-screen';
import LoginScreen from './screens/login-screen';
import SignupScreen from './screens/signup-screen';
import LoadingScreen from './components/LoadingScreen';
import { useData } from './lib/data-provider';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ErrorPage from './404';


function App() {
  const { isLoading, loadingMessage, isAuthenticated } = useData();

  return (
    <Router>
      <div id='root'>
        {isLoading && <LoadingScreen message={loadingMessage} />}
        <Routes>
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/user" element={<UserScreen />} />
          <Route path="/membership" element={<MembershipScreen />} />
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
