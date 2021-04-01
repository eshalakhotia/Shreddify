import React from 'react';
import './Home.css';
import Sidebar from './Sidebar';

class Home extends React.Component {


    async componentDidMount() {

    }

    /* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
    openNav() {
        //document.getElementById("mySidenav").style.width = "250px";
        //document.getElementById("main").style.marginLeft = "250px";
    }

    /* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
    closeNav() {
        console.log("in closeNav")
        console.log(document.getElementById("mySidenav"))
        //document.getElementById("mySidenav").style.width = "0";
        //document.getElementById("main").style.marginLeft = "0";
    }

    render() {
        console.log("rendering Home")
        return (
            <div id="Home" className="Home">
                <div id="mySidenav" className="sidenav">
                    <a href="javascript:void(0)" className="closebtn" onClick={this.closeNav}>&times;</a>
                    <a href="#">Create New</a>
                    <a href="#">Explore</a>
                    <a href="#">Discover</a>
                    <a href="#">Questionnaire</a>
                </div>

                <span onClick={this.openNav}>open</span>
                <div id="main">
                    <h1>Home</h1>
                </div>
            </div>
        );
    }
}

export default Home;