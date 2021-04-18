import './App.css';
import Backend from "./Backend";
import React from 'react';
import { Redirect } from 'react-router-dom';
import SignUp from './SignUp';
import './SignUp.css'


/**
 * Login page
 */
class App extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            level: 50,
            user: '',
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
            this.setState({error: 'ERROR: Could not login. Please make sure backend is running.'})
        } else {
            console.log("success: " + info.success)
            console.log("error: " + info.error)
            console.log("User: " + info.results)
            //if successful
            if (info.success) {
                this.setState({authenticated: true, user: info.results})
            } else {
                this.setState({error: info.error})
            }
        }
    }

    async onCreate() {
        //gets boolean success, User, error message
        console.log("creating new account: ")
        console.log("username: " + this.state.username)
        console.log("password: " + this.state.password)
        console.log("level: " + this.state.level)
        const info = await Backend.signUp(this.state.username, this.state.password, this.state.level)
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
            this.setState({authenticated: true, user: info.results})
        } else {
            this.setState({error: info.error})
        }
    }
    openSignUpSheet() {
        document.getElementById("SignUp").style.display = "block";
    }

    onSign() {
        document.getElementById("level").style.display = "block";
        document.getElementById("signup").style.display = "none";
        document.getElementById("login").style.display = "none";
        document.getElementById("createacc").style.display = "block";
    }

    changeLevel() {
        this.state.level = document.getElementById("levelSlider").value
    }

    //redirects to Homepage if authenticated
    renderRedirect() {
        if (this.state.authenticated) {
            return <Redirect to={{
                pathname: "/Home",
                state: {username: this.state.username, user: this.state.user}  }}/>
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

                    <div id="level">
                        <h2 id="levelh">What's your general fitness level?</h2>
                        <p id="levelp">Getting started · · Ready to ramp up · · Juiced & Shredded!</p>
                        <input id="levelSlider" type="range" min="0" max="100" step="2" onInput={this.changeLevel.bind(this)}/>
                    </div>


                    <div className="loginError">{errorMessage}</div>

                    {this.renderRedirect()}

                    <div id="login">
                        <button id="loginButton" onClick={this.onLogin.bind(this)}>Log In</button>
                    </div>

                    <div id="signup">
                        <button id="signupButton" onClick={this.onSign.bind(this)}> Sign Up </button>
                    </div>

                    <div id="createacc">
                        {/*<Link to="/Home">*/}
                        <button id="createaccbutton" onClick={this.onCreate.bind(this)}> Create Account </button>
                        {/*</Link>*/}
                    </div>
                </div>
            </div>

        )
    }
}

export default App;
