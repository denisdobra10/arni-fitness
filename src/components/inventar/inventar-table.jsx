import React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { useState, useEffect } from 'react';
import { IconButton } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';


function createData(
    name,
    calories,
    fat,
    carbs,
    protein,
) {
    return { name, calories, fat, carbs, protein };
}

export default function InventarTable(props) {
    const [isMobile, setIsMobile] = useState(false);

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
                    {props.items.map((item, number) => (
                        <TableRow
                            key={number}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {item.title}
                            </TableCell>
                            <TableCell align="center">{item.quantity}</TableCell>
                            <TableCell align='center'>
                                <div>
                                    <IconButton color="primary" aria-label="plus">
                                        <AddIcon />
                                    </IconButton>
                                    <IconButton color="primary" aria-label="minus">
                                        <RemoveIcon />
                                    </IconButton>
                                </div>
                            </TableCell>
                        </TableRow>
                    ))}

                </TableBody>
            </Table>
        </TableContainer>
    );
}
