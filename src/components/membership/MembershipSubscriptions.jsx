import React, {useEffect, useState} from 'react'
import MembershipSubscription from './MembershipSubscription'
import {useData} from "../../lib/data-provider.jsx";
import axios from "../../utils/axios";

const MembershipSubscriptions = () => {
    const { user } = useData();
    const [subscriptions, setSubscriptions] = useState([]);

    useEffect(() => {
        const fetchSubscriptions = async () => {
            try {
                const response = await axios.get('/user/memberships');

                setSubscriptions(response.data);
            } catch (err) {
                console.log(err);
            }
        }

        fetchSubscriptions();
    }, []);

    return (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-4 my-16">
            {subscriptions.map(subscription => (
                <MembershipSubscription
                    key={subscription.id}
                    subscriptionId={subscription.id}
                    title={subscription.name}
                    price={subscription.price}
                    entries={subscription.entries}
                    availability={subscription.availability}
                    description={subscription.description} />
            ))}

        </div>
    )
}

export default MembershipSubscriptions