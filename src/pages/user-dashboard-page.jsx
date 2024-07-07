import React, { useEffect, useLayoutEffect } from 'react'
import UserDashboardHeader from '../components/userdashboard/user-dashboard-header'
import UserDashboardBody from '../components/userdashboard/user-dashboard-body'
import { useData } from '../lib/data-provider'
import { useNavigate } from 'react-router-dom'

const UserDashboardPage = () => {

    const { user } = useData();

    return (
        <div className="flex flex-col">

            <UserDashboardHeader name={user?.user?.name} subscriptionType={'Gold'} />
            <UserDashboardBody />

        </div>
    )
}

export default UserDashboardPage