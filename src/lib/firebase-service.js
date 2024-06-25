import { initializeApp } from 'firebase/app';
import { getAuth } from 'firebase/auth';
import { getDatabase } from 'firebase/database';

const firebaseConfig = {
    apiKey: "AIzaSyAeug9ej5TIgSVJ-8-YEyA6Zsgvpq-EmM8",
    authDomain: "energy-kardio-club.firebaseapp.com",
    projectId: "energy-kardio-club",
    storageBucket: "energy-kardio-club.appspot.com",
    messagingSenderId: "343194465016",
    appId: "1:343194465016:web:4b84ef7d5b659f4855c10d",
    databaseURL: "https://energy-kardio-club-default-rtdb.europe-west1.firebasedatabase.app"
};


const firebaseApp = initializeApp(firebaseConfig);

export const auth = getAuth(firebaseApp);
export const db = getDatabase(firebaseApp);
export default firebaseApp;