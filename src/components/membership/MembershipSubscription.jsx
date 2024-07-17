import React, { useState } from 'react'
import Checkmark from '../../assets/checkmark.png'
import axios from "../../utils/axios";
import {useData} from "../../lib/data-provider.jsx";

const MembershipSubscription = ({ subscriptionId, title, price, entries, availability, description }) => {
    const { displayNotification } = useData();
    const [displayedDescription] = useState(description.length > 400 ? description.slice(0, 400) + '...' : description);

    const handleChoseSubscription = async () => {
        try {
            // Make a request to the backend to subscribe the user to the selected subscription
            const response = await axios.post(`/user/purchase/${subscriptionId}`, {});

            if (response?.data?.checkoutLink) {
                window.location.href = response.data.checkoutLink;
            }

        } catch (err) {
            displayNotification(err.response.data.message, 'error');
            console.log(err);
        }
    }

    return (
        <div className="flex flex-col bg-white border-2 text-primary border-primary rounded-xl justify-center items-center h-[700px]">
            <div className='py-8 text-center'>
                <span className='text-2xl font-bold'>{title}</span>
            </div>

            <div className='flex flex-row py-8 gap-2 text-center bg-primary w-full text-white justify-center'>
                <span className='text-5xl font-bold'>{price}</span>
                <span className='text-xl'>lei</span>
            </div>

            <div className="flex flex-row justify-center gap-4 items-center w-full py-8">
                <div className="flex flex-row gap-2">
                    <img src={Checkmark} className='w-8 h-8' />
                    <span className='text-lg'>{entries} intrari</span>
                </div>

                <div className="flex flex-row gap-2">
                    <img src={Checkmark} className='w-8 h-8' />
                    <span className='text-lg'>{availability} de zile</span>
                </div>
            </div>

            <div className="p-8 px-4 text-left border-y border-primary">
                {displayedDescription}
            </div>

            <div className=' px-4 w-full py-8 text-white text-2xl font-bold'>
                <button className='w-full bg-primary py-4' onClick={() => handleChoseSubscription()}>Alege</button>
            </div>

        </div>
    )
}

export default MembershipSubscription