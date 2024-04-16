import React from 'react'
import AntrenorActivityCard from './antrenor-activity-card'

function AntrenoriListWidget() {
    return (
        <div className='flex flex-col w-full bg-white rounded-lg p-3 my-10 text-center sm:text-left sm:p-10 '>
            <h2 className='text-3xl text-primary font-semibold capitalize'>Antrenori activi</h2>
            <span className='text-gray-500 mb-6'>Antrenorii activi si disponibili clientilor tai</span>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-3">
                <AntrenorActivityCard />
                <AntrenorActivityCard />
                <AntrenorActivityCard />
                <AntrenorActivityCard />
            </div>
        </div>
    )
}

export default AntrenoriListWidget