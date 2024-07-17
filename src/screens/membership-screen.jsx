import React, {useEffect, useState} from 'react'
import MembershipHeader from '../components/membership/MembershipHeader'
import MembershipNavbar from '../components/membership/MembershipNavbar'
import MembershipBody from '../components/membership/MembershipBody'
import {useData} from "../lib/data-provider.jsx";
import axios from "../utils/axios";

const MembershipScreen = () => {
    return (
        <div className='flex flex-col bg-black w-full'>
            <MembershipNavbar />
            <MembershipHeader />
            <MembershipBody />
        </div>
    )
}

export default MembershipScreen