import React, { useEffect, useState } from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CreateAbonamentWidget from '../components/abonamente/create-abonament-widget'
import AbonamenteActiveWidget from '../components/abonamente/abonamente-active-widget';
import { AxiosError } from 'axios';
import { useData } from '../lib/data-provider';
import axios from '../utils/axios';

function AbonamentePage() {

    const { displayNotification } = useData();
    const [memberships, setMemberships] = useState<any[] | undefined>(undefined)


    useEffect(() => {
        console.log('fetching memberships')
        const fetchMemberships = async () => {
            try {
                const response = await axios.get('/admin/memberships');
                setMemberships(response.data);

                console.log(response.data)

            } catch (err: any) {
                const error = err as AxiosError;
                displayNotification(error.response?.data || 'A aparut o eroare la aducerea abonamentelor', 'error')
            }

        }

        fetchMemberships();

    }, [])

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Abonamente (Energy Kardio Club)' />
            <CreateAbonamentWidget />
            <AbonamenteActiveWidget memberships={memberships} />
        </div>
    )
}

export default AbonamentePage