import React from 'react'

const UserActivePayment = ({ date, link, amount, description }) => {
    return (
        <div className='grid grid-cols-2 gap-2 border-2 p-2 border-primary'>
            <a href={link} target='_blank' className="flex flex-row gap-2 justify-center underline font-semibold">
                <span>{date}</span>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-6 h-6">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M13.19 8.688a4.5 4.5 0 0 1 1.242 7.244l-4.5 4.5a4.5 4.5 0 0 1-6.364-6.364l1.757-1.757m13.35-.622 1.757-1.757a4.5 4.5 0 0 0-6.364-6.364l-4.5 4.5a4.5 4.5 0 0 0 1.242 7.244" />
                </svg>
            </a>

            <span>{amount} lei</span>
            <span>{description}</span>
            <span className='font-bold text-lg text-green-500'>PLATIT</span>
        </div>
    )
}

export default UserActivePayment