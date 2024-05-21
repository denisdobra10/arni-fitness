import React from 'react'
import UserHeaderBusinessName from '../../assets/user-header-business-name-image.png'
import WaveImage from '../../assets/user-header-corner-image.png'
import UserHeaderDecorationImage from '../../assets/user-header-decoration-element-image.png'


const UserDashboardHeader = ({ name, subscriptionType }) => {
    return (
        <div className="relative flex justify-center w-full bg-white py-32 select-none pointer-events-none">

            <div className="flex flex-col text-start mr-0 xl:mr-48">
                <span className='text-black text-4xl font-semibold tracking-widest'>{name}</span>
                <span className='text-2xl text-primary'>Abonamentul <span className="uppercase font-bold">{subscriptionType}</span></span>
            </div>

            <img src={UserHeaderBusinessName} className='absolute top-0 left-10 w-32 h-32' />
            <img src={WaveImage} className='absolute bottom-0 left-0 w-full md:w-[50%] h-[75%]' />
            <img src={UserHeaderDecorationImage} className='absolute bottom-10 invisible md:visible md:right-32 right-64 w-48 h-48' />
        </div>
    )
}

export default UserDashboardHeader