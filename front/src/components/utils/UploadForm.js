import React, {useState} from "react";
import BoxDiv from "./style/BoxDiv";
import BaseService from "../../services/BaseService";
import ErrorsHandler from "./Utils";
import {useNavigate} from "react-router-dom";

const UploadForm = ({changeStatusHandler}) => {

    const navigate = useNavigate();
    const [file, setFile] = useState();

    const fileSubmitHandler = (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('file', file);
        BaseService
            .sendFile(formData)
            .then(result => {
                if (result.hasOwnProperty('imageId')) {
                    changeStatusHandler({message: 'image saved', type: 'success'});
                    document.getElementById('inlineFormUploadFile').value = '';
                } else {
                    changeStatusHandler({message: 'unknown result data', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }

    const handleFile = (event) => {
        let file = event.target.files[0];
        setFile(file);
    }
    return(
        <BoxDiv>
            <form onSubmit={fileSubmitHandler} className="row row-cols-lg-auto align-items-center p-3" encType="multipart/form-data">
                <div className="col-12">
                    <label className="visually-hidden" htmlFor="inlineFormUploadFile">Username</label>
                    <div className="input-group">
                        <input type="file"
                               className="form-control"
                               id="inlineFormUploadFile"
                               placeholder="select file"
                               name="file"
                               onChange={e => handleFile(e)}
                        />
                    </div>
                </div>
                <div className="col-12">
                    <button type="submit" className="btn btn-outline-secondary">Upload</button>
                </div>
            </form>
        </BoxDiv>
    );
}

export default UploadForm;