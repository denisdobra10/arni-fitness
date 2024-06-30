import React, {useEffect, useState} from 'react'
import { Button, FormControl, TextField } from '@mui/material'

function AddToInventoryWidget(props) {

    const onClick = () => {
        let numeProdus = document.getElementById('nume-produs').value;
        let cantitate = document.getElementById('cantitate').value;
        console.log(numeProdus, cantitate);
        props.onAdaugaClick({title: numeProdus, quantity: cantitate});
    }

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Adauga in inventar</h2>
            <span className='text-white mb-3'>Introdu numele si cantitatea, pentru a adauga produsul in inventar.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">

                <FormControl className='gap-3'>
                    <TextField id="nume-produs" label="Nume produs" variant="outlined" />
                    <TextField type='number' id="cantitate" label="Cantitate" variant="outlined" />
                    <Button className='w-full sm:w-1/2 self-center' variant="contained" onClick={() => onClick()}>Adauga</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default AddToInventoryWidget
