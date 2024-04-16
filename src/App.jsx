import React from 'react'
import HomePage from './pages/home-page'
import Layout from './pages/layout';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import StatisticsPage from './pages/statistics-page';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<StatisticsPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App