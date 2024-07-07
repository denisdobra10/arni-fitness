import React from 'react'
import StatisticCard from './statistic-card-widget'

function SecondaryStatisticsList({ createdMemberships, createdClasses, createdCoaches, todaysReservations, todayFullSessions }) {
    return (
        <div className="flex flex-col sm:flex-row p-6 justify-center items-center gap-6">
            <StatisticCard value={createdMemberships} title={'Abonamente create'} />
            <StatisticCard value={createdClasses} title={'Clase create'} />
            <StatisticCard value={createdCoaches} title={'Antrenori creati'} />
            <StatisticCard value={todaysReservations} title={'Rezervari astazi'} />
            <StatisticCard value={todayFullSessions} title={'Clase full ocupate'} />
        </div>
    )
}

export default SecondaryStatisticsList