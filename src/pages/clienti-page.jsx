import React, {useEffect, useState} from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CheckinWidget from '../components/clienti/checkin-widget'
import ClientListWidget from '../components/clienti/clients-list-widget';
import { useData } from "../lib/data-provider.jsx";
import axios from "../utils/axios";

function ClientiPage() {
    const { displayNotification } = useData();
    const [clients, setClients] = useState([])

    useEffect(() => {
        const fetchClients = async () => {
            try {
                const response = await axios.get('/admin/clients');
                setClients(response.data);
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        fetchClients();
    }, []);

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Clienti (Energy Kardio Club)' />
            <CheckinWidget />
            <ClientListWidget clients={clients} />
        </div>
    )
}

export default ClientiPage