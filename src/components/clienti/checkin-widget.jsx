import { Button, FormControl, TextField } from '@mui/material'
import React from 'react'

function CheckinWidget() {
    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Check-in</h2>
            <span className='text-white mb-3'>Introdu codul PIN al unui client si valideaza intrarea.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">

                <FormControl className='gap-3'>
                    <TextField type='number' id="cod-pin" label="Cod pin" variant="outlined" />
                    <Button className='w-full sm:w-1/2 self-center' variant="contained">Checkin</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default CheckinWidget