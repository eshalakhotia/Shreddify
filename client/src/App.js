import './App.css';
import Backend from "./Backend";
import React from 'react';
import { Redirect } from 'react-router-dom';
import {
    BrowserRouter as Router,
    Route,
    Link,
} from "react-router-dom";

/**
 * Login page
 */
class App extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: ''
        }
    }


    async componentDidMount() {

    }

    /**
     * Updates state upon user input in text boxes.
     * @param target
     */
    onUserInput(target) {
        console.log(target)
        console.log("user input detected")
        const newUsername = target.name === 'username' ? target.value : this.state.username;
        const newPassword = target.name === 'password' ? target.value : this.state.password;
        this.setState((state) => {
            console.log("setting state")
            console.log("new username typed: " + newUsername)
            return {
                username: newUsername,
                password: newPassword
            }
        });
        //console.log("testing document");
        //console.log(document.getElementById("username").style);
    }

    async onLogin() {
        //gets boolean success, User, error message
        const info = await Backend.login(this.state.username, this.state.password)
        if (info === null) {
            console.log("response is null, something wrong with backend handler")
        } else {
            console.log("success: " + info.success)
            console.log("error: " + info.error)
            console.log("User: " + info.results)
        }
    }

    render() {
        return (
            <div className="App">
                <h1>SHREDDIFY</h1>
                <div className = "Controls" >
                    <div id="username">
                        <input id="username" name="username" placeholder="Username" onChange={(e) => this.onUserInput(e.target)} value={this.state.username}/>
                    </div>

                    <div id="password">
                        <input type="password" id="password" name="password" placeholder="Password" onChange={(e) => this.onUserInput(e.target)} value={this.state.password}/>
                    </div>

                    <div id="login">
                        <Link to="/Home"><button id="loginButton" onClick={this.onLogin.bind(this)}>Log In</button></Link>
                    </div>

                    <div id="signup">
                        <Link to="/Home"><button id="signupButton">Sign Up</button></Link>
                    </div>

                </div>
            </div>
        )
    }
}

export default App;
