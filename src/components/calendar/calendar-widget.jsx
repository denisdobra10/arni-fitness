import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import roLocale from '@fullcalendar/core/locales/ro';
import interactionPlugin from "@fullcalendar/interaction";
import CalendarModal from './add-to-calendar-modal';
import EventModal from './event-modal';
import axios from "../../utils/axios";
import {useData} from "../../lib/data-provider.jsx";

function CalendarWidget() {
    const { displayNotification } = useData();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEventModalOpen, setIsEventModalOpen] = useState(false);
    const [selectedDate, setSelectedDate] = useState(null);
    const [selectedEvent, setSelectedEvent] = useState(null);
    const [sessions, setSessions] = useState([]);

    useEffect(() => {
        const fetchSessions = async () => {
            try {
                const response = await axios.get('/admin/sessions');
                setSessions(response.data);
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        fetchSessions();
    }, [isModalOpen]);

    function handleDateClick(arg) {
        setSelectedDate(arg.dateStr);
    }

    function handleEventClick(arg) {
        setSelectedEvent(arg.event);
        console.log(setSelectedEvent(arg.event))
    }

    function closeModal() {
        setIsModalOpen(false);
        setSelectedDate(null);
    }

    function closeEventModal() {
        setIsEventModalOpen(false);
        setSelectedEvent(null);
    }

    useEffect(() => {
        if (selectedEvent && !selectedDate) {
            setIsEventModalOpen(true);
            console.log(selectedEvent);
        }

        if (selectedDate && !selectedEvent) {
            setIsModalOpen(true);
            console.log(selectedDate);
        }
    }, [selectedEvent, selectedDate]);

    return (
        <>
            <FullCalendar
                plugins={[dayGridPlugin, interactionPlugin]}
                initialView="dayGridMonth"
                locale={roLocale}
                weekends={false}
                events={sessions.map(session =>
                {
                    const title = session.name + " " + session.datetime.split("T")[1];
                    const date = session.datetime.split("T")[0];
                    return {
                        title: title,
                        date: date,
                    };
                })}
                dateClick={handleDateClick}
                eventClick={handleEventClick}
            />
            <EventModal
                isOpen={isEventModalOpen}
                onClose={closeEventModal}
            />
            <CalendarModal isOpen={isModalOpen} onClose={closeModal} data={selectedDate}/>
        </>
    );
}

export default CalendarWidget;
