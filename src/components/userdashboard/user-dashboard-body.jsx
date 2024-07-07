import React from 'react'
import UserDashboardMainDetails from './user-main-details'
import UserReservation from './user-reservation'
import UserAccountOptions from './user-account-options'
import { useData } from '../../lib/data-provider'
import { useNavigate } from 'react-router-dom'

const UserDashboardBody = () => {

    const { logout, user } = useData();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();

        navigate('/login');
    }

    return (
        <div className='flex flex-col px-8 py-8 lg:px-32 lg:py-16 gap-16 justify-center items-center'>

            <div className="flex flex-col py-8 gap-2 w-full lg:w-1/2 rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
                <span className='text-6xl font-bold'>{user.user.pin}</span>
                <span className='text-3xl'>Codul tau PIN</span>
                <span>Prezinta acest cod la intrarea in sala de sport</span>
            </div>

            <UserDashboardMainDetails />
            <UserReservation />
            <UserAccountOptions />

            <button onClick={handleLogout} className='w-full md:w-1/2 xl:w-1/3 bg-primary text-white text-lg font-semibold px-4 py-2'>Iesi din cont</button>
        </div>
    )
}

export default UserDashboardBody