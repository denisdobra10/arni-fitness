package com.dodera.arni_fitness.mail;

public class EmailTemplates {
    public static String getWelcomeEmail(String name) {
        return """
                Bună %s,
                                
                Suntem încântați să te avem alături de noi! Contul tău la Arni Fitness a fost creat cu succes. Acum poți accesa toate beneficiile și resursele oferite de sala noastră.
                                                                
                Dacă ai întrebări sau ai nevoie de asistență, nu ezita să ne contactezi la [Adresa de email pentru suport] sau la [Număr de telefon].
                                
                Îți mulțumim că ai ales Arni Fitness! Suntem nerăbdători să te ajutăm să îți atingi obiectivele de fitness.
                                
                Cu respect,
                Echipa Arni Fitness
                """.formatted(name);
    }

    public static String getPaymentEmail(String name, String membershipName, int membershipPrice, String paymentLink) {
        return """
                Bună %s,
                                
                Îți mulțumim că ai efectuat plata pentru abonamentul tău la Arni fitness. Suntem bucuroși să te anunțăm că plata a fost procesată cu succes.
                                
                Detaliile abonamentului tău sunt următoarele:
                                
                - **Tip Abonament:** %s
                - **Suma Plătită:** %s
                                
                Poți începe să te bucuri de toate facilitățile și serviciile oferite de sala noastră imediat. Pentru a vizualiza detaliile plății și a accesa contul tău, te rugăm să folosești link-ul de mai jos:
                                
                %s
                                
                Dacă ai întrebări sau ai nevoie de informații suplimentare, te rugăm să ne contactezi la doderasoft@test.com.
                                
                Îți mulțumim că ai ales Arni Fitness pentru a-ți atinge obiectivele de fitness. Suntem aici pentru a te susține în fiecare pas al drumului tău către o viață mai sănătoasă și activă.
                                
                Cu respect,
                Echipa Arni Fitness
                """.formatted(name, membershipName, membershipPrice, paymentLink);
    }
}
