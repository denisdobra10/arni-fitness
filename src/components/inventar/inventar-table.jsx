import React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useState, useEffect } from 'react';
import {Button, IconButton} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import {useData} from "../../lib/data-provider.jsx";
import axios from "../../utils/axios";

export default function InventarTable() {
    const { displayNotification } = useData();
    const [items, setItems] = useState([])
    const [isMobile, setIsMobile] = useState(false);

    useEffect(() => {
        const handleResize = () => {
            setIsMobile(window.innerWidth <= 600);
        };

        const fetchItems = async () => {
            try {
                const response = await axios.get('/admin/items');
                setItems(response.data);

                console.log(response.data)
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        window.addEventListener('resize', handleResize);


        handleResize();
        fetchItems();

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    const handleDelete = async (id) => {
        if (id === null || id === undefined || id === '') {
            displayNotification('A aparut o eroare neasteptata', 'error');
            return;
        }

        try {
            await axios.delete(`/admin/items/${id}`);
            setItems(items.filter((item) => item.id !== id));

            displayNotification('Produsul a fost sters cu succes', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }

    const handleIncrease = async (id) => {
        if (id === null || id === undefined || id === '') {
            displayNotification('A aparut o eroare neasteptata', 'error');
            return;
        }

        try {
            await axios.post(`/admin/items/${id}/increase`, {});

            window.location.reload();
            displayNotification('Cantitatea a fost crescuta cu succes', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }

    const handleDecrease = async (id) => {
        if (id === null || id === undefined || id === '') {
            displayNotification('A aparut o eroare neasteptata', 'error');
            return;
        }

        try {
            await axios.post(`/admin/items/${id}/decrease`, {});

            window.location.reload();
            displayNotification('Cantitatea a fost scazuta cu succes', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="table" className=' bg-gray-100'>
                <TableHead>
                    <TableRow>
                        <TableCell>Produs</TableCell>
                        <TableCell align="center">Cantitate</TableCell>
                        <TableCell align="center">Actiuni</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {items.map((row) => (
                        <TableRow
                            key={row?.title}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {row?.title}
                            </TableCell>
                            <TableCell align="center">{row?.quantity}</TableCell>
                            <TableCell align='center'>
                                <div>
                                    <IconButton color="primary" aria-label="plus" onClick={() => handleIncrease(row?.id)}>
                                        <AddIcon />
                                    </IconButton>
                                    <IconButton color="primary" aria-label="minus" onClick={() => handleDecrease(row?.id)}>
                                        <RemoveIcon />
                                    </IconButton>
                                    <Button onClick={() => handleDelete(row?.id)} variant="contained" color="error">Sterge</Button>
                                </div>
                            </TableCell>
                        </TableRow>
                    ))}

                </TableBody>
            </Table>
        </TableContainer>
    );
}