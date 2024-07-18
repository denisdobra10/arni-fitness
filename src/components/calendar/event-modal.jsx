import React, {useEffect} from 'react';
import Modal from 'react-modal';
import { FormControl, InputLabel, Select, MenuItem, TextField, Button } from '@mui/material';
import axios from '../../utils/axios';
import {useData} from "../../lib/data-provider.jsx";

Modal.setAppElement(document.body);

export function EventModal({ selectedEvent, isOpen, onClose, setSessions}) {
    const { displayNotification } = useData();

    useEffect(() => {
        console.log(selectedEvent);
    }, [selectedEvent]);

    const handleDeleteSession = async (sessionId) => {
        try {
            // Make a request to the backend to delete the session
            await axios.delete(`/admin/sessions/${sessionId}`);
            setSessions(prevState => prevState.filter((item) => item.id !== sessionId));
            onClose();
            displayNotification('Sesiunea a fost stearsa cu succes', 'success');
        } catch (err) {
            displayNotification(err.response.data, 'error');
            console.log(err);
        }
    }

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

    const options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};

    return (
        <Modal isOpen={isOpen} onRequestClose={onClose} style={customStyles}>
            <h2 className='text-lg font-semibold text-center text-primary'>Informatii</h2>
            {selectedEvent && (
                <>
                    <h2 className='text-primary text-lg'>Clasa: <span
                        className='font-bold'>{selectedEvent?.title}</span></h2>
                    {/*<h2 className='text-primary text-lg'>Ora: <span className='font-bold'>14:15</span></h2>*/}
                    <h2 className='text-primary text-lg'>Data: <span
                        className='font-bold'>{selectedEvent?.start?.toLocaleDateString('ro-RO', options)}</span>
                    </h2>
                    <h2 className='text-primary text-lg'>Antrenor: <span
                        className='font-bold'>{selectedEvent?.extendedProps?.coachName}</span></h2>
                    <h2 className='text-primary text-lg'>Clienti: <span className='font-bold'>{selectedEvent?.extendedProps?.clients?.join(', ')}</span></h2>
                </>
            )}

            <div className="mt-6 text-center">
                <Button variant="contained" color='error' className='' onClick={() => handleDeleteSession(selectedEvent?.extendedProps?.sessionId)}>Sterge antrenament</Button>
            </div>
        </Modal>
    );
}

export default EventModal;