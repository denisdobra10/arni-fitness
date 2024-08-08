import React from 'react'
import ClassReservationItem, {formatRomanianDate} from './class-reservation-item'

const UserReservationAvailableClasses = ({ selectedDate, sessions}) => {
    return (
        <div className="flex flex-col gap-4">
            <span className='text-3xl font-semibold self-start'>Clase disponibile {formatRomanianDate(selectedDate, false)}</span>

            {sessions.length === 0 && <span className='text-2xl'>Nu sunt clase disponibile pentru aceasta zi</span>}
            {sessions.map(session => (
                <ClassReservationItem
                    key={session.sessionId}
                    sessionId={session.sessionId}
                    className={session.sessionName}
                    coach={session.coachName}
                    time={session.date}
                    availableSpace={session.availableSpots}
                />
            ))}
        </div>
    )
}

export default UserReservationAvailableClasses