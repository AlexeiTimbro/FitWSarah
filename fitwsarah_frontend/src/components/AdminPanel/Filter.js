import React from "react";
import "./Filter.css";
import Popup from 'reactjs-popup';
import { IoSearchSharp } from "react-icons/io5";
import { useTranslation } from "react-i18next";


function Filter({src, labels, onInputChange, searchTerm, clearFilters}) {

    const [statusButton, setStatusButton] = React.useState();
    const { t } = useTranslation('filter');

    const handleInputChange = (event, label) => {    
        //takes the actual value for the onInputChange from event (not for status), keep same name for status too
        if (label === "Status") {
                setStatusButton(event);
                onInputChange(label, event);
            }

        else{
            const { value } = event.target;
            onInputChange(label, value);
            }
        };

    const getSearchTermValue = (label) => {
        const term = searchTerm.find((term) => term[0] === label.toLowerCase().replace(/\s+/g, ''));
        if (term) {
            return term[1];
        }
        return "";
    }

    const clear = () => {
        setStatusButton("");
        clearFilters();
    }
    //depending on where the filter will be, use this to automatically localize the name
    const source = (label) => {
       switch (label){
        case 'User ID':
            //returns translation key (in json files)
            return 'userId'
        case 'Appointment ID':
            return 'appointmentId'
        case 'Account ID':
            return 'accountId'
        case 'Username':
            return 'username'
       case 'Payment Type':
            return 'paymentType'
        case 'Email':
            return 'email'
        case 'City':
            return 'city'
           case  'Invoice ID' :
               return 'invoiceId'

              case 'State':
                  return 'state'
       }




    }

    return (
        <Popup trigger={<button className="filter-popup-button"> <IoSearchSharp /> <span className="tooltip-text">{t('openFilter')}</span> </button>} position="left center">
            <div className="filter-popup">
                <>
                    <section className="filter-section">
                        <h4>{t('filterBy')}</h4>
                        {labels.map((label) => (
                            <div key={label}>                            
                                {label !== "Status" ? (
                                    <div>
                                        <label>{t(source(label))}</label>
                                        <input type="text" name={label} maxLength="60" value={getSearchTermValue(label)} onChange={(event) => handleInputChange(event, label)} />
                                    </div>
                                    ) : (
                                    <div>
                                        {}
                                        {src === "appointment" && (
                                        <>
                                            <label>{t('status')}</label>
                                        <input className={statusButton === "Requested" ? "status-button active" : "status-button"} type="button" name={label + "3"} maxLength="60" value={t('requested')} onClick={() => handleInputChange('Requested', label)} />
                                        <input className={statusButton === "Scheduled" ? "status-button active" : "status-button"} type="button" name={label + "1"} maxLength="60" value={t('scheduled')} onClick={() => handleInputChange('Scheduled', label)} />
                                        <input className={statusButton === "Cancelled" ? "status-button active" : "status-button"} type="button" name={label + "2"} maxLength="60" value={t('cancelled')} onClick={() => handleInputChange('Cancelled', label)} />
                                        <input className={statusButton === "Completed" ? "status-button active" : "status-button"} type="button" name={label + "4"} maxLength="60" value={t('completed')} onClick={() => handleInputChange('Completed', label)} />
                                        </>
                                        )}
                                        {src === "feedback" && (
                                        <>
                                        {}
                                            <label>{t('status')}</label>
                                        <input className={statusButton === "Invisible" ? "status-button active" : "status-button"} type="button" name={label + "5"} maxLength="60" value="Invisible" onClick={() => handleInputChange('Invisible', label)} />
                                        <input className={statusButton === "Visible" ? "status-button active" : "status-button"} type="button" name={label + "6"} maxLength="60" value="Visible" onClick={() => handleInputChange('Visible', label)} />
                                        </>
                                        )}


                                        {src === "invoices" && (
                                            <>
                                                {}
                                                <label>{t('state')}</label>
                                                <input className={statusButton === "Completed" ? "status-button active" : "status-button"} type="button" name={label + "5"} maxLength="60" value="Completed" onClick={() => handleInputChange('Completed', label)} />
                                                <input className={statusButton === "Pending" ? "status-button active" : "status-button"} type="button" name={label + "6"} maxLength="60" value="Pending" onClick={() => handleInputChange('Pending', label)} />
                                                <input className={statusButton === "OverDue" ? "status-button active" : "status-button"} type="button" name={label + "6"} maxLength="60" value="OverDue" onClick={() => handleInputChange('OverDue', label)} />

                                            </>
                                        )}
                                        </div>




                                )}
                               

                            </div>
                        ))}

                    </section>
                    <button className="filter-button" onClick={clear}>{t('clear')}</button>
                </>
            </div>
        </Popup>
    );
}

export default Filter;