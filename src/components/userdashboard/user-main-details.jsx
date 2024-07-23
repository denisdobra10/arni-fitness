import React, {useEffect} from 'react'
import {useNavigate} from "react-router-dom";

const UserDashboardMainDetails = ({ user }) => {
    const hasSubscription = user?.user?.subscriptionDetails !== null;
    const navigate = useNavigate();

    const handleBuySubscription = () => {
        navigate('/membership');
    }

    useEffect(() => {

    }, []);

    return (
        hasSubscription ? (
        <div className="flex flex-col py-8 px-4 gap-4 lg:gap-16 w-full rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
            <span className='text-3xl font-bold uppercase'>Detalii <span className='text-slate-500'>Abonament</span></span>

            <div className="flex flex-col gap-4 lg:gap-0 lg:flex-row justify-center items-center w-full">
                <div className="flex flex-col gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>{user?.subscriptionDetails?.reservationsTomorrow}</span>
                    <span className='text-2xl'>Rezervari Maine</span>
                </div>
                <div className="flex flex-col  gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>{user?.subscriptionDetails?.reservationsTotal}</span>
                    <span className='text-2xl'>Clase Rezervate</span>
                </div>
                <div className="flex flex-col gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>{user?.subscriptionDetails?.weekReservations}</span>
                    <span className='text-2xl'>Rezervari Saptamana Aceasta</span>
                </div>
            </div>

            <div className="flex flex-col lg:flex-row gap-4 lg:gap-0 justify-center items-center w-full">
                <div className="flex flex-col gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>{user?.subscriptionDetails?.reservationsLeft}</span>
                    <span className='text-2xl'>Programari Ramase Din Abonament</span>
                </div>
                <div className="flex flex-col  gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>{user?.subscriptionDetails?.daysLeft}</span>
                    <span className='text-2xl'>Zile Ramase Din Abonament</span>
                </div>
            </div>
        </div>
        ) : (
            <div className="flex flex-col py-8 px-4 gap-4 lg:gap-16 w-full rounded-lg bg-white items-center text-center text-primary shadow-spreaded shadow-primary">
                <span className='text-3xl font-bold uppercase'>Nu ai nici un abonament activ. <span className='text-slate-500'></span></span>
                <button className='px-4 py-2 w-fit bg-primary text-white font-bold text-xl' onClick={() => handleBuySubscription()}>Cumpara unul</button>
            </div>
        )
    )
}

export default UserDashboardMainDetails