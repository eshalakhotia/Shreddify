import './Sidebar.css';


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

    return (
        <div id="mySidenav" className="sidenav">
            <a href="javascript:void(0)" className="closebtn" onClick={closeNav}>&times;</a>
            <a href="javascript:void(0)" className="openbtn" onClick={openNav}>
                <div className="bar1"/>
                <div className="bar2"/>
                <div className="bar3"/>
            </a>
            <div className="links">
                <a href="#" onClick={findWorkouts}>Find Workouts</a>
                <a href="#">Explore</a>
            </div>

        </div>

    )
}

export default Sidebar;