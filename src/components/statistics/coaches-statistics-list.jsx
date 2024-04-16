import StatisticCard from './statistic-card-widget'
import React from 'react'


function CoachesStatisticsList() {
    return (
        <div className="flex flex-col sm:flex-row p-6 justify-center items-center gap-6">
            <StatisticCard value={'Ion Popescu'} title={'Antrenorul cu cele mai multe programari azi'} />
            <StatisticCard value={'Ana Manole'} title={'Antrenorul cu cele mai multe programari saptamana aceasta'} />
            <StatisticCard value={'Cristian Pop'} title={'Antrenorul cu cele mai multe programari luna aceasta'} />
        </div>
    )
}

export default CoachesStatisticsList