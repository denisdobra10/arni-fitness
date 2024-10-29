import React, { useEffect } from 'react'
import UserDashboardMainDetails from './user-main-details'
import UserReservation from './user-reservation'
import UserAccountOptions from './user-account-options'
import { useData } from '../../lib/data-provider'
import { useNavigate } from 'react-router-dom'
import axios from '../../utils/axios';

const UserDashboardBody = () => {

    const { logout, user,displayNotification } = useData();

    const navigate = useNavigate();

    const handleLogout = () => {
        logout();

        navigate('/login');
    }

    const handleResetPassword = async () => {
        try {
        const response = await axios.post(`/user/reset-password`);
            setTimeout(() => {
                window.location.href = response.data;}, 2000);
            } catch (err) {
                displayNotification(err.response.data, 'error');
            }
        navigate('/reset');
    }


    return (
        <div className='flex flex-col px-8 py-8 lg:px-32 lg:py-16 gap-16 justify-center items-center'>

            <div className="flex flex-col py-8 gap-2 w-full lg:w-1/2 rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
                <span className='text-6xl font-bold'>{user?.user?.pin}</span>
                <span className='text-3xl'>Codul tau PIN</span>
                <span>Prezinta acest cod la intrarea in sala de sport</span>
            </div>

            <UserDashboardMainDetails user={user}/>
            {!!user?.subscriptionDetails && <UserReservation />}
            <UserAccountOptions />

            <button onClick={handleResetPassword} className='w-full md:w-1/2 xl:w-1/3 bg-primary text-white text-lg font-semibold px-4 py-2'>Resetare Parola</button>

            <button onClick={handleLogout} className='w-full md:w-1/2 xl:w-1/3 bg-primary text-white text-lg font-semibold px-4 py-2'>Iesi din cont</button>
        </div>
    )
}

export default UserDashboardBody