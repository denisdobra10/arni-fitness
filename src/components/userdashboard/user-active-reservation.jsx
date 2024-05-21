import React from 'react'

const UserActiveReservation = ({ dateTime, className, coach }) => {
    return (
        <div className='grid grid-cols-2 gap-2 border-2 p-2 border-primary'>
            <span>{dateTime}</span>
            <span>{className}</span>
            <span>{coach}</span>
            <span className='font-bold text-lg'>REZERVAT</span>

            <button className='col-span-2 text-white bg-primary font-semibold text-lg'>Renunta</button>
        </div>
    )
}

export default UserActiveReservation