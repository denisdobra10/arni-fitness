import React, { useState } from 'react'
import Logo from '../assets/logo-dreptunghi.jpeg'
import { Link, useLocation } from 'react-router-dom'

const AdminMobileMenu = () => {

    const currentPath = useLocation().pathname;

    const [modalActive, setModalActive] = useState(false);

    const toggleMenu = () => {
        setModalActive(!modalActive);
    }

    return (
        <div className="flex flex-row px-8 py-2 items-center w-full justify-between sm:hidden">
            <a href="#">
                <img src={Logo} className='w-32 h-16 select-none' />
            </a>

            <div className="relative flex flex-col">
                <svg onClick={toggleMenu} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-8 h-8 text-red-500 hover:cursor-pointer">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>

                {modalActive && <div className="absolute right-6 top-8 flex flex-col p-5 gap-4 w-56 text-lg font-semibold rounded z-10 bg-slate-800 select-none">
                    <Link to='/' className={currentPath === '/' ? 'text-red-500' : ''}>Statistici</Link>
                    <Link to='/abonamente' className={currentPath === '/abonamente' ? 'text-red-500' : ''}>Abonamente</Link>
                    <Link to='/clase' className={currentPath === '/clase' ? 'text-red-500' : ''}>Clase</Link>
                    <Link to='/antrenori' className={currentPath === '/antrenori' ? 'text-red-500' : ''}>Antrenori</Link>
                    <Link to='/calendar' className={currentPath === '/calendar' ? 'text-red-500' : ''}>Calendar</Link>
                    <Link to='/clienti' className={currentPath === '/clienti' ? 'text-red-500' : ''}>Clienti</Link>
                    <Link to='/inventar' className={currentPath === '/inventar' ? 'text-red-500' : ''}>Inventar</Link>
                </div>}
            </div>

        </div>
    )
}

export default AdminMobileMenu