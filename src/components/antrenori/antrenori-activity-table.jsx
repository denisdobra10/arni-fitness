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

export default function AntrenoriActivityTable(props) {
    const { classStatistics } = props;
    const [isMobile, setIsMobile] = useState(false);

    useEffect(() => {
        console.log()
        const handleResize = () => {
            setIsMobile(window.innerWidth <= 600);
        };

        window.addEventListener('resize', handleResize);

        handleResize();

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);


    return (
        <TableContainer component={Paper}>
            <Table aria-label="table" className=' bg-gray-100'>
                <TableHead>
                    <TableRow>
                        <TableCell>Clienti in total</TableCell>
                        <TableCell align="center">Clienti saptamana</TableCell>
                        <TableCell align="center">Clienti luna</TableCell>
                        <TableCell align="center">Clienti azi</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    <TableRow
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                        <TableCell component="th" scope="row">{classStatistics?.totalClients}</TableCell>
                        <TableCell align="center">{classStatistics?.weeklyClients}</TableCell>
                        <TableCell align="center">{classStatistics?.monthlyClients}</TableCell>
                        <TableCell align="center">{classStatistics?.todayClients}</TableCell>
                    </TableRow>

                </TableBody>
            </Table>
        </TableContainer>
    );
}