import React, {useEffect, useState} from 'react';
import AssignCoachToClassWidget from '../components/clase/assign-coach-to-class-widget';
import HeaderTitleWidget from '../components/statistics/header-title-widget';
import CreateClassWidget from '../components/clase/create-class-widget';
import ClaseActiveWidget from '../components/clase/clase-active-widget';
import { useData } from '../lib/data-provider';
import axios from '../utils/axios';

function ClasePage() {
    const { displayNotification } = useData();
    const [classes, setClasses] = useState([]);
    const [coaches, setCoaches] = useState([]);

    useEffect(() => {
        const fetchClassesAndCoaches = async () => {
            try {
                const response = await axios.get('/admin/classes/coaches');
                setCoaches(response.data.coaches);
                setClasses(response.data.classes);
            } catch (err) {
                displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
            }
        };

        fetchClassesAndCoaches();
    }, []);

    const handleAssignCoach = async (classId, coachId) => {
        try {
            await axios.post(`/admin/classes/assign`, { classId, coachId });
            window.location.reload();

            displayNotification('Clasa a fost stearsa cu succes', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }

    const handleDelete = async (id) => {
        if (id === null || id === undefined || id === '') {
            displayNotification('A aparut o eroare neasteptata', 'error');
            return;
        }

        try {
            await axios.delete(`/admin/classes/${id}`);
            setClasses(classes.filter((item) => item.id !== id));

            displayNotification('Clasa a fost stearsa cu succes', 'success')
        } catch (err) {
            displayNotification(err.response?.data || 'A aparut o eroare neasteptata', 'error')
        }
    }

    return (
        <div className="flex flex-col p-6 w-full">
            <HeaderTitleWidget title='Clase (Energy Kardio Club)' />
            <AssignCoachToClassWidget classes={classes} coaches={coaches} assignCoach={handleAssignCoach}/>
            <CreateClassWidget />
            <ClaseActiveWidget handleDelete={handleDelete} classes={classes}/>
        </div>
    )
}

export default ClasePage