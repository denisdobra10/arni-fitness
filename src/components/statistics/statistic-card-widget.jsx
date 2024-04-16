import React from 'react'

function StatisticCard({ icon, iconWidth = '100px', value, title }) {
    return (
        <div className="flex flex-col bg-white rounded-lg p-3 w-full">
            <img src={icon} alt="Statistic icon" width={iconWidth} className='self-center' />
            <h2 className='text-primary font-semibold text-3xl'>{value}</h2>
            <span className=' text-gray-500 '>{title}</span>
        </div>
    )
}


export default StatisticCard