import React from 'react'
import AntrenoriActivityTable from './antrenori-activity-table'

function AntrenorActivityCard(props) {
    const { coach } = props;

    return (
        <div className="flex flex-col rounded-sm p-3 shadow-sm shadow-black text-primary w-full">

            <div className="flex flex-col text-center">
                <h2 className='text-lg font-bold'>{coach?.name}</h2>
                <h2>antreneaza <span className='font-bold'>{coach?.classStatistics?.length} clase</span></h2>
            </div>

            <div className="my-6">
                {coach?.description}
            </div>

            <div className="flex flex-col gap-3 text-center">

                <div className="flex flex-row gap-3">
                    <div className='w-full'><span className='font-bold'>Clienti in total:</span> {coach?.totalClients}</div>
                    <div className='w-full'><span className='font-bold'>Clienti saptamana:</span> {coach?.weeklyClients}</div>
                </div>
            </div>

            {coach?.classStatistics?.map((classStats, index) => (
                <div key={index} className="flex flex-col gap-3 text-center my-6">
                    <span>{classStats?.className}</span>
                    <AntrenoriActivityTable classStatistics={classStats}/>
                </div>
            ))}
        </div>
    )
}

export default AntrenorActivityCard