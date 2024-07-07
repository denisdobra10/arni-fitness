import React, { useEffect } from 'react'
import { Button, FormControl, TextField } from '@mui/material'
import { useState } from 'react';
import { useData } from '../../lib/data-provider';
import axios from '../../utils/axios';

function CreateClassWidget() {

    const { displayNotification } = useData();

    const [formData, setFormData] = useState({
        title: '',
        description: '',
        availableSpots: 0
    });
    const [selectedFile, setSelectedFile] = useState(null);

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            await axios.post('/admin/classes', formData);

            displayNotification('Clasa a fost creata cu succes', 'success')
            window.location.reload();
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    };

    const handleChange = (event) => {
        setFormData({
            ...formData,
            [event.target.name]: event.target.value
        });
    };

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Creeaza o clasa</h2>
            <span className='text-white mb-3'>Creeaza o clasa noua, disponibila spre a fi rezervata, pentru clientii tai</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Formular Clasa</h2>

                <form onSubmit={handleSubmit}>
                    <FormControl className='flex flex-col gap-3 w-full'>
                        <TextField
                            id="titlu-clasa"
                            name="title"
                            label="Titlul clasei"
                            variant="outlined"
                            value={formData.title}
                            onChange={handleChange}
                        />
                        <TextField
                            multiline
                            rows={'4'}
                            id="descriere-clasa"
                            name="description"
                            label="Descrierea clasei"
                            variant="outlined"
                            value={formData.description}
                            onChange={handleChange}
                        />
                        {/* <div className="text-left">
                            <input
                                accept="image/*"
                                style={{ display: 'none' }}
                                id="file-input"
                                type="file"
                                onChange={handleFileChange}
                            />
                            <label htmlFor="file-input">
                                <Button variant="outlined" component="span">
                                    Selectează imaginea
                                </Button>
                            </label>
                            {selectedFile && <p className='text-gray-500'>Fisier selectat: {selectedFile.name}</p>}

                        </div> */}
                        <TextField
                            type='number'
                            id="max-clienti-clasa"
                            name="availableSpots"
                            label="Numarul maxim de clienti"
                            variant="outlined"
                            value={formData.availableSpots}
                            onChange={handleChange}
                        />
                        <Button className='w-full sm:w-1/2 self-center' variant="contained" type="submit">Creeaza clasa</Button>
                    </FormControl>
                </form>
            </div>
        </div>
    )
}

export default CreateClassWidget