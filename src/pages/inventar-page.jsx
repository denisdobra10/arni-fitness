import React, {useEffect} from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import AddToInventoryWidget from '../components/inventar/add-to-inventory-widget'
import InventoryItemsWidget from '../components/inventar/inventory-items-list'

function InventarPage() {
    const [items, setItems] = React.useState([])
    const getAllItems = async () => {
        let url = 'http://localhost:8080/api/inventory/items';

        let response = await fetch(url)
        let data = await response.json()
        let result = await data

        setItems(result)
    }

    const addItem = async (item) => {
        let url = 'http://localhost:8080/api/inventory/items';

        let response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        })
        let data = await response.json()
        let result = await data

        setItems([...items, result])
    }

    useEffect(() => {
        getAllItems();
    }, []);

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Inventar (Energy Kardio Club)' />
            <AddToInventoryWidget onAdaugaClick={addItem}/>
            <InventoryItemsWidget items={items}/>
        </div>
    )
}

export default InventarPage
