import React from 'react'
import CalendarWidget from '../components/calendar/calendar-widget'
import HeaderTitleWidget from '../components/statistics/header-title-widget'

function CalendarPage() {
    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Calendar (Energy Kardio Club)' />

            <div className="my-10">
                <CalendarWidget />
            </div>
        </div>
    )
}

export default CalendarPage