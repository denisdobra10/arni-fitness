import React from 'react'
import UserActiveReservation from './user-active-reservation'
import {useData} from "../../lib/data-provider.jsx";

const UserActiveReservations = () => {
    const { user } = useData();

    return (
        <div className="flex flex-col w-full">
            {user?.activeReservations?.length === 0 && <span className='text-2xl'>Nu ai nicio rezervare activa</span>}
            {user?.activeReservations?.map((reservation, index) => (
                <UserActiveReservation
                    key={index}
                    reservationId={reservation.reservationId}
                    dateTime={reservation.date}
                    className={reservation.sessionName}
                    coach={reservation.coachName} />
            ))}
        </div>
    )
}

export default UserActiveReservations