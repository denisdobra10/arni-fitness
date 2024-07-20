import React from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import AddToInventoryWidget from '../components/inventar/add-to-inventory-widget'
import InventoryItemsWidget from '../components/inventar/inventory-items-list'

function InventarPage() {

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Inventar (Energy Kardio Club)' />
            <AddToInventoryWidget />
            <InventoryItemsWidget />
        </div>
    )
}

export default InventarPage