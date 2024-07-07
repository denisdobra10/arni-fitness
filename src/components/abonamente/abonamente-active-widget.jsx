import React from 'react'
import AbonamenteTable from './abonamente-table';

function AbonamenteActiveWidget({ memberships = [] }) {
    return (
        <div className='flex flex-col w-full bg-white rounded-lg p-3 my-10 text-center sm:text-left sm:p-10 '>
            <h2 className='text-3xl text-primary font-semibold capitalize'>Abonamente active</h2>
            <span className='text-gray-500 mb-6'>Abonamentele active si disponibile clientilor tai</span>

            <AbonamenteTable memberships={memberships} />
        </div>
    )
}

export default AbonamenteActiveWidget