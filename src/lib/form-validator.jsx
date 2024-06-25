
export const useSignupValidator = (name, email, password, confirmPassword) => {

    if (name.length < 3) {
        return 'Numele trebuie sa contina cel putin 3 caractere';
    }

    if (!email.includes('@')) {
        return 'Adresa de email nu este valida';
    }

    if (password.length < 6) {
        return 'Parola trebuie sa contina cel putin 6 caractere';
    }

    if (password !== confirmPassword) {
        return 'Parolele nu coincid';
    }

    return null;
}