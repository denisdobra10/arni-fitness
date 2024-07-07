import { createContext, useContext, useEffect, useState } from "react";
import { toast } from 'react-toastify';
import axios from '../utils/axios';

const DataContext = createContext();

export const DataProvider = ({ children }) => {
    const [isLoading, setIsLoading] = useState(false);
    const [loadingMessage, setLoadingMessage] = useState('Loading...');

    const [loggedIn, setLoggedIn] = useState(false)
    const [user, setUser] = useState(null);

    const [confirmModalOptions, setConfirmModalOptions] = useState({ message: 'Esti sigur ca vrei sa continui?', onConfirm: () => { }, onCancel: () => { }, show: false, title: 'Confirm', confirmText: 'Confirma', cancelText: 'Renunta' });

    const displayConfirmModal = (message, onConfirm, onCancel, title = 'Confirm', confirmText = 'Confirm', cancelText = 'Cancel') => {
        setConfirmModalOptions({ message, onConfirm, onCancel, show: true, title, confirmText, cancelText });
    }

    useEffect(() => {
        const token = localStorage.getItem('accessToken');

        const fetchUserData = async () => {
            if (token) {
                try {
                    const response = await axios.get('/user/details');

                    setUser(response.data);
                    localStorage.setItem('user', JSON.stringify(response.data));
                } catch (err) {
                    console.log(err);
                }

            }
        }

        const getUser = async () => {

            if (token) {
                if (user) return;

                if (localStorage.getItem('user' !== null)) {
                    setUser(JSON.parse(localStorage.getItem('user')));
                } else {
                    fetchUserData();
                }
            }
        }

        getUser();
    }, []);

    const login = async (token) => {
        localStorage.setItem('accessToken', token);

        const response = await axios.get('/user/details', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        console.log(response.data);

        setUser(response.data);
        localStorage.setItem('user', JSON.stringify(response.data));

        setLoggedIn(true);
    }

    const logout = () => {
        localStorage.removeItem('accessToken');
        setLoggedIn(false);
    }

    const setAccessToken = (token) => {
        localStorage.setItem('accessToken', token);
    }

    const getAccessToken = () => {
        return localStorage.getItem('accessToken');
    }

    const isAuthenticated = () => {
        return getAccessToken() !== null;
    }

    const displayLoadingScreen = (message) => {
        setIsLoading(true);
        setLoadingMessage(message);
    }

    const hideLoadingScreen = () => {
        setIsLoading(false);
        setLoadingMessage('Loading...');
    }

    const displayNotification = (message, type = 'info') => {
        toast(message, { hideProgressBar: true, type: type, autoClose: 3000 })
    }

    return (
        <DataContext.Provider value={{ user, setUser, loggedIn, login, logout, setAccessToken, getAccessToken, isLoading, loadingMessage, displayLoadingScreen, hideLoadingScreen, displayNotification, isAuthenticated, confirmModalOptions, displayConfirmModal, setConfirmModalOptions }}>
            {children}
        </DataContext.Provider>
    );
};

export const useData = () => {
    return useContext(DataContext);
};