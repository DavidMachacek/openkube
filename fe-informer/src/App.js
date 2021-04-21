import logo from './logo.svg';
import './App.css';
import React, { Component } from 'react';
import Clients from './components/clients';

class App extends Component {

    state = {
        clients: []
    }

    componentDidMount() {
        fetch('http://informers-informer.apps.ocsp1.sg-lab.local/db')
            .then(res => res.json())
            .then((data) => {
                this.setState({ clients: data })
            })
            .catch(console.log)
    }
    render() {
        return (
            <Clients clients={this.state.clients} />
        )
    }
}
export default App;
