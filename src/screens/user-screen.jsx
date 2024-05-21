import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import UserDashboardPage from '../pages/user-dashboard-page'

const UserScreen = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route index element={<UserDashboardPage />} />
            </Routes>
        </BrowserRouter>
    )
}

export default UserScreen