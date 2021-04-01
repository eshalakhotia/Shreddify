import React from 'react';
import './Home.css';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";

class Home extends React.Component {


    async componentDidMount() {

    }

    /* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
    /*openNav() {
        document.getElementById("mySidenav").style.width = "300px";
        document.getElementById("main").style.marginLeft = "300px";
        document.getElementById("mySidenav").getElementsByClassName("closebtn")[0].style.display = "block";
        document.getElementById("mySidenav").getElementsByClassName("openbtn")[0].style.display = "none";
        document.getElementById("mySidenav").getElementsByClassName("links")[0].style.display = "block";
    }*/

    /* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
    /*closeNav() {
        document.getElementById("mySidenav").style.width = "80px";
        document.getElementById("main").style.marginLeft = "80px";
        document.getElementById("mySidenav").getElementsByClassName("closebtn")[0].style.display = "none";
        document.getElementById("mySidenav").getElementsByClassName("openbtn")[0].style.display = "block";
        document.getElementById("mySidenav").getElementsByClassName("links")[0].style.display = "none";
    }*/

    findWorkouts() {
        document.getElementById("questionnaire").style.display = "block";
    }

    /*closeFindWorkouts() {
        document.getElementById("questionnaire").style.display = "none";
    }*/


    render() {
        console.log("rendering Home")
        return (
            <div id="Home" className="Home">
                <Sidebar className="Sidebar" findWorkouts={this.findWorkouts}/*closeNav={this.closeNav} openNav={this.openNav}*//>

                <div id="main">
                    <h1>Welcome Back, [User]!</h1>
                    <h2>My Workouts</h2>
                    <h2>My Achievements</h2>
                </div>

                <Questionnaire/>
            </div>
        );
    }
}

export default Home;