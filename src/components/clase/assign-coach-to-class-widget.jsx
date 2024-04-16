import React from 'react'
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';


function AssignCoachToClassWidget() {

    const [gymClass, setGymClass] = React.useState('');
    const [coach, setCoach] = React.useState('');

    const handleClassChange = (event) => {
        setGymClass(event.target.value);
    };

    const handleCoachChange = (event) => {
        setCoach(event.target.value);
    };

    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Asociaza antrenor la o clasa</h2>
            <span className='text-white mb-3'>Seteaza antrenorul potrivit pentru o anumita clasa</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Adauga Antrenor la Clasa</h2>

                <InputLabel id="select-class-label">Selecteaza clasa</InputLabel>
                <Select
                    labelId="select-class-label"
                    id="select-class"
                    value={gymClass}
                    onChange={handleClassChange}
                >
                    <MenuItem value={10}>Nume clasa 1</MenuItem>
                    <MenuItem value={20}>Nume clasa 2</MenuItem>
                    <MenuItem value={30}>Nume clasa 3</MenuItem>
                </Select>

                <InputLabel htmlFor="select-coach-label" id="select-coach-label">Selecteaza antrenor</InputLabel>
                <Select
                    labelId="select-coach-label"
                    id="select-coach"
                    value={coach}
                    onChange={handleCoachChange}
                >
                    <MenuItem value={10}>Nume antrenor 1</MenuItem>
                    <MenuItem value={20}>Nume antrenor 2</MenuItem>
                    <MenuItem value={30}>Nume antrenor 3</MenuItem>
                </Select>

                <Button className='w-full sm:w-1/2 self-center' variant="contained">Asociaza antrenor clasei</Button>


            </div>
        </div>
    );
}

export default AssignCoachToClassWidget