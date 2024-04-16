import React from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CreateAbonamentWidget from '../components/abonamente/create-abonament-widget'
import AbonamenteActiveWidget from '../components/abonamente/abonamente-active-widget';


function AbonamentePage() {
    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Abonamente (Energy Kardio Club)' />
            <CreateAbonamentWidget />
            <AbonamenteActiveWidget />
        </div>
    )
}

export default AbonamentePage