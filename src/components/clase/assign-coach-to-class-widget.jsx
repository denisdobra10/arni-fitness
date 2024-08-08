import React from 'react'
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';


function AssignCoachToClassWidget(props) {
    const { classes, coaches, assignCoach } = props;

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
                    placeholder="Selecteaza clasa"
                    onChange={handleClassChange}
                >
                    {classes?.map((cls) => (
                        <MenuItem key={cls.className} value={cls.id}>{cls.className}</MenuItem>
                    ))}
                </Select>

                <InputLabel htmlFor="select-coach-label" id="select-coach-label">Selecteaza antrenor</InputLabel>
                <Select
                    labelId="select-coach-label"
                    id="select-coach"
                    placeholder="Selecteaza antrenor"
                    value={coach}
                    onChange={handleCoachChange}
                >
                    {coaches?.map((coach) => (
                        <MenuItem key={coach.name} value={coach.id}>{coach.name}</MenuItem>
                    ))}
                </Select>

                <Button className='w-full sm:w-1/2 self-center' variant="contained"
                        onClick={() => assignCoach(gymClass, coach)}>Asociaza antrenor clasei</Button>


            </div>
        </div>
    );
}

export default AssignCoachToClassWidget