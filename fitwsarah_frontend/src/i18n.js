import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from "i18next-browser-languagedetector";

i18n
.use(LanguageDetector)
.use(initReactI18next)
.init({
    debug: true,
    fallbackLng: 'en',
    interpolation: {
        escapeValue: false,
    },
    resources: {
        en: {
            translation: {
                // FooterNotLoggedIn.js
                aboutMe: "About Me",
                contactMe: "Contact Me",
                locationInfo: "Based in Montreal, Canada",
                copyrightInfo: "©Copyright 2023 All rights reserved. Powered by TheMontrealGoats",
                // Home.js
                servicesAndPrices: "Services & Prices",
                book: "Book",
                details: "Details",
                addFitnessService: "Add a Fitness Service",
                fitnessServiceTitle: "Fitness Service Title",
                duration: "Duration",
                minutes: "minutes",
                hours: "hour(s)",
                price: "Price",
                description: "Description",
                otherInformationOptional: "Other Information (Optional)",
                // Filter.js
                openFilter: "Open Filter",
                filterBy: "Filter by:",
                clear: "Clear",
                requested: "Requested",
                scheduled: "Scheduled",
                cancelled: "Cancelled",
                completed: "Completed",
                appointmentId: "Appointment ID",
                status: "Status",
                // Login.js
                login: "Login",
                // Logout.js
                logout: "Logout",
                // Register.js
                signup: "Sign Up",
                // RoleBasedSwitch.js
                editMode: "Edit Mode",
                // BookingButton.js
                confirmBooking: "Confirm",
                // Appointment.js
                appointmentLocation: "Location: ",
                appointmentDate: "Date: ",
                appointmentTime: "Time: ",
                appointmentViewDetails: "View Details",
                appointmentClose: "Close",
                appointmentDescription: "Description: ",
                appointmentOtherInformation: "Other Information: ",
                appointmentPrice: "Price: ",
                appointmentDuration: "Duration: ",
                // setting.js
                accountSettings: "Account Settings",
                username: "Username",
                email: "Email",
                city: "City",
                update: "Update",
                updateSuccess: "Profile updated successfully!",
                // CoachNote.js
                coachNote: "Coach Note",
                coachNoteTitle: "Coach Notes",
                // navLoggedIn.js
                profile: "Profile",
                trainerPanel: "Trainer Panel",
                adminPanel: "Admin Panel",
                // addService.js
                addService: "Add",
                // AdminAccounts.js
                accounts: "Accounts",
                accountId: "Account ID",
                delete: "Delete",
                details: "Details",
                // AdminAppointments.js
                appointmentId: "Appointment ID",
                location: "Location",
                firstName: "First Name",
                lastName: "Last Name",
                phoneNumber: "Phone Number",
                date: "Date",
                time: "Time",
                actions: "Actions",
                save: "Save",
                cancel: "Cancel",
                accept: "Accept",
                deny: "Deny",
                edit: "Edit",
                cancelAppointment: "Cancel Appointment",
                // AdminInvoices.js
                invoices: "Invoices",
                invoiceId: "Invoice ID",
                amount: "Amount",
                content: "Content",
                // AdminPanel.js
                adminPanelaccounts: "Accounts",
                currentTotalAccounts: "Current Total: {{count}} Accounts",
                lastMonthTotalAccounts: "Last Month's Total: {{count}} Accounts",
                adminPanelAppointments: "Appointments",
                currentTotalAppointments: "Current Total: {{count}} Appointments",
                lastMonthTotalAppointments: "Last Month's Total: {{count}} Appointments",
                adminPanelInvoices: "Invoices",
                currentTotalInvoices: "Current Total: {{count}} Invoices",
                lastMonthTotalInvoices: "Last Month's Total: {{count}} Invoices",
                adminPanelAnalytics: "Analytics",
                currentTotalAnalytics: "Current Total: {{count}} Reports",
                lastMonthTotalAnalytics: "Last Month's Total: {{count}} Reports",
                adminPanelFeedback: "Feedback",
                currentTotalFeedback: "Current Total: {{count}} Submissions",
                lastMonthTotalFeedback: "Last Month's Total: {{count}} Submissions",
                adminPanelServices: "Fitness Services",
                currentTotalServices: "Current Total: {{count}} Services",
                lastMonthTotalServices: "Last Month's Total: {{count}} Services",
                adminPanelCoachNotes: "Coach Notes",
                currentTotalCoachNotes: "Current Total: {{count}} Notes",
                lastMonthTotalCoachNotes: "Last Month's Total: {{count}} Notes",

            }
        },
        fr: {
            translation: {
                // FooterNotLoggedIn.js
                aboutMe: "À Propos De Moi",
                contactMe: "Contactez Moi",
                locationInfo: "Basé à Montréal, Canada",
                copyrightInfo: "©Copyright 2023 Tous droits réservés. Propulsé par TheMontrealGoats",
                // Home.js
                servicesAndPrices: "Services et Tarifs",
                book: "Réserver",
                details: "Détails",
                addFitnessService: "Ajouter un Service de Fitness",
                fitnessServiceTitle: "Titre du Service de Fitness",
                duration: "Durée",
                minutes: "minutes",
                hours: "heure(s)",
                price: "Prix",
                description: "Description",
                otherInformationOptional: "Autres Informations (Facultatif)",
                // Filter.js
                openFilter: "Ouvrir le filtre",
                filterBy: "Filtrer par :",
                clear: "Effacer",
                requested: "Demandé",
                scheduled: "Prévu",
                cancelled: "Annulé",
                completed: "Terminé",
                appointmentId: "ID du Rendez-vous",
                status: "Statut",
                // Login.js
                login: "S'identifier",
                // Logout.js
                logout: "Se Déconnecter",
                // Register.js
                signup: "S'inscrire",
                // RoleBasedSwitch.js
                editMode: "Mode Édition",
                // BookingButton.js
                confirmBooking: "Confirmer",
                // Appointment.js
                appointmentLocation: "Emplacement : ",
                appointmentDate: "Date : ",
                appointmentTime: "Heure : ",
                appointmentViewDetails: "Voir les détails",
                appointmentClose: "Fermer",
                appointmentDescription: "Description : ",
                appointmentOtherInformation: "Autres Informations : ",
                appointmentPrice: "Prix : ",
                appointmentDuration: "Durée : ",
                // setting.js
                accountSettings: "Paramètres du Compte",
                username: "Nom d'utilisateur",
                email: "Email",
                city: "Ville",
                update: "Mettre à Jour",
                updateSuccess: "Profil mis à jour avec succès!",
                // CoachNote.js
                coachNote: "Note de Coach",
                coachNoteTitle: "Notes de Coach",
                // navLoggedIn.js
                profile: "Profil",
                trainerPanel: "Panneau du Coach",
                adminPanel: "Panneau d'Administration",
                // addService.js
                addService: "Ajouter",
                // AdminAccounts.js
                accounts: "Comptes",
                accountId: "ID du Compte",
                delete: "Supprimer",
                details: "Détails",
                // AdminAppointments.js
                appointmentId: "ID du Rendez-vous",
                location: "Emplacement",
                firstName: "Prénom",
                lastName: "Nom de Famille",
                phoneNumber: "Numéro de Téléphone",
                date: "Date",
                time: "Heure",
                actions: "Actions",
                save: "Sauvegarder",
                cancel: "Annuler",
                accept: "Accepter",
                deny: "Refuser",
                edit: "Modifier",
                cancelAppointment: "Annuler le Rendez-vous",
                // AdminInvoices.js
                invoices: "Factures",
                invoiceId: "ID de la Facture",
                amount: "Montant",
                content: "Contenu",
                // AdminPanel.js
                adminPanelaccounts: "Comptes",
                currentTotalAccounts: "Total Actuel : {{count}} Comptes",
                lastMonthTotalAccounts: "Total du Mois Dernier : {{count}} Comptes",
                adminPanelAppointments: "Rendez-vous",
                currentTotalAppointments: "Total Actuel : {{count}} Rendez-vous",
                lastMonthTotalAppointments: "Total du Mois Dernier : {{count}} Rendez-vous",
                adminPanelInvoices: "Factures",
                currentTotalInvoices: "Total Actuel : {{count}} Factures",
                lastMonthTotalInvoices: "Total du Mois Dernier : {{count}} Factures",
                adminPanelAnalytics: "Analytiques",
                currentTotalAnalytics: "Total Actuel : {{count}} Rapports",
                lastMonthTotalAnalytics: "Total du Mois Dernier : {{count}} Rapports",
                adminPanelFeedback: "Commentaires",
                currentTotalFeedback: "Total Actuel : {{count}} Soumissions",
                lastMonthTotalFeedback: "Total du Mois Dernier : {{count}} Soumissions",
                adminPanelServices: "Services de Fitness",
                currentTotalServices: "Total Actuel : {{count}} Services",
                lastMonthTotalServices: "Total du Mois Dernier : {{count}} Services",
                adminPanelCoachNotes: "Notes de Coach",
                currentTotalCoachNotes: "Total Actuel : {{count}} Notes",
                lastMonthTotalCoachNotes: "Total du Mois Dernier : {{count}} Notes",
                
            }
        }
    }
});