import React, {Component} from 'react'
import './clients.css'
import axios from "axios";

const Clients = ({clients}) => {

    function deleteFun(id) {
        axios
            .delete("http://informers-informer.apps.ocsp1.sg-lab.local/db/" + id)
            .then(res => console.log(res))
            .catch(err => console.log(err));
    };

      return (
          <div className="container">
            <div className="table">
                <div className="table-header">
                    <div className="header__item">ID</div>
                    <div className="header__item">First Name</div>
                    <div className="header__item">Last Name</div>
                    <div className="header__item">Created</div>
                    <div className="header__item">Action</div>
                </div>
                <div className="table-content">
                {clients.map((client) => (
                    <div class="table-row">
                            <div class="table-data">{client.id}</div>
                            <div class="table-data">{client.firstName}</div>
                            <div class="table-data">{client.lastName}</div>
                            <div class="table-data">{client.created}</div>
                            <div className="table-data">
                                <button type="delete" onClick={deleteFun(client.id)}>Delete</button>
                            </div>
                    </div>
                ))}
                </div>
            </div>
        </div>
    )
};

export default Clients