
import React, { useEffect, useState } from 'react'
import HeaderTitleWidget from '../components/statistics/header-title-widget';
import MainStatisticsList from '../components/statistics/main-statistics-widget';
import SecondaryStatisticsList from '../components/statistics/secondary-statistics-list';
import DescriptiveDoubleColorHeader from '../components/statistics/descriptive-double-color-header';
import ClassesStatisticsList from '../components/statistics/classes-statistics-list';
import CoachesStatisticsList from '../components/statistics/coaches-statistics-list';
import { AxiosError } from 'axios';
import { useData } from '../lib/data-provider';
import axios from '../utils/axios'


interface StatisticsResponse {
    activeMemberships: number;
    createdClasses: number;
    createdCoaches: number;
    createdMemberships: number;
    monthChosenClass: string;
    monthlyRevenue: number;
    mostChoseMembershipCount: number | null;
    mostChosenMembership: string | null;
    mostPopularCoachMonth: string;
    mostPopularCoachToday: string;
    mostPopularCoachWeek: string;
    soldMemberships: number;
    todayFullSessions: number;
    todaysChosenClass: string;
    todaysClients: number;
    todaysReservations: number;
    weekChosenClass: string;
    yearChosenClass: string;
}



function StatisticsPage() {

    const { displayNotification } = useData();
    const [data, setData] = useState<StatisticsResponse | null>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('/admin/statistics');
                setData(response.data);

                console.log(response.data);
            } catch (err: any) {
                const error = err as AxiosError;
                displayNotification(error.response?.data || 'A aparut o eroare la incarcarea datelor. Va rugam incercati din nou.', 'error');
            }
        }

        fetchData();
    }, [])

    return (
        <div className='flex flex-col p-6 w-full'>
            <HeaderTitleWidget title='Statistici (Energy Kardio Club)' />

            <section id="main-statistics-section">

                <div className="container">
                    <MainStatisticsList soldMemberships={data?.soldMemberships} monthlyRevenue={data?.monthlyRevenue} activeMemberships={data?.activeMemberships} todaysClients={data?.todaysClients} />
                    <SecondaryStatisticsList createdMemberships={data?.createdMemberships} createdClasses={data?.createdClasses} createdCoaches={data?.createdCoaches} todaysReservations={data?.todaysReservations} todayFullSessions={data?.todayFullSessions} />
                </div>
            </section>

            <section id='classes-statistics'>
                <div className="container">
                    <DescriptiveDoubleColorHeader first='statisticile' second='claselor' description={'Gradul de interes a clientilor, dupa clasa sportiva'} className={'my-12 text-center'} />
                    <ClassesStatisticsList todayChosenClass={data?.todaysChosenClass} weekChosenClass={data?.weekChosenClass} monthChosenClass={data?.monthChosenClass} yearChosenClass={data?.yearChosenClass} />
                </div>
            </section>

            <section id='coaches-statistics'>
                <div className="container">
                    <DescriptiveDoubleColorHeader first='statisticile' second='antrenorilor' description={'Nivelul de performanta al antrenorilor, bazat pe interesul clientilor'} className={'my-12 text-center'} />
                    <CoachesStatisticsList mostPopularCoachToday={data?.mostPopularCoachToday} mostPopularCoachWeek={data?.mostPopularCoachWeek} mostPopularCoachMonth={data?.mostPopularCoachMonth} />
                </div>
            </section>

            <section id='best-subscription'>
                <div className="block text-center">
                    <DescriptiveDoubleColorHeader first='abonamentul' second='preferat' className={'mt-12 text-center'} description={undefined} />
                    <h2 className='my-3'><span className='font-bold italic'>Abonamentul {data?.mostChosenMembership}</span> a fost achizitionat in total de {data?.mostChoseMembershipCount} de ori.</h2>
                </div>

            </section>
        </div>
    )
}

export default StatisticsPage