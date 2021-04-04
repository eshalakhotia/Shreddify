import './Questionnaire.css';
import Backend from './Backend.js'
import React from "react";
import {Link} from "react-router-dom";

/**
 * Find Workouts questionnaire, opened from Sidebar
 */
class Questionnaire {

    constructor() {
        this.energy = 0
        this.time = 0
        this.targets = []
        this.flexibility = true;
    }

    //closes questionnaire modal
    closeQuestionnaire() {
        document.getElementById("questionnaire").style.display = "none";
        //remove options so they are not re-rendered
        const options = document.getElementById("options")
        while (options.firstChild) {
            options.removeChild(options.firstChild)
        }
    }

    //renders a target area option on the form
    addTargets(area) {
        const option = document.createElement('div');
        option.className ='targetButton'
        option.id = area
        option.style.backgroundColor = 'white';
        option.innerHTML = "<p id='targetItem'>" + area + "</p>"
        //renders to targets section
        if (document.getElementById("options") !== null) {
            document.getElementById("options").appendChild(option)
        }
        option.addEventListener('click', () => {
            this.selectTarget(area)
        })
    }

    //fills target area option when user selects it
    selectTarget(option) {
        const el = document.getElementById(option);
        if (el.style.backgroundColor === "white") {
            //select
            el.style.backgroundColor = "hotpink";
            el.style.color = "white";

            //add to targets list
            this.targets.push(option)
        } else {
            //deselect
            el.style.backgroundColor = "white";
            el.style.color = "hotpink";

            //remove from targets list
            const index = this.targets.indexOf(option)
            this.targets.splice(index, 1)
        }
    }

    //updates energy field when user changes slider
    changeEnergy() {
        //console.log("energy: " + document.getElementById("energySlider").value)
        this.energy = document.getElementById("energySlider").value
        console.log("energy: " + this.energy)
    }

    //updates time field when user changes slider
    changeTime() {
        this.time = document.getElementById("timeSlider").value
        console.log("time: " + this.time)
    }

    changeFlexibility() {
        //console.log("toggle: " + document.getElementById("toggle").checked);
        //checked true = Not flexible, checked false = flexible
        this.flexibility = !document.getElementById("toggle").checked;
    }

    //sends request to Backend when Go button is pressed
    onSubmit() {
        console.log("energy: " + this.energy)
        console.log("time: " + this.time)
        console.log("flexibility: " + this.flexibility)
        console.log("targets: " + this.targets)
        const recs = Backend.getRecs(this.energy, this.time, this.flexibility, this.targets)
        //give result to Recommendations page
    }

    //renders questionnaire
    renderQuestionnaire() {
        return (
            <div id="questionnaire" className="questionnaire-background">
                <div className="questionnaire">
                    <span className="close" onClick={this.closeQuestionnaire}>&times;</span>
                    <h1>Find Workouts</h1>
                    <h3>Help us recommend the perfect workouts for you!</h3>
                    <p id="energyp">Low Medium High</p>
                    <h2 id="energy">Energy level?</h2>
                    <input id="energySlider" type="range" min="0" max="100" onInput={this.changeEnergy.bind(this)}/>

                    <h2 id="time">How much time do you have?</h2>
                    <p id="timep">5min · · 30min · · 60min</p>
                    <input id="timeSlider" type="range" min="5" max="60" step="5" onInput={this.changeTime.bind(this)}/>

                    <p id="flexibilityp">Would you go slightly overtime for the perfect workout?</p>
                    <p id="yes">Sure!</p>
                    <label className="switch">
                        <input id="toggle" type="checkbox" onChange={this.changeFlexibility.bind(this)}/>
                            <span className="slider round"></span>
                    </label>
                    <p id="no">No</p>

                    <div id="wrapper">
                        <div id="left">
                            <h2 id="targets">What areas do you want to target?</h2>
                            <div id="options" className="options">
                            </div>

                            {this.addTargets("abs")}
                            {this.addTargets("legs")}
                            {this.addTargets("back")}
                            {this.addTargets("arms")}
                            {this.addTargets("glutes")}
                            {this.addTargets("cardio")}
                        </div>

                        <div id="right">
                           <button id='go' onClick={this.onSubmit.bind(this)}>Go!</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default Questionnaire;

/*
    <input id="target" name="target" placeholder="What areas do you want to target?"/>
   <input type='button' value='Add' id='add' onClick={this.addTarget}/>

   <div id="list" className="list">
    </div>

    //renders a new target option based on user input
    addTarget() {
        const text = document.getElementById("target").value;
        let c = document.createElement('div');
        c.className = 'targetArea';
        c.innerHTML = "<p id='targetItem'>" + text + "</p>";
        document.getElementById("list").appendChild(c);
    }
 */


/*
  <div id="options" className="options">
                        <div className="targetButton" id="abs" onClick={this.selectTarget("abs")}><p id='targetItem'>abs</p></div>
                        <div className="targetButton" onClick={this.selectTarget}><p id='targetItem'>legs</p></div>
                        <div className="targetButton" onClick={this.selectTarget}><p id='targetItem'>back</p></div>
                        <div className="targetButton" onClick={this.selectTarget}><p id='targetItem'>arms</p></div>
                        <div className="targetButton" onClick={this.selectTarget}><p id='targetItem'>cardio</p></div>
                    </div>
 */