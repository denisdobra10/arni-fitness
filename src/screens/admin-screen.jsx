import React from 'react'
import AdminLayout from '../pages/admin-layout';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import StatisticsPage from '../pages/statistics-page';

function AdminDashboard() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<AdminLayout />}>
                    <Route index element={<StatisticsPage />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AdminDashboard