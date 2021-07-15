import React, { Component } from "react";
import axios from "axios";
import './clients.css'

class NewClient extends Component {
    state = {
        firstName: "",
        lastName: ""
    };

    onTitleChange = e => {
        this.setState({
            firstName: e.target.value
        });
    };

    onBodyChange = e => {
        this.setState({
            lastName: e.target.value
        });
    };
    handleSubmit = e => {
        e.preventDefault();
        const data = {
            title: this.state.title,
            body: this.state.body
        };
        axios
            .post("http://informers-informer.apps.ocsp1.sg-lab.local/db", data)
            .then(res => console.log(res))
            .catch(err => console.log(err));
    };

    render() {
        return (
            <div align="center">
                <div>Add newcomer</div>
                <form onSubmit={this.handleSubmit}>
                    <input
                        placeholder="firstName" value={this.state.firstName}
                        onChange={this.onTitleChange} required
                    />
                    <input
                        placeholder="lastName" value={this.state.lastName}
                        onChange={this.onBodyChange} required
                    />
                    <button type="submit">Create Post</button>
                </form>
            </div>
        );
    }
}

export default NewClient;