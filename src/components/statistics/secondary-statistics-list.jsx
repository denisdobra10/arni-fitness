import React from 'react'
import StatisticCard from './statistic-card-widget'

function SecondaryStatisticsList() {
    return (
        <div className="flex flex-col sm:flex-row p-6 justify-center items-center gap-6">
            <StatisticCard value={'4'} title={'Abonamente create'} />
            <StatisticCard value={'5'} title={'Clase create'} />
            <StatisticCard value={'3'} title={'Antrenori creati'} />
            <StatisticCard value={'28'} title={'Rezervari astazi'} />
            <StatisticCard value={'2'} title={'Clase full ocupate'} />
        </div>
    )
}

export default SecondaryStatisticsList