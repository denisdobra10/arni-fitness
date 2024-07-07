import { createContext, useContext, useState } from "react";
import { toast } from 'react-toastify';

const DataContext = createContext();

export const DataProvider = ({ children }) => {
    const [isLoading, setIsLoading] = useState(false);
    const [loadingMessage, setLoadingMessage] = useState('Loading...');

    const displayLoadingScreen = (message) => {
        setIsLoading(true);
        setLoadingMessage(message);
    }

    const hideLoadingScreen = () => {
        setIsLoading(false);
        setLoadingMessage('Loading...');
    }

    const displayNotification = (message, type = 'info') => {
        toast(message, { hideProgressBar: true, type: type })
    }

    return (
        <DataContext.Provider value={{ displayLoadingScreen, hideLoadingScreen, displayNotification }}>
            {children}
        </DataContext.Provider>
    );
};

export const useData = () => {
    return useContext(DataContext);
};