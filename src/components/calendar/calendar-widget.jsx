import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import roLocale from '@fullcalendar/core/locales/ro';
import interactionPlugin from "@fullcalendar/interaction";
import CalendarModal from './add-to-calendar-modal';
import EventModal from './event-modal';

function CalendarWidget() {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEventModalOpen, setIsEventModalOpen] = useState(false);
    const [selectedDate, setSelectedDate] = useState(null);
    const [selectedEvent, setSelectedEvent] = useState(null);

    function handleDateClick(arg) {
        setSelectedDate(arg.dateStr);
    }

    function handleEventClick(arg) {
        setSelectedEvent(arg.event);
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
                events={[
                    { title: 'Clasa TRX ora 19:30', date: '2024-04-16' },
                    { title: 'Clasa Cross-fit ora 20:30', date: '2024-04-16' },
                    { title: 'Clasa Cross-fit ora 20:30', date: '2024-04-16' },
                    { title: 'Clasa Cross-fit ora 20:30', date: '2024-04-16' }
                ]}
                dateClick={handleDateClick}
                eventClick={handleEventClick}
            />
            <EventModal
                isOpen={isEventModalOpen}
                onClose={closeEventModal}
                data={selectedEvent}
            />
            <CalendarModal isOpen={isModalOpen} onClose={closeModal} />
        </>
    );
}

export default CalendarWidget;
