import React from 'react'
import LogoutIcon from '@mui/icons-material/Logout';
import { IconButton } from '@mui/material'


function HeaderTitleWidget({ title = "Page Title" }) {
    return (
        <div className="flex flex-row items-center justify-between">
            <h1 className='text-4xl text-textcolor font-bold'>{title}</h1>
            <IconButton aria-label='Logout' size='large' style={{ color: 'white' }} >
                <LogoutIcon />
            </IconButton>
        </div>
    )
}

export default HeaderTitleWidget