import React from 'react'
import UserActivePayment from './user-active-payment'

const UserActivePayments = () => {
    return (
        <div className="flex flex-col w-full">
            <UserActivePayment date={'25 aprilie 2024'} link={'https://doderasoft.com'} amount={250} description={'Abonament TRX'} />
            <UserActivePayment date={'25 aprilie 2024'} link={'https://doderasoft.com'} amount={250} description={'Abonament TRX'} />
            <UserActivePayment date={'25 aprilie 2024'} link={'https://doderasoft.com'} amount={250} description={'Abonament TRX'} />

        </div>
    )
}

export default UserActivePayments