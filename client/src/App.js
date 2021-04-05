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
            password: '',
            authenticated: false,
            error: ''
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
        this.setState(() => {
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

    //calls backend to check authentication, signals redirect if authenticated
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
        //if successful
        if (info.success) {
            console.log("going to home")
            this.setState({authenticated: true})
        } else {
            this.setState({error: info.error})
        }
    }

    //redirects to Homepage if authenticated
    renderRedirect() {
        if (this.state.authenticated) {
            return <Redirect to="/Home"/>
        }
    }

    //renders everything on login page
    render() {
        const errorMessage = this.state.error === '' ? '' :
            `${this.state.error}`
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


                    <div className="loginError">{errorMessage}</div>

                    {this.renderRedirect()}

                    <div id="login">
                        <button id="loginButton" onClick={this.onLogin.bind(this)}>Log In</button>
                    </div>


                    <div id="signup">
                        <Link to="/Home">
                            <button id="signupButton">Sign Up</button>
                        </Link>
                    </div>

                </div>
            </div>
        )
    }
}

export default App;
