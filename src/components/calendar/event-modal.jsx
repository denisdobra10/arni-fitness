import React from 'react';
import Modal from 'react-modal';
import { FormControl, InputLabel, Select, MenuItem, TextField, Button } from '@mui/material';

Modal.setAppElement(document.body);

function EventModal({ isOpen, onClose, data }) {

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

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };

    return (
        <Modal isOpen={isOpen} onRequestClose={onClose} style={customStyles}>
            <h2 className='text-lg font-semibold text-center text-primary'>Informatii</h2>
            {data && (
                <>
                    <h2 className='text-primary text-lg'>Clasa: <span className='font-bold'>{data.title}</span></h2>
                    <h2 className='text-primary text-lg'>Ora: <span className='font-bold'>14:15</span></h2>
                    <h2 className='text-primary text-lg'>Data: <span className='font-bold'>{data.start.toLocaleDateString('ro-RO', options)}</span></h2>
                    <h2 className='text-primary text-lg'>Antrenor: <span className='font-bold'>Nume Antrenor</span></h2>
                    <h2 className='text-primary text-lg'>Clienti: <span className='font-bold'>Nume Client, Nume Client, Nume Client, Nume Client, Nume Client, Nume Client</span></h2>
                </>
            )}

            <div className="mt-6 text-center">
                <Button onClick={onClose} variant="contained" color='error' className=''>Sterge antrenament</Button>

            </div>
        </Modal>
    );
}

export default EventModal;
