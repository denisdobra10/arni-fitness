import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import roLocale from '@fullcalendar/core/locales/ro';
import interactionPlugin from "@fullcalendar/interaction";


function UserCalendarWidget({ setSelectedDate, sessions }) {

    const handleDateClick = (arg) => {
        setSelectedDate(arg.dateStr);
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
