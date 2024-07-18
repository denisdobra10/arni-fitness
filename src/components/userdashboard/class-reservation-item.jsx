import React from 'react'
import axios from "../../utils/axios";
import {useData} from "../../lib/data-provider.jsx";
import {useNavigate} from "react-router-dom";

const ClassReservationItem = ({ sessionId, className, coach, time, availableSpace }) => {
    const { displayNotification } = useData();
    const navigate = useNavigate();

    const handleReservation = async () => {
        try {
            // Make a request to the backend to subscribe the user to the selected subscription
            await axios.post(`/user/reserveSession/${sessionId}`, {});
            navigate(0);
            displayNotification('Rezervarea a fost facuta cu succes', 'success');
        } catch (err) {
            displayNotification(err.response.data, 'error');
            console.log(err);
        }
    }

    return (
        <div className='flex flex-col sm:flex-row gap-4 sm:gap-0 p-4 md:px-8 md:py-4 w-full border-2 border-primary justify-between items-center'>
            <div className="flex flex-col sm:flex-row gap-4 text-2xl font-bold">
                <span>{className}</span>
                <span>{coach}</span>
                <span>{time}</span>
                <span>{availableSpace} locuri ramase</span>
            </div>

            <button className='px-4 py-2 w-fit bg-primary text-white font-bold text-xl'
            onClick={() => handleReservation()}>Rezerva loc</button>
        </div>
    )
}

export default ClassReservationItem