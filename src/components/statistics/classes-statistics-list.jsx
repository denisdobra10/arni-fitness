import React from 'react'
import StatisticCard from './statistic-card-widget'

function ClassesStatisticsList({ todayChosenClass, weekChosenClass, monthChosenClass, yearChosenClass }) {
    return (
        <div className="flex flex-col p-6 justify-center items-center gap-6">
            <div className="flex flex-col w-full sm:flex-row justify-center items-center gap-6">
                <StatisticCard value={todayChosenClass} title={'Cea mai ocupata clasa azi'} />
                <StatisticCard value={weekChosenClass} title={'Cea mai ocupata clasa saptamana aceasta'} />
            </div>
            <div className="flex flex-col w-full sm:flex-row justify-center items-center gap-6">
                <StatisticCard value={monthChosenClass} title={'Cea mai ocupata clasa luna aceasta'} />
                <StatisticCard value={yearChosenClass} title={'Cea mai ocupata clasa anul acesta'} />
            </div>
        </div>
    )
}

export default ClassesStatisticsList