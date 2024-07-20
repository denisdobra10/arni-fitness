import React from 'react'
import LogoutIcon from '@mui/icons-material/Logout';
import { IconButton } from '@mui/material'
import {useNavigate} from "react-router-dom";


function HeaderTitleWidget({ title = "Page Title" }) {
    const navigate = useNavigate();


    const handleLogout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('user');
        navigate('/login');
    }

    return (
        <div className="flex flex-row items-center justify-between">
            <h1 className='text-4xl text-textcolor font-bold'>{title}</h1>
            <IconButton aria-label='Logout' size='large' style={{ color: 'white' }} onClick={handleLogout}>
                <LogoutIcon />
            </IconButton>
        </div>
    )
}

export default HeaderTitleWidget