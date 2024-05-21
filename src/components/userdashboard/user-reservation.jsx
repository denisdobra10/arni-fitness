import React from 'react'
import UserCalendarWidget from './user-calendar-widget'
import UserReservationAvailableClasses from './user-reservation-available-classes'

const UserReservation = () => {
    return (
        <div className="flex flex-col py-8 px-4 gap-4 lg:gap-16 w-full rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
            <span className='text-3xl font-bold uppercase'>Rezerva <span className='text-slate-500'>Clasa</span></span>
            <span className='self-start'>Selecteaza o zi din calendar pentru a vedea ce clase sunt disponibile pentru ziua respectiva.</span>
            <div className="self-center w-full md:w-[60%]">
                <UserCalendarWidget />
            </div>
            <UserReservationAvailableClasses />

        </div>
    )
}

export default UserReservation