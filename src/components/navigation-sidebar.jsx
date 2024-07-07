import React from 'react'
import logo from '../assets/logo-dreptunghi.jpeg';
import { Link, useLocation } from 'react-router-dom';

function NavigationSidebar() {

    const location = useLocation();
    const activeDesign = 'bg-primary p-4 font-semibold text-lg my-2';
    const inactiveDesign = 'hover:bg-primary p-4 font-semibold text-lg my-2';

    return (
        <div className="flex flex-col p-7 sticky top-0 z-10">

            <img src={logo} alt="Logo" />

            <h6 className='text-primary font-medium text-md mt-6'>Meniu</h6>
            <div className="flex flex-col mt-3">
                <Link to='' className={location.pathname === '/admin' ? activeDesign : inactiveDesign}>Statistici</Link>
                <Link to='abonamente' className={location.pathname === '/admin/abonamente' ? activeDesign : inactiveDesign}>Abonamente</Link>
                <Link to='clase' className={location.pathname === '/admin/clase' ? activeDesign : inactiveDesign}>Clase</Link>
                <Link to='antrenori' className={location.pathname === '/admin/antrenori' ? activeDesign : inactiveDesign}>Antrenori</Link>
                <Link to='calendar' className={location.pathname === '/admin/calendar' ? activeDesign : inactiveDesign}>Calendar</Link>
                <Link to='clienti' className={location.pathname === '/admin/clienti' ? activeDesign : inactiveDesign}>Clienti</Link>
                <Link to='inventar' className={location.pathname === '/admin/inventar' ? activeDesign : inactiveDesign}>Inventar</Link>
            </div>

        </div>
    )
}

export default NavigationSidebar