import React, { useState, useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import roLocale from '@fullcalendar/core/locales/ro';
import interactionPlugin from "@fullcalendar/interaction";


function UserCalendarWidget() {
    const [selectedDate, setSelectedDate] = useState(null);

    function handleDateClick(arg) {
        setSelectedDate(arg.dateStr);
        alert('Data selectata: ' + arg.dateStr);
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
            />
        </>
    );
}

export default UserCalendarWidget;
