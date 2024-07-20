import React, {useEffect, useState} from 'react'
import AntrenorActivityCard from './antrenor-activity-card'
import axios from '../../utils/axios'
import {useData} from "../../lib/data-provider.jsx";

function AntrenoriListWidget() {
    const { displayNotification } = useData();
    const [coaches, setCoaches] = useState([]);

    useEffect(() => {
        const fetchCoaches = async () => {
            try {
                const response = await axios.get('/admin/coaches');
                setCoaches(response.data);
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        fetchCoaches();
    }, [])


    return (
        <div className='flex flex-col w-full bg-white rounded-lg p-3 my-10 text-center sm:text-left sm:p-10 '>
            <h2 className='text-3xl text-primary font-semibold capitalize'>Antrenori activi</h2>
            <span className='text-gray-500 mb-6'>Antrenorii activi si disponibili clientilor tai</span>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-3">
                {coaches.map(coach => <AntrenorActivityCard key={coach?.name} coach={coach} />)}
            </div>
        </div>
    )
}

export default AntrenoriListWidget