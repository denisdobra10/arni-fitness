import React, { useState } from 'react'
import Logo from '../../assets/logo-dreptunghi.jpeg'

const MembershipNavbar = () => {

    const [showDropdown, setShowDropdown] = useState(false);

    return (
        <div className='flex flex-row justify-between items-center px-8 md:px-32'>
            <img src={Logo} className='w-32 h-16 md:w-[250px] md:h-[150px]' />

            <div className="relative flex items-center text-red-500">
                <span onClick={() => setShowDropdown(!showDropdown)} className='invisible md:visible text-2xl tracking-widest text-white font-semibold hover:cursor-pointer'>Contul Meu</span>
                <svg onClick={() => setShowDropdown(!showDropdown)} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-8 h-8 visible md:invisible hover:cursor-pointer">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>

                {showDropdown && <div className="absolute right-2 z-10 md:right-8 top-8 bg-slate-700 min-w-40 md:min-w-64 p-4">
                    <button className='w-full text-start font-semibold tracking-wider text-white'>Logout</button>
                </div>}
            </div>


        </div>
    )
}

export default MembershipNavbar