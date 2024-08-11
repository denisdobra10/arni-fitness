import React from 'react'
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import axios from "../../utils/axios";
import {useData} from "../../lib/data-provider.jsx";

function BuyMembershipForClient(props) {
    const { clients, memberships } = props;
    const { displayNotification } = useData();

    const [client, setClient] = React.useState('');
    const [membership, setMembership] = React.useState('');

    const handleClientChange = (event) => {
        setClient(event.target.value);
    };

    const handleMembershipChange = (event) => {
        setMembership(event.target.value);
    };

    const handleBuyMembership = async () => {
        try {
            await axios.post(`/admin/setMembership`, { clientId: client, membershipId: membership });
            window.location.reload();

            displayNotification('Abonamentul a fost setat cu success!', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }


    return (
        <div className="flex flex-col w-full">
            <h2 className='text-3xl text-primary mt-10 mb-3 font-medium'>Seteaza abonament pentru utilizator</h2>
            <span className='text-white mb-3'>Selecteaza utilizatorul si atribuie un abonament</span>

            <div className="flex flex-col bg-white p-6 rounded-lg text-center w-full sm:w-1/2 self-center gap-3">
                <h2 className='text-xl sm:text-3xl font-bold uppercase text-gray-600 mb-6'>Alege utilizatorul</h2>

                <InputLabel id="select-class-label">Selecteaza utilizator</InputLabel>
                <Select
                    labelId="select-class-label"
                    id="select-class"
                    value={client}
                    placeholder="Selecteaza clasa"
                    // className="max-h-xl overflow-y-auto"
                    MenuProps={{
                        PaperProps: {
                            style: {
                                maxHeight: 300, // Set the max height of the dropdown
                                overflowY: 'auto', // Enable vertical scrolling
                            },
                        },
                    }}
                    onChange={handleClientChange}
                >
                    {clients?.sort((c1, c2) => {
                        return c1.name.localeCompare(c2.name);
                    }).filter(c =>
                        !c.hasActiveSubscription).map((c) => (
                        <MenuItem key={c.email} value={c.id}>{c.name} ({c.email})</MenuItem>
                    ))}
                </Select>

                <InputLabel htmlFor="select-coach-label" id="select-coach-label">Alege abonamentul dorit de utilizator</InputLabel>
                <Select
                    labelId="select-coach-label"
                    id="select-coach"
                    placeholder="Selecteaza antrenor"
                    value={membership}
                    onChange={handleMembershipChange}
                >
                    {memberships?.map((m) => (
                        <MenuItem key={m.id} value={m.id}>{m.name}</MenuItem>
                    ))}
                </Select>

                <Button className='w-full sm:w-1/2 self-center' variant="contained"
                        onClick={() => handleBuyMembership()}>Continua</Button>


            </div>
        </div>
    );
}

export default BuyMembershipForClient;