
import React from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget';
import MainStatisticsList from '../components/statistics/main-statistics-widget';



function StatisticsPage() {
    return (
        <div className='flex flex-col p-6 w-full'>
            <HeaderTitleWidget title='Statistici (Energy Kardio Club)' />

            <div className="container min-h-12">
                <MainStatisticsList />
            </div>
        </div>
    )
}

export default StatisticsPage