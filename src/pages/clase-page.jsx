import React from 'react'
import AssignCoachToClassWidget from '../components/clase/assign-coach-to-class-widget'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CreateClassWidget from '../components/clase/create-class-widget'
import ClaseActiveWidget from '../components/clase/clase-active-widget'

function ClasePage() {
    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Clase (Energy Kardio Club)' />
            <AssignCoachToClassWidget />
            <CreateClassWidget />
            <ClaseActiveWidget />
        </div>
    )
}

export default ClasePage