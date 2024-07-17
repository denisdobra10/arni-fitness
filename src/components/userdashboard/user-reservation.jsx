import React, {useEffect, useState} from 'react'
import UserCalendarWidget from './user-calendar-widget'
import UserReservationAvailableClasses from './user-reservation-available-classes'
import axios from "../../utils/axios";

function getTodayDate() {
    const today = new Date();
    const day = today.getDate();
    const month = today.getMonth() + 1;
    const year = today.getFullYear();

    return `${year}-${month < 10 ? `0${month}` : month}-${day < 10 ? `0${day}` : day}`;
}

function compareDates(date1, date2) {
    // Create new date objects to avoid modifying the original dates
    let d1 = new Date(date1);
    let d2 = new Date(date2);

    // Set the time components to zero
    d1.setHours(0, 0, 0, 0);
    d2.setHours(0, 0, 0, 0);
    console.log(d1.getTime() === d2.getTime());
    // Compare the dates
    return d1.getTime() === d2.getTime();
}

const UserReservation = () => {
    const [selectedDate, setSelectedDate] = useState(getTodayDate());
    const [sessions, setSessions] = useState([]);

    useEffect(() => {
        const fetchSessions = async () => {
            try {
                const response = await axios.get('/user/sessions');

                setSessions(response.data);
            } catch (err) {
                console.log(err);
            }
        }

        fetchSessions();
    }, [])

    return (
        <div className="flex flex-col py-8 px-4 gap-4 lg:gap-16 w-full rounded-lg bg-white text-center text-primary shadow-spreaded shadow-primary">
            <span className='text-3xl font-bold uppercase'>Rezerva <span className='text-slate-500'>Clasa</span></span>
            <span className='self-start'>Selecteaza o zi din calendar pentru a vedea ce clase sunt disponibile pentru ziua respectiva.</span>
            <div className="self-center w-full md:w-[60%]">
                <UserCalendarWidget
                    selectedDate={selectedDate}
                    setSelectedDate={setSelectedDate}
                    sessions={sessions}
                />
            </div>
            <UserReservationAvailableClasses
                selectedDate={selectedDate}
                sessions={sessions.filter(session => compareDates(session.date, selectedDate))}
            />

        </div>
    )
}

export default UserReservation