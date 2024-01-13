import React from "react";
import "./Filter.css";
import Popup from 'reactjs-popup';
import { IoSearchSharp } from "react-icons/io5";


function Filter({labels, onInputChange, searchTerm, clearFilters}) {

    const [statusButton, setStatusButton] = React.useState();

    const handleInputChange = (event, label) => {
            if (label === "Status") {
                setStatusButton(event.target.value);
            }
            onInputChange(label, event.target.value);
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
                        <h4>Filter by: </h4>
                        {labels.map((label) => (
                            <div key={label}>
                                {label !== "Status" ? (
                                    <div>
                                        <label>{label}</label>
                                        <input type="text" name={label} maxLength="60" value={getSearchTermValue(label)} onChange={(event) => handleInputChange(event, label)} />
                                    </div>
                                    ) : (
                                    <div>
                                        <label>{label}</label>
                                        <input className={statusButton === "Scheduled" ? "status-button active" : "status-button"} type="button" name={label + "1"} maxLength="60" value="Scheduled" onClick={(event) => handleInputChange(event, label)} />
                                        <input className={statusButton === "Cancelled" ? "status-button active" : "status-button"} type="button" name={label + "2"} maxLength="60" value="Cancelled" onClick={(event) => handleInputChange(event, label)} />
                                        <input className={statusButton === "Completed" ? "status-button active" : "status-button"} type="button" name={label + "3"} maxLength="60" value="Completed" onClick={(event) => handleInputChange(event, label)} />
                                    </div>
                                )}
                            </div>
                        ))}
                    </section>
                    <button className="filter-button" onClick={clear}>Clear</button>
                </>
            </div>
        </Popup>
    );
}

export default Filter;