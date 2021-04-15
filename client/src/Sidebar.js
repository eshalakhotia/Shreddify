import './Sidebar.css';
import {Link, Redirect} from "react-router-dom";


/**
 * Side navigation bar
 */


/* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
function openNav() {
    document.getElementById("mySidenav").style.width = "300px";
    document.getElementById("main").style.marginLeft = "300px";
    document.getElementById("mySidenav").getElementsByClassName("closebtn")[0].style.display = "block";
    document.getElementById("mySidenav").getElementsByClassName("openbtn")[0].style.display = "none";
    document.getElementById("mySidenav").getElementsByClassName("links")[0].style.display = "block";
}

/* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
function closeNav() {
    document.getElementById("mySidenav").style.width = "80px";
    document.getElementById("main").style.marginLeft = "80px";
    document.getElementById("mySidenav").getElementsByClassName("closebtn")[0].style.display = "none";
    document.getElementById("mySidenav").getElementsByClassName("openbtn")[0].style.display = "block";
    document.getElementById("mySidenav").getElementsByClassName("links")[0].style.display = "none";
}

function explore() {

}

function renderRedirect() {
    return <Redirect to={{
            pathname: "/Recommendations",
            }}/>

}

function Sidebar(props) {

    /*const closeNav = e => {
        props.closeNav()
    }

    const openNav = e => {
        props.openNav()
    }*/

    const findWorkouts = e => {
        props.findWorkouts()
    }

    const toExplore = e => {
        props.toExplore()
    }

    return (
        <div id="mySidenav" className="sidenav">
            <a href="javascript:void(0)" className="closebtn" onClick={closeNav}>&times;</a>
            <a href="javascript:void(0)" className="openbtn" onClick={openNav}>
                <div className="bar1"/>
                <div className="bar2"/>
                <div className="bar3"/>
            </a>
            <div className="links">
                <Link to={{
                    pathname: "/Home",
                }}>
                    <a href="#">Home</a>
                </Link>
                <a href="#" onClick={findWorkouts}>Find Workouts</a>
                <Link to={{
                    pathname: "/Explore",
                }}>
                    <a href="#">Explore</a>
                </Link>
            </div>
        </div>

    )
}

export default Sidebar;