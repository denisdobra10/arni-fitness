import React, {useEffect, useState} from 'react'
import UserActiveReservations from './user-active-reservations'
import UserActivePayments from './user-active-payments';
import {useData} from "../../lib/data-provider.jsx";

const UserAccountOptions = () => {
    const [activeReservations, setActiveReservations] = useState(false);
    const [activePayments, setActivePayments] = useState(false);
    const { user } = useData();

    useEffect(() => {
        if (user?.activeReservations.length >= 1) {
            setActiveReservations(true);
        }
    }, [user]);


    return (
        <div className="flex flex-col py-8 px-4 gap-4 lg:gap-16 w-full rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
            <span className='text-3xl font-bold uppercase'>Optiunile <span className='text-slate-500'>Contului</span></span>

            <div className="flex flex-col">
                <button onClick={() => setActiveReservations(!activeReservations)} className='flex flex-row justify-between w-full bg-primary text-white text-2xl font-bold px-4 py-2 items-center'>
                    <span>Rezervari Active</span>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-6 h-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M15.042 21.672 13.684 16.6m0 0-2.51 2.225.569-9.47 5.227 7.917-3.286-.672ZM12 2.25V4.5m5.834.166-1.591 1.591M20.25 10.5H18M7.757 14.743l-1.59 1.59M6 10.5H3.75m4.007-4.243-1.59-1.59" />
                    </svg>
                </button>
                {activeReservations && <UserActiveReservations />}
            </div>

            <div className="flex flex-col">
                <button onClick={() => setActivePayments(!activePayments)} className='flex flex-row justify-between w-full bg-primary text-white text-2xl font-bold px-4 py-2 items-center'>
                    <span>Plati si Facturare</span>
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-6 h-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M15.042 21.672 13.684 16.6m0 0-2.51 2.225.569-9.47 5.227 7.917-3.286-.672ZM12 2.25V4.5m5.834.166-1.591 1.591M20.25 10.5H18M7.757 14.743l-1.59 1.59M6 10.5H3.75m4.007-4.243-1.59-1.59" />
                    </svg>
                </button>
                {activePayments && <UserActivePayments />}
            </div>

        </div>
    )
}

export default UserAccountOptions