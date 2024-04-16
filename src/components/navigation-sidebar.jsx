import React from 'react'
import logo from '../assets/logo-dreptunghi.jpeg';
import { Link } from 'react-router-dom';

function NavigationSidebar() {

    const activeDesign = 'bg-primary p-4 font-semibold text-lg my-2';
    const inactiveDesign = 'hover:bg-primary p-4 font-semibold text-lg my-2';

    return (
        <div className="flex flex-col p-7">

            <img src={logo} alt="Logo" />

            <h6 className='text-primary font-medium text-md mt-6'>Meniu</h6>
            <div className="flex flex-col mt-3">
                <Link to='/' className={activeDesign}>Statistici</Link>
                <Link to='/' className={inactiveDesign}>Abonamente</Link>
                <Link to='/' className={inactiveDesign}>Clase</Link>
                <Link to='/' className={inactiveDesign}>Antrenori</Link>
                <Link to='/' className={inactiveDesign}>Calendar</Link>
                <Link to='/' className={inactiveDesign}>Clienti</Link>
                <Link to='/' className={inactiveDesign}>Inventar</Link>
            </div>

        </div>
    )
}

export default NavigationSidebar