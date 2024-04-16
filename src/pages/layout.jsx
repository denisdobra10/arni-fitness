import React from 'react'
import { Outlet } from "react-router-dom";
import NavigationSidebar from '../components/navigation-sidebar';
import MenuIcon from '@mui/icons-material/Menu';

function Layout() {
    return (

        <>
            <div className="flex flex-row w-full bg-yellow-500 justify-between sm:hidden">
                Logo
                <MenuIcon onClick={() => console.log('test')} />

            </div>

            <div className='flex flex-row w-full'>


                <div className="sm:flex w-[20%] hidden">
                    <NavigationSidebar />
                </div>

                <div className="flex w-full sm:w-[80%]">
                    <Outlet />
                </div>
            </div>
        </>


    )
}

export default Layout