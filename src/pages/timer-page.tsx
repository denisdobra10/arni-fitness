import { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";


const TimerPage = () => {
    const [count, setCount] = useState(5);
    const navigate = useNavigate();

    useEffect(() => {
        if (count > 0) {
            const timer = setTimeout(() => setCount(count - 1), 1000);
            return () => clearTimeout(timer);
        } else {
            navigate('/user'); // Change this to your target page
        }
    }, [count, navigate]);

    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <div className="text-center text-4xl font-bold text-gray-700">
                <h1>Iti multumim pentru achizitie.</h1>
                <h1>Vei fi redirectionat in {count}...</h1>
            </div>
        </div>
    );
};

export default TimerPage;