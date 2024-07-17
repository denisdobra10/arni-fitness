import React from 'react'
import UserActivePayment from './user-active-payment'
import {useData} from "../../lib/data-provider.jsx";

const UserActivePayments = () => {
    const { user } = useData();

    return (
        <div className="flex flex-col w-full">
            {user?.purchasesDetails?.length === 0 && <span className='text-2xl'>Nu ai nicio plata activa</span>}
            {user?.purchasesDetails?.map(payment => (
                <UserActivePayment
                    key={payment.date}
                    date={payment.date}
                    link={payment.paymentLink}
                    amount={payment.membershipPrice}
                    description={payment.membershipName} />
            ))}
        </div>
    )
}

export default UserActivePayments