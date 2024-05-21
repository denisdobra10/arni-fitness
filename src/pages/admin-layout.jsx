import React from 'react'
import { Outlet } from "react-router-dom";
import NavigationSidebar from '../components/navigation-sidebar';
import AdminMobileMenu from '../components/admin-mobile-menu';


function AdminLayout() {
    return (

        <>
            <AdminMobileMenu />

            <div className='flex flex-row w-full'>
                <div className="sm:flex sm:flex-col w-[20%] hidden">
                    <NavigationSidebar />
                </div>

                <div className="flex w-full sm:w-[80%]">
                    <Outlet />
                </div>
            </div>
        </>


    )
}

export default AdminLayout