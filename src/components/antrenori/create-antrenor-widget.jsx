import React from 'react'
import { Button, FormControl, TextField } from '@mui/material'

function CreateAntrenorWidget() {
    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Creeaza un antrenor</h2>
            <span className='text-white mb-3'>Creeaza un nou antrenor in sistem.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Adauga Antrenor</h2>

                <FormControl className='gap-3'>
                    <TextField id="nume-antrenor" label="Nume antrenor" variant="outlined" />
                    <TextField multiline rows={'4'} id="descriere-antrenor" label="Descriere" variant="outlined" />
                    <Button className='w-full sm:w-1/2 self-center' variant="contained">Creeaza antrenor</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default CreateAntrenorWidget