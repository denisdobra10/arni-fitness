import React from 'react';
import Modal from 'react-modal';
import { FormControl, InputLabel, Select, MenuItem, TextField, Button } from '@mui/material';

Modal.setAppElement(document.body);

function CalendarModal({ isOpen, onClose }) {

    const customStyles = {
        overlay: {
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            zIndex: 1000,
        },
        content: {
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            backgroundColor: 'white',
            border: 'none',
            borderRadius: '8px',
            padding: '20px',
            width: '50%',
        },
    };

    if (window.innerWidth <= 600) {
        customStyles.content.width = '90%';
    }

    return (
        <Modal isOpen={isOpen} onRequestClose={onClose} style={customStyles}>
            <h2 className='text-lg font-semibold text-center text-primary'>Seteaza un antrenament</h2>
            <FormControl fullWidth sx={{ my: 2 }}>
                <InputLabel id="select-class-label">Selecteaza clasa</InputLabel>
                <Select
                    labelId="select-class-label"
                    id="select-class"
                    label="Selecteaza clasa"
                >
                    <MenuItem value={1}>Clasa 1</MenuItem>
                    <MenuItem value={2}>Clasa 2</MenuItem>
                    <MenuItem value={3}>Clasa 3</MenuItem>
                </Select>
            </FormControl>
            <FormControl fullWidth sx={{ my: 2 }}>
                <InputLabel id="select-coach-label">Selecteaza antrenor</InputLabel>
                <Select
                    labelId="select-coach-label"
                    id="select-coach"
                    label="Selecteaza antrenor"
                >
                    <MenuItem value={1}>Antrenor 1</MenuItem>
                    <MenuItem value={2}>Antrenor 2</MenuItem>
                    <MenuItem value={3}>Antrenor 3</MenuItem>
                </Select>
            </FormControl>
            <FormControl fullWidth sx={{ my: 2 }}>
                <TextField
                    id="time"
                    label="Ora de antrenament"
                    type="time"
                    InputLabelProps={{
                        shrink: true,
                    }}
                />
            </FormControl>
            <FormControl fullWidth sx={{ my: 2 }}>
                <TextField
                    id="observations"
                    label="Observatii pentru clienti"
                    multiline
                    rows={4}
                />
            </FormControl>
            <Button onClick={onClose} variant="contained">Seteaza antrenament</Button>
        </Modal>
    );
}

export default CalendarModal;
