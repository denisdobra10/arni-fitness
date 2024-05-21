import React from 'react'

const ClassReservationItem = ({ className, coach, time, availableSpace }) => {
    return (
        <div className='flex flex-col sm:flex-row gap-4 sm:gap-0 p-4 md:px-8 md:py-4 w-full border-2 border-primary justify-between items-center'>
            <div className="flex flex-col sm:flex-row gap-4 text-2xl font-bold">
                <span>{className}</span>
                <span>{coach}</span>
                <span>{time}</span>
                <span>{availableSpace} locuri ramase</span>
            </div>

            <button className='px-4 py-2 w-fit bg-primary text-white font-bold text-xl'>Rezerva loc</button>
        </div>
    )
}

export default ClassReservationItem