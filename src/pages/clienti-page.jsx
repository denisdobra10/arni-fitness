import React, {useEffect, useState} from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CheckinWidget from '../components/clienti/checkin-widget'
import ClientListWidget from '../components/clienti/clients-list-widget';
import { useData } from "../lib/data-provider.jsx";
import axios from "../utils/axios";
import BuyMembershipForClient from "../components/clienti/BuyMembershipForClient.tsx";

function ClientiPage() {
    const { displayNotification } = useData();
    const [clients, setClients] = useState([])
    const [memberships, setMemberships] = useState([])

    useEffect(() => {
        const fetchClients = async () => {
            try {
                const clientsResponse = await axios.get('/admin/clients');
                const membersResponse = await axios.get('/admin/memberships');
                setClients(clientsResponse.data);
                setMemberships(membersResponse.data);
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        fetchClients();
    }, []);

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Clienti (Energy Kardio Club)' />
            <BuyMembershipForClient clients={clients} memberships={memberships}/>
            <CheckinWidget />
            <ClientListWidget clients={clients} />
        </div>
    )
}

export default ClientiPage