import React from 'react'
import UserActiveReservation from './user-active-reservation'

const UserActiveReservations = () => {
    return (
        <div className="flex flex-col w-full">
            <UserActiveReservation dateTime={'14 mai 2024 15:30'} className={'TRX'} coach={'Marius Pop'} />
            <UserActiveReservation dateTime={'14 mai 2024 15:30'} className={'TRX'} coach={'Marius Pop'} />
            <UserActiveReservation dateTime={'14 mai 2024 15:30'} className={'TRX'} coach={'Marius Pop'} />
        </div>
    )
}

export default UserActiveReservations