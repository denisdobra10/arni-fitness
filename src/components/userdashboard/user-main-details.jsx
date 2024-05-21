import React from 'react'

const UserDashboardMainDetails = () => {
    return (
        <div className="flex flex-col py-8 px-4 gap-4 lg:gap-16 w-full rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
            <span className='text-3xl font-bold uppercase'>Detalii <span className='text-slate-500'>Abonament</span></span>

            <div className="flex flex-col gap-4 lg:gap-0 lg:flex-row justify-center items-center w-full">
                <div className="flex flex-col gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>1</span>
                    <span className='text-2xl'>Rezervari Maine</span>
                </div>
                <div className="flex flex-col  gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>9</span>
                    <span className='text-2xl'>Clase Rezervate</span>
                </div>
                <div className="flex flex-col gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>5</span>
                    <span className='text-2xl'>Rezervari Saptamana Aceasta</span>
                </div>
            </div>

            <div className="flex flex-col lg:flex-row gap-4 lg:gap-0 justify-center items-center w-full">
                <div className="flex flex-col gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>7</span>
                    <span className='text-2xl'>Programari Ramase Din Abonament</span>
                </div>
                <div className="flex flex-col  gap-2 text-center w-full">
                    <span className='text-6xl font-bold'>12</span>
                    <span className='text-2xl'>Zile Ramase Din Abonament</span>
                </div>
            </div>

            <div className="flex flex-col gap-2 w-full">
                <button className='px-16 py-4 bg-primary hover:bg-red-900 text-white w-full lg:w-fit self-center'>Ingheata Abonamentul</button>
                <span className='text-left text-sm'>Poti ingheta abonamentul o singura data pentru o perioada de maximum 14 zile. In acest timp zilele ramase din abonament vor ramane intacte.</span>

            </div>
        </div>
    )
}

export default UserDashboardMainDetails