import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import roLocale from '@fullcalendar/core/locales/ro';
import interactionPlugin from "@fullcalendar/interaction";

function formatDate(date) {
    var year = date.getFullYear();
    var month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    var day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
}

function UserCalendarWidget({ setSelectedDate, sessions }) {

    const handleDateClick = (arg) => {
        setSelectedDate(arg.dateStr);
    }

    const handleEventClick = (arg) => {
        const date = new Date(arg.event.start);
        setSelectedDate(formatDate(date));

    }


    return (
        <>
            <FullCalendar
                height="auto"
                plugins={[dayGridPlugin, interactionPlugin]}
                initialView="dayGridMonth"
                locale={roLocale}
                weekends={false}
                dateClick={handleDateClick}
                eventClick={handleEventClick}
                events={sessions.map(session =>
                {
                    const title = session.sessionName + " " + session.date.split("T")[1];
                    const date = session.date.split("T")[0];
                    return {
                        title: title,
                        date: date,
                    };
                })}
            />
        </>
    );
}

export default UserCalendarWidget;
