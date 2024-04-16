import React from 'react'
import { Button, FormControl, TextField } from '@mui/material'
import { useState } from 'react';

function CreateClassWidget() {

    const [selectedFile, setSelectedFile] = useState(null);

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };


    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Creeaza o clasa</h2>
            <span className='text-white mb-3'>Creeaza o clasa noua, disponibila spre a fi rezervata, pentru clientii tai</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Formular Clasa</h2>

                <FormControl className='gap-3'>
                    <TextField id="titlu-clasa" label="Titlul clasei" variant="outlined" />
                    <TextField multiline rows={'4'} id="descriere-clasa" label="Descrierea clasei" variant="outlined" />
                    <div className="text-left">
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

                    </div>
                    <TextField type='number' id="max-clienti-clasa" label="Numarul maxim de clienti" variant="outlined" />
                </FormControl>
                <Button className='w-full sm:w-1/2 self-center' variant="contained">Creeaza clasa</Button>

            </div>
        </div>
    )
}

export default CreateClassWidget