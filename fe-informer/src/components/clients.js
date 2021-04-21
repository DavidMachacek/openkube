import React from 'react'
import './clients.css'

const Clients = ({clients}) => {
    return (
        <div className="container">
            <div className="table">
                <div className="table-header">
                    <div className="header__item">ID</div>
                    <div className="header__item">First Name</div>
                    <div className="header__item">Last Name</div>
                    <div className="header__item">Created</div>
                </div>
                <div className="table-content">
                {clients.map((client) => (
                    <div class="table-row">
                            <div class="table-data">{client.id}</div>
                            <div class="table-data">{client.firstName}</div>
                            <div class="table-data">{client.lastName}</div>
                            <div class="table-data">{client.created}</div>
                    </div>
                ))}
                </div>
            </div>
        </div>
    )
};

export default Clients