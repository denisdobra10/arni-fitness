import React from 'react'
import AntrenoriActivityTable from './antrenori-activity-table'

function AntrenorActivityCard() {
    return (
        <div className="flex flex-col rounded-sm p-3 shadow-sm shadow-black text-primary w-full">

            <div className="flex flex-col text-center">
                <h2 className='text-lg font-bold'>Nume antrenor</h2>
                <h2>antreneaza <span className='font-bold'>3 clase</span>: nume clasa 1, nume clasa 2, nume clasa 3</h2>
            </div>

            <div className="my-6">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut elit tellus, luctus nec ullamcorper mattis, pulvinar dapibus leo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut elit tellus, luctus nec ullamcorper mattis, pulvinar dapibus leo.
            </div>

            <div className="flex flex-col gap-3 text-center">

                <div className="flex flex-row gap-3">
                    <div className='w-full'><span className='font-bold'>Clienti in total:</span> 2500</div>
                    <div className='w-full'><span className='font-bold'>Clienti saptamana:</span> 25</div>
                </div>

                <div className="flex flex-row gap-3">
                    <div className='w-full'><span className='font-bold'>Clienti in total:</span> 2500</div>
                    <div className='w-full'><span className='font-bold'>Clienti saptamana:</span> 25</div>
                </div>
            </div>

            <div className="flex flex-col gap-3 text-center my-6">
                <span>Nume clasa</span>
                <AntrenoriActivityTable />
            </div>
            <div className="flex flex-col gap-3 text-center my-6">
                <span>Nume clasa</span>
                <AntrenoriActivityTable />
            </div>
        </div>
    )
}

export default AntrenorActivityCard