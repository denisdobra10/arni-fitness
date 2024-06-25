import React from 'react'
import MembershipSubscriptions from './MembershipSubscriptions'

const MembershipBody = () => {
    return (
        <div className="flex flex-col px-8 md:px-32 py-16">
            <div className="flex flex-col text-center self-center gap-4 w-full">
                <h2 className='text-primary tracking-widest uppercase text-3xl font-semibold'>Preturile noastre</h2>
                <span className='text-white text-lg'>Avem mai multe optiuni de abonamente, menite sa satisfaca nevoile fiecarui client in mod individual.</span>
            </div>

            <MembershipSubscriptions />
        </div>
    )
}

export default MembershipBody