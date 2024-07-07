import React, { useRef, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import GoogleSvgIcon from '../assets/google-icon.svg'
import { useData } from '../lib/data-provider';
import axios from '../utils/axios';

const LoginFormular = () => {

    const { displayLoadingScreen, hideLoadingScreen, displayNotification, login } = useData();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const submitButton = useRef(null);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        submitButton.current.disabled = true;

        try {
            displayLoadingScreen('Se conecteaza...');

            const response = await axios.post('/login', formData);
            const accessToken = response.data.accessToken;
            await login(accessToken);

            displayNotification('Te-ai logat cu succes', 'success');

            navigate('/user');
        } catch (err) {
            displayNotification(err.response.data || 'A aparut o eroare neasteptata. Incearca mai tarziu', 'error');
        }

        hideLoadingScreen();
        submitButton.current.disabled = false;

    }

    return (
        <div className="flex flex-col gap-4 w-full text-black">
            <span className='text-3xl'>Conectare</span>

            <form onSubmit={handleSubmit} className='flex flex-col gap-2 w-full lg:w-2/3 text-left self-center'>
                <label htmlFor="email" className='text-lg'>Email</label>
                <input onChange={handleChange} type="text" name="email" id="email" placeholder='exemplu@gmail.com'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />

                <label htmlFor="password" className='text-lg'>Parola</label>
                <input onChange={handleChange} type="password" name="password" id="password" placeholder='************'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />

                <input type="submit" value="Login" className='text-lg font-bold bg-primary py-3 w-2/3 text-white self-center rounded my-2 hover:cursor-pointer hover:bg-red-800' />
            </form>

            <div className="flex flex-row gap-4 self-end items-center">
                <span className='text-base text-primary font-light'>Nu ai un cont?</span>
                <Link to={'/signup'} className='bg-primary px-8 text-base py-2 text-center text-white font-bold rounded'>Inregistreaza-te</Link>
            </div>

            <div className="w-full h-[0.5px] bg-slate-600"></div>

            <button ref={submitButton} className='flex flex-row justify-center w-2/3 self-center gap-4 py-2 items-center text-center border border-slate-500 rounded'>
                <img src={GoogleSvgIcon} className='w-6 h-6' />
                <span className='text-base font-medium text-black'>Conectare cu Google</span>
            </button>
        </div>
    )
}

export default LoginFormular