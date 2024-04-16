import React from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CheckinWidget from '../components/clienti/checkin-widget'
import ClientListWidget from '../components/clienti/clients-list-widget'

function ClientiPage() {

    const clients = [
        {
            id: 1,
            name: 'Ana Popescu',
            email: 'ana.popescu@example.com',
            phone: '0722 123 456',
            registrationDate: '2024-04-16',
            activeSubscription: true,
        },
        {
            id: 2,
            name: 'Ion Ionescu',
            email: 'ion.ionescu@example.com',
            phone: '0733 456 789',
            registrationDate: '2024-04-15',
            activeSubscription: false,
        },
        {
            id: 3,
            name: 'Maria Vasilescu',
            email: 'maria.vasilescu@example.com',
            phone: '0744 789 123',
            registrationDate: '2024-04-14',
            activeSubscription: true,
        },
    ];

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Clienti (Energy Kardio Club)' />
            <CheckinWidget />
            <ClientListWidget clients={clients} />
        </div>
    )
}

export default ClientiPage