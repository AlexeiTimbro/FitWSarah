import React from "react";
import "./Filter.css";
import Popup from 'reactjs-popup';
import { IoSearchSharp } from "react-icons/io5";
import { useTranslation } from "react-i18next";


function Filter({labels, onInputChange, searchTerm, clearFilters}) {

    const [statusButton, setStatusButton] = React.useState();
    const { t } = useTranslation('filter');

    const handleInputChange = (event, label) => {
            if (label === "Status") {
                setStatusButton(event);
            }
            onInputChange(label, event);
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

    return (
        <Popup trigger={<button className="filter-popup-button"> <IoSearchSharp /> <span className="tooltip-text">Open Filter</span> </button>} position="left center">
            <div className="filter-popup">
                <>
                    <section className="filter-section">
                        <h4>{t('filterBy')}</h4>
                        {labels.map((label) => (
                            <div key={label}>
                                {label !== "Status" ? (
                                    <div>
                                        <label>{t('appointmentId')}</label>
                                        <input type="text" name={label} maxLength="60" value={getSearchTermValue(label)} onChange={(event) => handleInputChange(event, label)} />
                                    </div>
                                    ) : (
                                    <div>
                                        <label>{t('status')}</label>
                                        <input className={statusButton === "Requested" ? "status-button active" : "status-button"} type="button" name={label + "3"} maxLength="60" value={t('requested')} onClick={() => handleInputChange('Requested', label)} />
                                        <input className={statusButton === "Scheduled" ? "status-button active" : "status-button"} type="button" name={label + "1"} maxLength="60" value={t('scheduled')} onClick={() => handleInputChange('Scheduled', label)} />
                                        <input className={statusButton === "Cancelled" ? "status-button active" : "status-button"} type="button" name={label + "2"} maxLength="60" value={t('cancelled')} onClick={() => handleInputChange('Cancelled', label)} />
                                        <input className={statusButton === "Completed" ? "status-button active" : "status-button"} type="button" name={label + "4"} maxLength="60" value={t('completed')} onClick={() => handleInputChange('Completed', label)} />
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