// import './SignUp.css';
// import Backend from './Backend.js'
// import React from "react";
// import {Link, Redirect} from "react-router-dom";
//
// /**
//  * Find Workouts questionnaire, opened from Sidebar
//  */
// class SignUp {
//
//     constructor() {
//         this.input = {
//             username: "",
//             password: "",
//             energy: 0,
//         }
//         this.output = {
//             success: false,
//             error: ''
//         }
//         this.submitted = false
//     }
//
//
//     //updates energy field when user changes slider
//     changeEnergy() {
//         //console.log("energy: " + document.getElementById("energySlider").value)
//         this.input.energy = document.getElementById("energySlider").value
//         console.log("energy: " + this.input.energy)
//     }
//
//     //renders questionnaire
//     renderSignUp() {
//         return (
//             <div id="SignUp" className="sign-background">
//                 <h1>Sign Up</h1>
//                 <div className="signingIn">
//                         <div id="username">
//                             <input id="username" name="username" placeholder="Username" onChange={(e) => this.onUserInput(e.target)} value={this.state.username}/>
//                         </div>
//                         <div id="password">
//                             <input type="password" id="password" name="password" placeholder="Password" onChange={(e) => this.onUserInput(e.target)} value={this.state.password}/>
//                         </div>
//                         <p id="energyp">Low Medium High</p>
//                     <h2 id="energy">Energy level?</h2>
//                     <input id="energySlider" type="range" min="0" max="100" onInput={this.changeEnergy.bind(this)}/>
//                     </div>
//                 <div id="right">
//                     <Link to={{
//                         pathname: "/SignUp",
//                         state: {
//                             input: this.input
//                         }
//                     }}>
//                         <button id='go'><span>Sign Up!</span></button>
//                     </Link>
//                 </div>
//     </div>
//         )
//     }
// }
// export default SignUp;
