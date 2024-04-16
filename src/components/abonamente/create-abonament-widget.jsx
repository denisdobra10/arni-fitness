import React from 'react'
import { Button, FormControl, TextField } from '@mui/material'

function CreateAbonamentWidget() {
    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Creeaza un abonament</h2>
            <span className='text-white mb-3'>Creeaza un nou abonament disponibil pentru clientii tai, completand formularul de mai jos.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Formular Abonament</h2>

                <FormControl className='gap-3'>
                    <TextField id="titlu-abonament" label="Titlul abonamentului" variant="outlined" />
                    <TextField type='number' id="pret-abonament" label="Pretul abonamentului" variant="outlined" />
                    <TextField type='number' id="numar-intrari-abonament" label="Numarul de intrari posibile" variant="outlined" />
                    <TextField type='number' id="valabilitate-abonament" label="Cate zile este valabil abonamentul" variant="outlined" />
                    <TextField multiline rows={'4'} id="descriere-abonament" label="Descrierea abonamentului" variant="outlined" />
                    <Button className='w-full sm:w-1/2 self-center' variant="contained">Creeaza abonament</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default CreateAbonamentWidget