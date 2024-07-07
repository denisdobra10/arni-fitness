import React, { useRef, useState } from 'react';
import { Button, FormControl, TextField } from '@mui/material';
import { useData } from '../../lib/data-provider';
import axios from '../../utils/axios'
import { AxiosError } from 'axios';

function CreateAbonamentWidget() {

    const { displayNotification } = useData();

    const [formData, setFormData] = useState({
        title: '',
        price: 0,
        entries: 0,
        availability: 0,
        description: ''
    });

    const titleRef = useRef<HTMLInputElement | null>(null);
    const priceRef = useRef<HTMLInputElement | null>(null);
    const entriesRef = useRef<HTMLInputElement | null>(null);
    const availability = useRef<HTMLInputElement | null>(null);
    const description = useRef<HTMLInputElement | null>(null);

    const handleChange = () => {

        setFormData({
            title: titleRef.current?.value || '',
            price: parseFloat(priceRef.current?.value || '0'),
            entries: parseInt(entriesRef.current?.value || '0', 10),
            availability: parseInt(availability.current?.value || '0', 10),
            description: description.current?.value || ''
        });
    };

    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();

        try {
            console.log(formData)
            const response = await axios.post('/admin/memberships', formData);

            displayNotification('Abonament creat cu succes', 'success');
        } catch (err: any) {
            const error = err as AxiosError;
            displayNotification(error.response?.data || 'A aparut o eroare la crearea abonamentului', 'error')
        }
    };

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Creeaza un abonament</h2>
            <span className='text-white mb-3'>Creeaza un nou abonament disponibil pentru clientii tai, completand formularul de mai jos.</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Formular Abonament</h2>

                <FormControl className='gap-3' onSubmit={handleSubmit}>
                    <TextField
                        id="titlu-abonament"
                        label="Titlul abonamentului"
                        variant="outlined"
                        inputRef={titleRef}
                        onChange={handleChange}
                    />
                    <TextField
                        type='number'
                        id="pret-abonament"
                        label="Pretul abonamentului"
                        variant="outlined"
                        inputRef={priceRef}
                        onChange={handleChange}
                    />
                    <TextField
                        type='number'
                        id="numar-intrari-abonament"
                        label="Numarul de intrari posibile"
                        variant="outlined"
                        inputRef={entriesRef}
                        onChange={handleChange}
                    />
                    <TextField
                        type='number'
                        id="valabilitate-abonament"
                        label="Cate zile este valabil abonamentul"
                        variant="outlined"
                        inputRef={availability}
                        onChange={handleChange}
                    />
                    <TextField
                        multiline
                        rows={'4'}
                        id="descriere-abonament"
                        label="Descrierea abonamentului"
                        variant="outlined"
                        inputRef={description}
                        onChange={handleChange}
                    />
                    <Button
                        className='w-full sm:w-1/2 self-center'
                        variant="contained"
                        onClick={handleSubmit}
                    >
                        Creeaza abonament
                    </Button>
                </FormControl>
            </div>
        </div>
    )
}

export default CreateAbonamentWidget;
