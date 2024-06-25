import React, { useRef, useState } from 'react'
import { auth, db } from '../lib/firebase-service'
import { createUserWithEmailAndPassword } from 'firebase/auth';
import { useSignupValidator } from '../lib/form-validator';
import { ref, set } from 'firebase/database';
import { useData } from '../lib/data-provider';

const SignupFormular = () => {

    const { setIsLoading, setLoadingMessage } = useData();
    const [formData, setFormData] = useState({ name: '', email: '', password: '', confirmPassword: '' });
    const submitButton = useRef(null);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    }

    const handleSignup = (e) => {
        e.preventDefault();

        const errors = useSignupValidator(formData.name, formData.email, formData.password, formData.confirmPassword);
        if (errors) {
            alert(errors);
        } else {
            setLoadingMessage('Se creeaza contul...');
            setIsLoading(true);
            submitButton.current.disabled = true;
            createUserWithEmailAndPassword(auth, formData.email, formData.password)
                .then((userCredential) => {
                    const user = userCredential.user;
                    set(ref(db, `users/${user.uid}`), {
                        authId: user.uid,
                        name: formData.name,
                        email: formData.email,
                        role: 'user'
                    }).catch((error) => {
                        alert('A aparut o eroare la crearea contului. Va rugam sa contactati un administrator.');
                    });

                    console.log(user);
                })
                .catch((error) => {
                    const errorCode = error.code;
                    const errorMessage = error.message;
                    console.log(errorCode, errorMessage);
                })
                .finally(() => {
                    setLoadingMessage('Loading...');
                    setIsLoading(false);
                    submitButton.current.disabled = false;
                });
        }
    }

    return (
        <div className="flex flex-col w-full lg:w-1/3 mx-8 lg:mx-0 px-8 py-16 gap-4 shadow-spreaded bg-white shadow-black rounded-lg">
            <span className='text-3xl text-primary font-bold'>Creare cont</span>

            <form onSubmit={(e) => handleSignup(e)} className='flex flex-col w-full gap-2 text-black text-base font-bold'>
                <label htmlFor="name">Nume</label>
                <input onChange={(e) => handleChange(e)} type="text" name="name" id="name" placeholder='Introdu numele complet'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />
                <label htmlFor="email">Email</label>
                <input onChange={(e) => handleChange(e)} type="text" name="email" id="email" placeholder='Introdu adresa ta de email'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />
                <label htmlFor="password">Parola</label>
                <input onChange={(e) => handleChange(e)} type="password" name="password" id="password" placeholder='Introdu o parola sigura'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />
                <label htmlFor="confirmPassword">Confirma Parola</label>
                <input onChange={(e) => handleChange(e)} type="password" name="confirmPassword" id="confirmPassword" placeholder='Introdu din nou parola'
                    className='border border-primary rounded-md p-3 text-primary placeholder-red-300 text-sm font-light focus:outline-primary'
                />

                <input ref={submitButton} type="submit" value="Creeaza cont" className='text-lg font-bold bg-primary py-3 w-2/3 text-white self-center rounded my-2 hover:cursor-pointer hover:bg-red-800 disabled:bg-gray-500' />

            </form>

            <div className="flex flex-row gap-1 text-primary text-base font-light">
                <span>Ai deja un cont?</span>
                <a href="#" className='italic underline font-bold'>Conecteaza-te</a>
            </div>
        </div>
    )
}

export default SignupFormular