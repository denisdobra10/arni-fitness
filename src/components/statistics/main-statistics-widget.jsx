import React from 'react'
import membershipTypeIcon from '../../assets/membership-types.png';
import revenueBagIcon from '../../assets/revenue-bag.png';
import muscleIcon from '../../assets/muscle.png';
import membershipCardIcon from '../../assets/membership-card.png';
import StatisticCard from '../../components/statistics/statistic-card-widget';


function MainStatisticsList() {
    return (
        <div className="flex flex-col sm:flex-row p-6 justify-center items-center gap-6">
            <StatisticCard icon={membershipTypeIcon} iconWidth='100px' value={'155'} title={'Abonamente vandute'} />
            <StatisticCard icon={revenueBagIcon} iconWidth='100px' value={'2470 lei'} title={'Venit luna aceasta'} />
            <StatisticCard icon={muscleIcon} iconWidth='100px' value={'35'} title={'Clienti activi'} />
            <StatisticCard icon={membershipCardIcon} iconWidth='100px' value={'24'} title={'Clienti astazi'} />
        </div>
    )
}

export default MainStatisticsList