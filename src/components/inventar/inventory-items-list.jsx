import React from 'react'
import InventarTable from './inventar-table'

function InventoryItemsWidget() {
    return (
        <div className='flex flex-col w-full bg-white rounded-lg p-3 my-10 text-center sm:text-left sm:p-10 '>
            <h2 className='text-3xl text-primary font-semibold capitalize'>Inventar</h2>
            <span className='text-gray-500 mb-6'>Lista cu toate produsele din inventar</span>

            <InventarTable />
        </div>
    )
}

export default InventoryItemsWidget
