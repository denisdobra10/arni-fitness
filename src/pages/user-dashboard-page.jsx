import React from 'react'
import UserDashboardHeader from '../components/userdashboard/user-dashboard-header'
import UserDashboardBody from '../components/userdashboard/user-dashboard-body'

const UserDashboardPage = () => {
    return (
        <div className="flex flex-col">

            <UserDashboardHeader name={'Ion Popescu'} subscriptionType={'Gold'} />
            <UserDashboardBody />

        </div>
    )
}

export default UserDashboardPage