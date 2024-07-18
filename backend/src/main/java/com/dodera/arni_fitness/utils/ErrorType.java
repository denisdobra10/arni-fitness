package com.dodera.arni_fitness.utils;

public class ErrorType {
    public static final String INVALID_CREDENTIALS = "Email sau parola gresita";
    public static final String USED_EMAIL = "Email-ul este deja folosit";
    public static final String ACCOUNT_CREATION_ERROR = "A aparut o eroare la crearea contului";
    public static final String UNEXPECTED_ERROR = "A aparut o eroare. Va rugam sa incercati mai tarziu.";
    public static final String INVALID_SUBSCRIPTION = "Abonamentul nu mai este valabil";
    public static final String NO_AVAILABLE_SPOTS = "Nu mai sunt locuri disponibile";
    public static final String NO_ENTRIES_LEFT = "Nu mai ai intrari disponibile";
    public static final String SUBSCRIPTION_CREATION_ERROR = "A aparut o eroare la crearea subscriptiei";
    // ERORI ADMIN
    public static final String MEMBERSHIP_CREATION_ERROR = "A aparut o eroare la crearea abonamentului";
    public static final String MEMBERSHIP_DELETION_ERROR = "A aparut o eroare la stergerea abonamentului";
    public static final String NO_SUBSCRIPTION_CHECK_IN = "Utilizatorul nu are nici un abonament activ.";
    public static final String EXPIRED_SUBSCRIPTION_CHECK_IN = "Abonamentul utilizatorului a expirat.";
    public static final String NO_ENTRIES_LEFT_CHECK_IN = "Utilizatorul a folosit toate intrarile disponibile.";

    public static final String RESERVATION_EXISTS = "Deja ai rezervat acest antrenament.";
    public static final String HAS_ACTIVE_SUBSCRIPTION = "Deja ai un abonament activ.";
}
