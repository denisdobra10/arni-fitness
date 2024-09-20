import React from 'react'
import Logo from '../assets/logo-dreptunghi.jpeg'
import LoginFormular from '../components/LoginFormular'

const LoginScreen = () => {

    return (
        <div className="relative flex flex-row w-full h-screen">
            <div className="hidden lg:flex flex-col justify-center items-end w-1/3 h-full bg-primary pointer-events-none select-none">
                <div className="flex flex-col justify-center items-center px-16 text-center w-[70%] h-2/3 rounded-l-lg gap-4 text-2xl font-bold shadow-spreaded shadow-black">
                    <img src={Logo} alt="Logo" />
                    <span>Clubul sportiv preferat de satmareni</span>
                </div>
            </div>
            <div className="flex flex-col justify-center items-center lg:items-start w-full lg:w-2/3 h-full bg-white">
                <img src={Logo} alt="Logo" className='visible lg:invisible absolute top-8 w-56 h-24 ' />

                <div className="flex flex-col justify-center items-center px-8 lg:px-16 text-center w-[90%] h-2/3 rounded lg:rounded-none lg:rounded-r-lg gap-4 text-2xl font-bold shadow-spreaded shadow-black">
                    <LoginFormular />
                </div>
            </div>
        </div>
    )
}

export default LoginScreen