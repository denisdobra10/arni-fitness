import React, { useState } from 'react';
import { Button, FormControl, TextField } from '@mui/material'
import axios from "../../utils/axios";
import {useData} from "../../lib/data-provider.jsx";

function CheckinWidget() {
    const { displayNotification } = useData();
    const [pin, setPin] = useState(0);

    const handlePinChange = (event) => {
        setPin(event.target.value)
    }

    const handleCheckin = async () => {
        try {
            await axios.post('/admin/checkin', { pin });

            displayNotification('Checkin realizat cu success', 'success');
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare la checkin-ul clientului', 'error')
        }
    }

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Check-in</h2>
            <span className='text-white mb-3'>Introdu codul PIN al unui client si valideaza intrarea.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">

                <FormControl className='gap-3'>
                    <TextField type='number' id="cod-pin" label="Cod pin" variant="outlined"
                        value={pin} onChange={handlePinChange}/>
                    <Button className='w-full sm:w-1/2 self-center' variant="contained"
                        onClick={() => handleCheckin()}>Checkin</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default CheckinWidget