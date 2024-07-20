import React, {useState} from 'react'
import { Button, FormControl, TextField } from '@mui/material'
import axios from "../../utils/axios";
import { useData } from "../../lib/data-provider.jsx";

function CreateAntrenorWidget() {
    const { displayNotification } = useData();
    const [coachName, setCoachName] = useState('');
    const [coachDescription, setCoachDescription] = useState('');

    const handleCoachNameChange = (event) => {
        setCoachName(event.target.value)
    }

    const handleCoachDescriptionChange = (event) => {
        setCoachDescription(event.target.value)
    }

    const handleCreateCoach = async () => {
        try {
            await axios.post('/admin/coaches', {name: coachName, description: coachDescription});

            displayNotification('Antrenorul a fost creat cu success', 'success');

            window.location.reload();
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare la crearea antrenorului', 'error')
        }
    }

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Creeaza un antrenor</h2>
            <span className='text-white mb-3'>Creeaza un nou antrenor in sistem.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Adauga Antrenor</h2>

                <FormControl className='gap-3'>
                    <TextField id="nume-antrenor" label="Nume antrenor" variant="outlined"
                        value={coachName} onChange={handleCoachNameChange}/>
                    <TextField multiline rows={'4'} id="descriere-antrenor" label="Descriere" variant="outlined"
                        value={coachDescription} onChange={handleCoachDescriptionChange}/>
                    <Button className='w-full sm:w-1/2 self-center' variant="contained" onClick={() => handleCreateCoach()}>Creeaza antrenor</Button>
                </FormControl>

            </div>
        </div>
    )
}

export default CreateAntrenorWidget