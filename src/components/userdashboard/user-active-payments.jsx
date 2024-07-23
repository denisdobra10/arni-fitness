import React from 'react'
import UserActivePayment from './user-active-payment'
import {useData} from "../../lib/data-provider.jsx";

const UserActivePayments = () => {
    const { user } = useData();

    const convertDate = (date) => {
        const d = new Date(date);
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return d.toLocaleDateString('ro-RO', options);
    }

    return (
        <div className="flex flex-col w-full">
            {user?.purchasesDetails?.length === 0 && <span className='text-2xl'>Nu ai nicio plata activa</span>}
            {user?.purchasesDetails?.map(payment => (
                <UserActivePayment
                    key={payment.date}
                    date={convertDate(payment.date)}
                    link={payment.paymentLink}
                    amount={payment.membershipPrice}
                    description={payment.membershipName} />
            ))}
        </div>
    )
}

export default UserActivePayments