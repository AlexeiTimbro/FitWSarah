import React from "react";
import "./Filter.css";
import Popup from 'reactjs-popup';
import { IoSearchSharp } from "react-icons/io5";

function Filter({labels, onInputChange}) {
    const handleInputChange = (event, label) => {
            onInputChange(label, event.target.value);
        };

        return (
        

        <Popup trigger={<button className="filter-popup-button"> <IoSearchSharp /> <span className="tooltip-text">Open Filter</span> </button>} position="left center">
            <div className="filter-popup">
                <section className="filter-section">
                    <h4>Filter by: </h4>
                    {labels.map((label) => (
                        <div>
                            <label>{label}</label>
                            <input type="text" name={label} maxLength="60" onChange={(event) => handleInputChange(event, label)}/>
                        </div>
                    ))}
            </section>
        </div>
        </Popup>
    );
}

export default Filter;