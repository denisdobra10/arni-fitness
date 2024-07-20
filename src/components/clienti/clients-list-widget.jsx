import React, { useState } from 'react';
import { TextField, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Button } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

function ClientListWidget({ clients }) {
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const clientsPerPage = 10;

    function formatDate(dateString) {
        const date = new Date(dateString);

        const dateOptions = { year: 'numeric', month: 'long', day: 'numeric' };
        const timeOptions = { hour: '2-digit', minute: '2-digit', second: '2-digit' };

        const formattedDate = date.toLocaleDateString(undefined, dateOptions);
        const formattedTime = date.toLocaleTimeString(undefined, timeOptions);

        return `${formattedDate} at ${formattedTime}`;
    }

    function showLastPayment(paymentLink) {
        if (!paymentLink) {
            return;
        }

        window.open(paymentLink, '_blank');
    }


    const handleSearch = (event) => {
        setSearchTerm(event.target.value);
    };

    const filteredClients = clients.filter((client) =>
        client.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const indexOfLastClient = currentPage * clientsPerPage;
    const indexOfFirstClient = indexOfLastClient - clientsPerPage;
    const currentClients = filteredClients.slice(indexOfFirstClient, indexOfLastClient);
    const totalPages = Math.ceil(filteredClients.length / clientsPerPage);

    const handleNextPage = () => {
        setCurrentPage((prevPage) => prevPage + 1);
    };

    const handlePrevPage = () => {
        setCurrentPage((prevPage) => prevPage - 1);
    };

    return (
        <div className='bg-white w-full rounded-lg my-6 p-6'>
            <TextField
                label="Cauta dupa nume"
                variant="outlined"
                fullWidth
                value={searchTerm}
                onChange={handleSearch}
                InputProps={{
                    endAdornment: (
                        <IconButton>
                            <SearchIcon />
                        </IconButton>
                    ),
                }}
            />
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Nume</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Telefon</TableCell>
                            <TableCell>Pin</TableCell>
                            <TableCell>Data Inregistrarii</TableCell>
                            <TableCell>Abonament Activ</TableCell>
                            <TableCell>Actiuni</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {currentClients.map((client) => (
                            <TableRow key={client?.id}>
                                <TableCell>{client?.name}</TableCell>
                                <TableCell>{client?.email}</TableCell>
                                <TableCell>{client?.phoneNumber}</TableCell>
                                <TableCell>{client?.pin}</TableCell>
                                <TableCell>{formatDate(client?.createdAt)}</TableCell>
                                <TableCell>{client?.hasActiveSubscription ? 'Da' : 'Nu'}</TableCell>
                                <TableCell>
                                    <Button variant="contained" disabled={!client?.lastPaymentLink} onClick={() => showLastPayment(client?.lastPaymentLink)}>Vezi ultima factura</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <div className='flex flex-row justify-center items-center text-primary'>
                <Button onClick={handlePrevPage} disabled={currentPage === 1}>Inapoi</Button>
                <p style={{ margin: '0 10px' }}>Pagina {currentPage} din {totalPages}</p>
                <Button onClick={handleNextPage} disabled={currentPage === totalPages}>Inainte</Button>
            </div>
        </div>
    );
}

export default ClientListWidget;
