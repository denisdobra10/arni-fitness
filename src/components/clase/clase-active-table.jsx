import React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button } from '@mui/material';
import { useState, useEffect } from 'react';
import dummyimage from '../../assets/logo-dreptunghi.jpeg';

function createData(
    name,
    calories,
    fat,
    carbs,
    protein,
) {
    return { name, calories, fat, carbs, protein };
}

const rows = [
    createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
    createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
    createData('Eclair', 262, 16.0, 24, 6.0),
    createData('Cupcake', 305, 3.7, 67, 4.3),
    createData('Gingerbread', 356, 16.0, 49, 3.9),
];

export default function ClaseActiveTable() {
    const [isMobile, setIsMobile] = useState(false);

    useEffect(() => {
        const handleResize = () => {
            setIsMobile(window.innerWidth <= 600);
        };

        window.addEventListener('resize', handleResize);

        handleResize();

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);


    const lorem = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut elit tellus, luctus nec ullamcorper mattis, pulvinar dapibus leo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut elit tellus, luctus nec ullamcorper mattis, pulvinar dapibus leo.';
    const truncatedLorem = `${lorem.substring(0, 16)}...`;

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="table" className=' bg-gray-100'>
                <TableHead>
                    <TableRow>
                        <TableCell>Titlu</TableCell>
                        <TableCell align="center">Rezervari active</TableCell>
                        <TableCell align="center">Max clienti</TableCell>
                        <TableCell align="center">Antrenori</TableCell>
                        <TableCell align="center">Poza</TableCell>
                        <TableCell align="center" width={'35%'}>Descriere</TableCell>
                        <TableCell align="center"></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <TableRow
                            key={row.name}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                Nume clasa
                            </TableCell>
                            <TableCell align="center">22</TableCell>
                            <TableCell align="center">12</TableCell>
                            <TableCell align="center">Ana Pop, Marius Vlad</TableCell>
                            <TableCell align="center"><img src={dummyimage} /></TableCell>
                            <TableCell align="left">{isMobile ? truncatedLorem : lorem}</TableCell>
                            <TableCell align="center"><Button variant="contained" color="error">Sterge</Button></TableCell>
                        </TableRow>
                    ))}

                </TableBody>
            </Table>
        </TableContainer>
    );
}