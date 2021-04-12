// // import React, { useState } from "react";
// // import Form from "react-bootstrap/Form";
// // import { useHistory } from "react-router-dom";
// // import LoaderButton from "../components/LoaderButton";
// // import { useAppContext } from "../libs/contextLib";
// // import { useFormFields } from "../libs/hooksLib";
// // import "./Signup.css";
// //
// // export default function Signup() {
// //     const [fields, handleFieldChange] = useFormFields({
// //         email: "",
// //         password: "",
// //         confirmPassword: "",
// //         confirmationCode: "",
// //     });
// //     const history = useHistory();
// //     const [newUser, setNewUser] = useState(null);
// //     const { userHasAuthenticated } = useAppContext();
// //     const [isLoading, setIsLoading] = useState(false);
// //
// //     function validateForm() {
// //         return (
// //             fields.email.length > 0 &&
// //             fields.password.length > 0 &&
// //             fields.password === fields.confirmPassword
// //         );
// //     }
// //
// //     function validateConfirmationForm() {
// //         return fields.confirmationCode.length > 0;
// //     }
// //
// //     async function handleSubmit(event) {
// //         event.preventDefault();
// //
// //         setIsLoading(true);
// //
// //         setNewUser("test");
// //
// //         setIsLoading(false);
// //     }
// //
// //     async function handleConfirmationSubmit(event) {
// //         event.preventDefault();
// //
// //         setIsLoading(true);
// //     }
// //
// //     function renderConfirmationForm() {
// //         return (
// //             <Form onSubmit={handleConfirmationSubmit}>
// //                 <Form.Group controlId="confirmationCode" size="lg">
// //                     <Form.Label>Confirmation Code</Form.Label>
// //                     <Form.Control
// //                         autoFocus
// //                         type="tel"
// //                         onChange={handleFieldChange}
// //                         value={fields.confirmationCode}
// //                     />
// //                     <Form.Text muted>Please check your email for the code.</Form.Text>
// //                 </Form.Group>
// //                 <LoaderButton
// //                     block
// //                     size="lg"
// //                     type="submit"
// //                     variant="success"
// //                     isLoading={isLoading}
// //                     disabled={!validateConfirmationForm()}
// //                 >
// //                     Verify
// //                 </LoaderButton>
// //             </Form>
// //         );
// //     }
// //
// //     function renderForm() {
// //         return (
// //             <Form onSubmit={handleSubmit}>
// //                 <Form.Group controlId="email" size="lg">
// //                     <Form.Label>Email</Form.Label>
// //                     <Form.Control
// //                         autoFocus
// //                         type="email"
// //                         value={fields.email}
// //                         onChange={handleFieldChange}
// //                     />
// //                 </Form.Group>
// //                 <Form.Group controlId="password" size="lg">
// //                     <Form.Label>Password</Form.Label>
// //                     <Form.Control
// //                         type="password"
// //                         value={fields.password}
// //                         onChange={handleFieldChange}
// //                     />
// //                 </Form.Group>
// //                 <Form.Group controlId="confirmPassword" size="lg">
// //                     <Form.Label>Confirm Password</Form.Label>
// //                     <Form.Control
// //                         type="password"
// //                         onChange={handleFieldChange}
// //                         value={fields.confirmPassword}
// //                     />
// //                 </Form.Group>
// //                 <LoaderButton
// //                     block
// //                     size="lg"
// //                     type="submit"
// //                     variant="success"
// //                     isLoading={isLoading}
// //                     disabled={!validateForm()}
// //                 >
// //                     Signup
// //                 </LoaderButton>
// //             </Form>
// //         );
// //     }
// //
// //     return (
// //         <div className="Signup">
// //             {newUser === null ? renderForm() : renderConfirmationForm()}
// //         </div>
// //     );
// // }
// import React, { useState } from "react";
// import Form from "react-bootstrap/Form";
// import { useHistory } from "react-router-dom";
// import LoaderButton from "../components/LoaderButton";
// import { useAppContext } from "../libs/contextLib";
// import { useFormFields } from "../libs/hooksLib";
// import "./Signup.css";
//
// /**
//  * Find Workouts questionnaire, opened from Sidebar
//  */
// class SignUp {
//
//     constructor() {
//         this.input = {
//             email: "",
//         password: "",
//         confirmPassword: "",
//         confirmationCode: "",
//         }
//         this.output = {
//             email: "",
//             password: "",
//             confirmPassword: "",
//             confirmationCode: "",
//         }
//         this.submitted = false
//     }
//
//     //renders a target area option on the form
//     addTargets(area) {
//         const option = document.createElement('div');
//         option.className ='targetButton'
//         option.id = area
//         option.style.backgroundColor = 'white';
//         option.innerHTML = "<p id='targetItem'>" + area + "</p>"
//         //renders to targets section
//         if (document.getElementById("options") !== null) {
//             document.getElementById("options").appendChild(option)
//         }
//         option.addEventListener('click', () => {
//             this.selectTarget(area)
//         })
//     }
//
//     //fills target area option when user selects it
//     selectTarget(option) {
//         const el = document.getElementById(option);
//         if (el.style.backgroundColor === "white") {
//             //select
//             el.style.backgroundColor = "hotpink";
//             el.style.color = "white";
//
//             //add to targets list
//             this.input.targets.push(option)
//         } else {
//             //deselect
//             el.style.backgroundColor = "white";
//             el.style.color = "hotpink";
//
//             //remove from targets list
//             const index = this.input.targets.indexOf(option)
//             this.input.targets.splice(index, 1)
//         }
//     }
//
//     //updates energy field when user changes slider
//     changeEnergy() {
//         //console.log("energy: " + document.getElementById("energySlider").value)
//         this.input.energy = document.getElementById("energySlider").value
//         console.log("energy: " + this.input.energy)
//     }
//
//     //updates time field when user changes slider
//     changeTime() {
//         this.input.time = document.getElementById("timeSlider").value
//         console.log("time: " + this.input.time)
//     }
//
//     changeFlexibility() {
//         //console.log("toggle: " + document.getElementById("toggle").checked);
//         //checked true = Not flexible, checked false = flexible
//         this.input.flexibility = !document.getElementById("toggle").checked;
//         console.log("flexibility: " + this.input.flexibility)
//     }
//
//     //(!!IGNORE, DOING IN RECOMMENDATIONS CURRENTLY) sends request to Backend when Go button is pressed
//     async onSubmit() {
//         console.log("energy: " + this.input.energy)
//         console.log("time: " + this.input.time)
//         console.log("flexibility: " + this.input.flexibility)
//         console.log("targets: " + this.input.targets)
//         const recs = await Backend.getRecs(this.input.energy, this.input.time, this.input.flexibility, this.input.targets)
//         //results passed to Recommendations.js through Link
//
//         if (recs === null) {
//             console.log("something wrong with backend, response is null")
//             this.output.error = 'Sorry, something went wrong with getting your recommendations :('
//         }
//         else {
//             this.output.success = recs.success
//             this.output.error = recs.error
//             this.output.results = recs.results
//             console.log("success? " + this.output.success)
//             console.log("error? " + this.output.error)
//             console.log("results" + this.output.results)
//         }
//
//         this.submitted = true;
//
//         //closes Questionnaire
//         this.closeQuestionnaire()
//     }
//
//     //redirects to Recommendations once necessary, passes recs info
//     renderRedirect() {
//         if (this.submitted) {
//             return <Redirect
//                 to={{
//                     pathname: "/Recommendations",
//                     state: {
//                         error: this.output.error}
//                 }}/>
//         }
//     }
//
//
//     //renders questionnaire
//     renderQuestionnaire() {
//         return (
//             <div id="questionnaire" className="questionnaire-background">
//                 <div className="questionnaire">
//                     <span className="close" onClick={this.closeQuestionnaire}>&times;</span>
//                     <h1>Find Workouts</h1>
//                     <h3>Help us recommend the perfect workouts for you!</h3>
//                     <p id="energyp">Low Medium High</p>
//                     <h2 id="energy">Energy level?</h2>
//                     <input id="energySlider" type="range" min="0" max="100" onInput={this.changeEnergy.bind(this)}/>
//
//                     <h2 id="time">How much time do you have?</h2>
//                     <p id="timep">5min · · 30min · · 60min</p>
//                     <input id="timeSlider" type="range" min="5" max="60" step="5" onInput={this.changeTime.bind(this)}/>
//
//                     <p id="flexibilityp">Would you go slightly overtime for the perfect workout?</p>
//                     <p id="yes">Sure!</p>
//                     <label className="switch">
//                         <input id="toggle" type="checkbox" onChange={this.changeFlexibility.bind(this)}/>
//                         <span className="slider round"></span>
//                     </label>
//                     <p id="no">No</p>
//
//                     <div id="wrapper">
//                         <div id="left">
//                             <h2 id="targets">What areas do you want to target?</h2>
//                             <div id="options" className="options">
//                             </div>
//
//                             {this.addTargets("abs")}
//                             {this.addTargets("legs")}
//                             {this.addTargets("back")}
//                             {this.addTargets("arms")}
//                             {this.addTargets("glutes")}
//                             {this.addTargets("cardio")}
//                         </div>
//
//
//                         <div id="right">
//                             <Link to={{
//                                 pathname: "/Recommendations",
//                                 state: {
//                                     input: this.input
//                                 }
//                             }}>
//                                 <button id='go'>Go!</button>
//                             </Link>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         )
//     }
// }
// export default Questionnaire;