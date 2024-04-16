
import React from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget';
import MainStatisticsList from '../components/statistics/main-statistics-widget';
import SecondaryStatisticsList from '../components/statistics/secondary-statistics-list';
import DescriptiveDoubleColorHeader from '../components/statistics/descriptive-double-color-header';
import ClassesStatisticsList from '../components/statistics/classes-statistics-list';
import CoachesStatisticsList from '../components/statistics/coaches-statistics-list';



function StatisticsPage() {
    return (
        <div className='flex flex-col p-6 w-full'>

            <section id="main-statistics-section">
                <HeaderTitleWidget title='Statistici (Energy Kardio Club)' />

                <div className="container">
                    <MainStatisticsList />
                    <SecondaryStatisticsList />
                </div>
            </section>

            <section id='classes-statistics'>
                <div className="container">
                    <DescriptiveDoubleColorHeader first='statisticile' second='claselor' description={'Gradul de interes a clientilor, dupa clasa sportiva'} className={'my-12 text-center'} />
                    <ClassesStatisticsList />
                </div>
            </section>

            <section id='coaches-statistics'>
                <div className="container">
                    <DescriptiveDoubleColorHeader first='statisticile' second='antrenorilor' description={'Nivelul de performanta al antrenorilor, bazat pe interesul clientilor'} className={'my-12 text-center'} />
                    <CoachesStatisticsList />
                </div>
            </section>

            <section id='best-subscription'>
                <div className="block text-center">
                    <DescriptiveDoubleColorHeader first='abonamentul' second='preferat' className={'mt-12 text-center'} />
                    <h2 className='my-3'><span className='font-bold italic'>Abonamentul GOLD Full Access</span> a fost achizitionat in total de 168 de ori.</h2>
                </div>

            </section>
        </div>
    )
}

export default StatisticsPage