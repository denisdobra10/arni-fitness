import React, { useState } from 'react'
import ClaseActiveTable from './clase-active-table'


function ClaseActiveWidget() {

    return (
        <div className='flex flex-col w-full bg-white rounded-lg p-3 my-10 text-center sm:text-left sm:p-10 '>
            <h2 className='text-3xl text-primary font-semibold capitalize'>Clase active</h2>
            <span className='text-gray-500 mb-6'>Clasele active si disponibile clientilor tai</span>

            <ClaseActiveTable />
        </div>
    )
}

export default ClaseActiveWidget