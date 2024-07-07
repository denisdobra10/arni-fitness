import StatisticCard from './statistic-card-widget'
import React from 'react'


function CoachesStatisticsList({ mostPopularCoachToday, mostPopularCoachWeek, mostPopularCoachMonth }) {
    return (
        <div className="flex flex-col sm:flex-row p-6 justify-center items-center gap-6">
            <StatisticCard value={mostPopularCoachToday} title={'Antrenorul cu cele mai multe programari azi'} />
            <StatisticCard value={mostPopularCoachWeek} title={'Antrenorul cu cele mai multe programari saptamana aceasta'} />
            <StatisticCard value={mostPopularCoachMonth} title={'Antrenorul cu cele mai multe programari luna aceasta'} />
        </div>
    )
}

export default CoachesStatisticsList