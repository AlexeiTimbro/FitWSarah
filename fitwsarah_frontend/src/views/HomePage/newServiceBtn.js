import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { Modal } from 'react-bootstrap';
import configData from "../../config.json";
import AddServiceButton from "../../components/PersonalTrainerPanel/addService";


function RequestNewFitnessService() {

      const [fitnessDataToSend, setFitnessDataToSend] = useState(null);
      const [showForm, setShowForm] = useState(false);
      const handleInputChange = (e) => {
        const {name, value} = e?.target || {};
        const updatedData = {
          ...fitnessDataToSend,
          [name]: value,
      };
      console.log(updatedData);
      setFitnessDataToSend(updatedData);
      }
  
      const handleDurationChange = (e, b) => {
        const {name, value} = e?.target || {};
        const {name2, value2} = b?.target || {};
        var updatedData;
        if (name){
        updatedData = {
          duration: `${value}`,
          };
        }
        if (name2){
          updatedData = {
            duration: ` ${value2}`
          };
        }
      console.log(updatedData);
      setFitnessDataToSend(updatedData);
      }
  
      return (
        <Modal show={showForm} onHide={()=>setShowForm(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add a Fitness Service</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <form>
          <div className="form-group">
            <input type="text" id="fitnessServiceName" maxLength="50" placeholder="Fitness Service Title" name="fitnessServiceName" required  onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
          <input type="number" id="duration" maxLength="2" placeholder="Duration" name="duration" required  onChange={(e) => handleDurationChange(e, null)} />
            <select id="durationType" name="durationType"  onChange={(b) => handleDurationChange(null, b)}  required>
                <option value="minutes">minutes</option>
                <option value="hour(s)">hour(s)</option>
            </select>
          </div>
          <div className="form-group">
            <input type="number" id="price"  placeholder="Price" name="price" required onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
            <input type="textarea" id="description"  placeholder="Description" name="description" required onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
            <input type="textarea" id="other"  placeholder="Other Information (Optional)" name="other"  onChange={(e) => handleInputChange(e)} />
          </div>
          
          <AddServiceButton fitnessDataToSend={fitnessDataToSend}></AddServiceButton>

        </form>
        </Modal.Body>

    </Modal>
      );

}
export default RequestNewFitnessService;