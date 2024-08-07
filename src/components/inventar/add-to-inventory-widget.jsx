import React, { useState } from 'react'
import { Button, FormControl, TextField } from '@mui/material'
import axios from "../../utils/axios";
import {useData} from "../../lib/data-provider";

function AddToInventoryWidget() {
    const [title, setTitle] = useState('');
    const [quantity, setQuantity] = useState(0);
    const { displayNotification } = useData();

    const handleNameChange = (event) => {
        setTitle(event.target.value)
    }

    const handleQuantityChange = (event) => {
        setQuantity(event.target.value)
    }

    const handleAddItem = async (item) => {
        try {
            await axios.post(`/admin/items`, item);

            window.location.reload();
            displayNotification('Cantitatea a fost scazuta cu succes', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Adauga in inventar</h2>
            <span className='text-white mb-3'>Introdu numele si cantitatea, pentru a adauga produsul in inventar.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">

                <FormControl className='gap-3'>
                    <TextField id="nume-produs" label="Nume produs" variant="outlined"
                               value={title} onChange={handleNameChange}/>
                    <TextField type='number' id="cantitate" label="Cantitate" variant="outlined"
                               value={quantity} onChange={handleQuantityChange}/>
                    <Button className='w-full sm:w-1/2 self-center' variant="contained" onClick={() => handleAddItem({title, quantity})}>Adauga</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default AddToInventoryWidget