import React from 'react'
import {useNavigate} from "react-router-dom";
import {useData} from "../../lib/data-provider.jsx";
import axios from "../../utils/axios";
import {formatDate} from "@fullcalendar/core";
import {formatRomanianDate} from "./class-reservation-item.jsx";

const UserActiveReservation = ({ reservationId, dateTime, className, coach }) => {
    const navigate = useNavigate();
    const { displayNotification } = useData();

    const handleCancelReservation = async () => {
        try {
            // Make a request to the backend to cancel the reservation
            await axios.delete(`/user/cancelReservation/${reservationId}`, {});
            navigate(0);
            displayNotification('Rezervarea a fost anulata cu succes', 'success');
        } catch (err) {
            displayNotification(err.response.data.message, 'error');
        }
    }

    return (
        <div className='grid grid-cols-2 gap-2 border-2 p-2 border-primary'>
            <span>{formatRomanianDate(dateTime)}</span>
            <span>{className}</span>
            <span>{coach}</span>
            <span className='font-bold text-lg'>REZERVAT</span>
            <button className='col-span-2 text-white bg-primary font-semibold text-lg'
                onClick={() => handleCancelReservation()}>Renunta</button>
        </div>
    )
}

export default UserActiveReservation