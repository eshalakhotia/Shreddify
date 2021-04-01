import './Questionnaire.css';
import React from "react";

function closeFindWorkouts() {
    document.getElementById("questionnaire").style.display = "none";
}

function addTarget() {
    const text = document.getElementById("target").value;
    /*const textnode=document.createTextNode(text);
    const node = document.createElement("Li");
    node.appendChild(textnode);
    document.getElementById("list").appendChild(node);*/
    let c = document.createElement('div');
    c.className = 'targetArea';
    c.innerHTML = "<p id='targetItem'>" + text + "</p>";
    document.getElementById("list").appendChild(c);
}

function Questionnaire(props) {

    return (
        <body>
            <div id="questionnaire" className="questionnaire-background">
                <div className="questionnaire">
                    <span className="close" onClick={closeFindWorkouts}>&times;</span>
                    <h1>Find Workouts</h1>
                    <h3>Help us recommend the perfect workouts for you!</h3>
                    <p id="energyp">Low Medium High</p>
                    <h2 id="energy">Energy level?</h2>
                    <input id="slider1" type="range" min="0" max="100"/>

                    <h2 id="time">How much time do you have?</h2>
                    <p id="timep">5min 10min 30min 1hr</p>
                    <input id="slider2" type="range" min="0" max="100"/>

                    <input id="target" name="target" placeholder="What areas do you want to target?"/>
                    <input type='button' value='Add' id='add' onClick={addTarget}/>

                    <div id="list" className="list">
                    </div>

                    <input type='button' value='Go!' id='go'/>
                </div>
            </div>
        </body>

    )
}

export default Questionnaire;