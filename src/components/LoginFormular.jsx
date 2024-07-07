import React from 'react'
import { Link } from 'react-router-dom'
import GoogleSvgIcon from '../assets/google-icon.svg'

const LoginFormular = () => {
    return (
        <div className="flex flex-col gap-4 w-full text-black">
            <span className='text-3xl'>Conectare</span>

            <form className='flex flex-col gap-2 w-full lg:w-2/3 text-left self-center'>
                <label htmlFor="email" className='text-lg'>Email</label>
                <input type="text" name="email" id="email" placeholder='exemplu@gmail.com'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />

                <label htmlFor="password" className='text-lg'>Parola</label>
                <input type="text" name="password" id="password" placeholder='************'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />

                <input type="submit" value="Login" className='text-lg font-bold bg-primary py-3 w-2/3 text-white self-center rounded my-2 hover:cursor-pointer hover:bg-red-800' />
            </form>

            <div className="flex flex-row gap-4 self-end items-center">
                <span className='text-base text-primary font-light'>Nu ai un cont?</span>
                <Link to={'/signup'} className='bg-primary px-8 text-base py-2 text-center text-white font-bold rounded'>Inregistreaza-te</Link>
            </div>

            <div className="w-full h-[0.5px] bg-slate-600"></div>

            <button className='flex flex-row justify-center w-2/3 self-center gap-4 py-2 items-center text-center border border-slate-500 rounded'>
                <img src={GoogleSvgIcon} className='w-6 h-6' />
                <span className='text-base font-medium text-black'>Conectare cu Google</span>
            </button>
        </div>
    )
}

export default LoginFormular