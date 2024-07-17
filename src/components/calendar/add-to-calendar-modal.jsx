import React, {useState, useEffect} from 'react';
import Modal from 'react-modal';
import { FormControl, InputLabel, Select, MenuItem, TextField, Button } from '@mui/material';
import { useData } from '../../lib/data-provider.jsx';
import axios from "../../utils/axios";

Modal.setAppElement(document.body);

function CalendarModal({ isOpen, onClose, data }) {
    const { displayNotification } = useData();
    const [coaches, setCoaches] = useState([]);
    const [classes, setClasses] = useState([]);

    const [coach, setCoach] = useState('');
    const [selectedClass, setSelectedClass] = useState('');
    const [time, setTime] = useState('');
    const [observations, setObservations] = useState('');

    function getClassName(classId) {
        const cls = classes.find(cls => cls.id === classId);
        return cls ? cls.className : '';
    }

    const resetStates = () => {
        setCoach('');
        setSelectedClass('');
        setTime('');
        setObservations('');
    }

    const handleCoachChange = (event) => {
        setCoach(event.target.value);
    }

    const handleClassChange = (event) => {
        setSelectedClass(event.target.value);
    }

    const handleTimeChange = (event) => {
        setTime(event.target.value);
    }

    const handleObservationsChange = (event) => {
        setObservations(event.target.value);
    }

    useEffect(() => {
        const fetchClassesAndCoaches = async () => {
            try {
                const response = await axios.get('/admin/classes/coaches');
                setCoaches(response.data.coaches);
                setClasses(response.data.classes);
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        fetchClassesAndCoaches();
    }, []);

    const handleCreateSession = async () => {
        try {
            await axios.post('/admin/sessions', {
                name: getClassName(selectedClass),
                classId: selectedClass,
                coachId: coach,
                date: `${data}T${time}`,
                observations: observations
            });

            onClose();
            resetStates();
            displayNotification('Antrenamentul a fost setat cu succes', 'success');
            // window.location.reload();
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error');
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

    return (
        <Modal isOpen={isOpen} onRequestClose={onClose} style={customStyles}>
            <h2 className='text-lg font-semibold text-center text-primary'>Seteaza un antrenament</h2>
            <FormControl fullWidth sx={{ my: 2 }}>
                <InputLabel id="select-class-label">Selecteaza clasa</InputLabel>
                <Select
                    labelId="select-class-label"
                    id="select-class"
                    label="Selecteaza clasa"
                    value={selectedClass}
                    onChange={handleClassChange}
                >
                    {classes.map(cls => <MenuItem key={cls.id} value={cls.id}>{cls.className}</MenuItem>)}
                </Select>
            </FormControl>
            <FormControl fullWidth sx={{ my: 2 }}>
                <InputLabel id="select-coach-label">Selecteaza antrenor</InputLabel>
                <Select
                    labelId="select-coach-label"
                    id="select-coach"
                    label="Selecteaza antrenor"
                    value={coach}
                    onChange={handleCoachChange}
                >
                    {coaches.map(coach => <MenuItem key={coach.id} value={coach.id}>{coach.name}</MenuItem>)}
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
                    value={time}
                    onChange={handleTimeChange}
                />
            </FormControl>
            <FormControl fullWidth sx={{ my: 2 }}>
                <TextField
                    id="observations"
                    label="Observatii pentru clienti"
                    multiline
                    rows={4}
                    value={observations}
                    onChange={handleObservationsChange}
                />
            </FormControl>
            <Button variant="contained" onClick={() => handleCreateSession()}>Seteaza antrenament</Button>
        </Modal>
    );
}

export default CalendarModal;
