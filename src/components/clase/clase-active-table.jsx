import React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button } from '@mui/material';

export default function ClaseActiveTable(props) {

    const { classes, handleDelete } = props;

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="table" className=' bg-gray-100'>
                <TableHead>
                    <TableRow>
                        <TableCell>Titlu</TableCell>
                        <TableCell align="center">Rezervari active</TableCell>
                        <TableCell align="center">Max clienti</TableCell>
                        <TableCell align="center">Antrenori</TableCell>
                        {/* <TableCell align="center">Poza</TableCell> */}
                        <TableCell align="center" width={'35%'}>Descriere</TableCell>
                        <TableCell align="center"></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {classes?.map((row) => (
                        <TableRow
                            key={row?.className}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {row?.className}
                            </TableCell>
                            <TableCell align="center">{row?.activeReservations}</TableCell>
                            <TableCell align="center">{row?.maxClients}</TableCell>
                            <TableCell align="center">{row?.coaches?.join(',')}</TableCell>
                            {/* <TableCell align="center"><img src={dummyimage} /></TableCell> */}
                            {/*<TableCell align="left">{isMobile ? row?.description.substring(0, 16) + '...' : row?.description}</TableCell>*/}
                            <TableCell align="left">{row?.description}</TableCell>
                            <TableCell align="center"><Button onClick={() => handleDelete(row?.id)} variant="contained" color="error">Sterge</Button></TableCell>
                        </TableRow>
                    ))}

                </TableBody>
            </Table>
        </TableContainer>
    );
}