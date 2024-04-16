import React from 'react'
import AdminLayout from '../pages/admin-layout';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import StatisticsPage from '../pages/statistics-page';
import AbonamentePage from '../pages/abonamente-page';
import ClasePage from '../pages/clase-page';
import AntrenoriPage from '../pages/antrenori-page';
import CalendarPage from '../pages/calendar-page';

function AdminDashboard() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<AdminLayout />}>
                    <Route index element={<StatisticsPage />} />
                    <Route path='/abonamente' element={<AbonamentePage />} />
                    <Route path='/clase' element={<ClasePage />} />
                    <Route path='/antrenori' element={<AntrenoriPage />} />
                    <Route path='/calendar' element={<CalendarPage />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
}

export default AdminDashboard