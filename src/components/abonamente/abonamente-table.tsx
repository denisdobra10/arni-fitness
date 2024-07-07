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
import { AxiosError } from 'axios';
import { useData } from '../../lib/data-provider';
import axios from '../../utils/axios';


interface Membership {
    id: string;
    name: string;
    sold: number;
    price: number;
    entries: number;
    duration: string;
    description: string;

}


export default function AbonamenteTable({ memberships = [] }) {

    const { displayNotification } = useData();
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


    const handleDelete = async (id: any) => {

        try {
            await axios.delete(`/admin/memberships/${id}`);
            displayNotification('Abonamentul a fost sters cu succes', 'success')
        } catch (err: any) {
            const error = err as AxiosError;
            displayNotification(error.response?.data || 'A aparut o eroare la stergerea abonamentului', 'error')
        }
    }

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="table" className=' bg-gray-100'>
                <TableHead>
                    <TableRow>
                        <TableCell>Titlu</TableCell>
                        <TableCell align="center">Cumparari</TableCell>
                        <TableCell align="center">Pret</TableCell>
                        <TableCell align="center">Intrari</TableCell>
                        <TableCell align="center">Valabilitate</TableCell>
                        <TableCell align="center" width={'50%'}>Descriere</TableCell>
                        <TableCell align="center"></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {memberships.map((membership: Membership) => (
                        <TableRow
                            key={membership.name}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {membership.name}
                            </TableCell>
                            <TableCell align="center">{membership.sold}</TableCell>
                            <TableCell align="center">{membership.price} lei</TableCell>
                            <TableCell align="center">{membership.entries}</TableCell>
                            <TableCell align="center">{membership.duration}</TableCell>
                            <TableCell align="left">{isMobile ? membership.description.substring(0, 16) + '...' : membership.description}</TableCell>
                            <TableCell align="center"><Button onClick={() => handleDelete(membership.id)} variant="contained" color="error">Sterge</Button></TableCell>
                        </TableRow>
                    ))}

                </TableBody>
            </Table>
        </TableContainer >
    );
}