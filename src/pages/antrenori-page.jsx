import React from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget'
import CreateAntrenorWidget from '../components/antrenori/create-antrenor-widget'
import AntrenoriListWidget from '../components/antrenori/antrenori-list-widget'

function AntrenoriPage() {
    return (
        <div className='flex flex-col w-full p-6'>
            <HeaderTitleWidget title='Antrenori (Energy Kardio Club)' />
            <CreateAntrenorWidget />
            <AntrenoriListWidget />

        </div>
    )
}

export default AntrenoriPage