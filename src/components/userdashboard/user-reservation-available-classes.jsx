import React from 'react'
import ClassReservationItem from './class-reservation-item'

const UserReservationAvailableClasses = () => {
    return (
        <div className="flex flex-col gap-4">
            <span className='text-3xl font-semibold self-start'>Clase disponibile (15.05.2024)</span>

            <ClassReservationItem className={'TRX'} coach={'Marius Pop'} time={'17:30'} availableSpace={8} />
            <ClassReservationItem className={'TRX'} coach={'Marius Pop'} time={'17:30'} availableSpace={8} />
            <ClassReservationItem className={'TRX'} coach={'Marius Pop'} time={'17:30'} availableSpace={8} />
        </div>
    )
}

export default UserReservationAvailableClasses