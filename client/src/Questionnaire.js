import './Questionnaire.css';
import Backend from './Backend.js'
import React from "react";
import {Link, Redirect} from "react-router-dom";

/**
 * Find Workouts questionnaire, opened from Sidebar
 */
class Questionnaire {

    constructor() {
        this.input = {
            energy: 0,
            time: 0,
            targets: [],
            flexibility: true
        }
        this.output = {
            success: false,
            results: [],
            error: ''
        }
        this.submitted = false
    }

    //closes questionnaire modal
    closeQuestionnaire() {
        //remove options so they are not re-rendered when questionnaire is opened again
        const options = document.getElementById("options")
        while (options.firstChild) {
            options.removeChild(options.firstChild)
        }
        document.getElementById("questionnaire").style.display = "none";
    }

    //renders a target area option on the form
    addTarget(area) {
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
            el.style.backgroundColor = "#ff6969";
            el.style.color = "white";

            //add to targets list
            this.input.targets.push(option)
        } else {
            //deselect
            el.style.backgroundColor = "white";
            el.style.color = "#ff6969";

            //remove from targets list
            const index = this.input.targets.indexOf(option)
            this.input.targets.splice(index, 1)
        }
    }

    //updates energy field when user changes slider
    changeEnergy() {
        //console.log("energy: " + document.getElementById("energySlider").value)
        this.input.energy = document.getElementById("energySlider").value
        console.log("energy: " + this.input.energy)
    }

    //updates time field when user changes slider
    changeTime() {
        this.input.time = document.getElementById("timeSlider").value
        console.log("time: " + this.input.time)
    }

    changeFlexibility() {
        //console.log("toggle: " + document.getElementById("toggle").checked);
        //checked true = Not flexible, checked false = flexible
        this.input.flexibility = !document.getElementById("toggle").checked;
        console.log("flexibility: " + this.input.flexibility)
    }

    //(!!IGNORE, DOING IN RECOMMENDATIONS CURRENTLY) sends request to Backend when Go button is pressed
    /*async onSubmit() {
        console.log("energy: " + this.input.energy)
        console.log("time: " + this.input.time)
        console.log("flexibility: " + this.input.flexibility)
        console.log("targets: " + this.input.targets)
        const recs = await Backend.getRecs(this.input.energy, this.input.time, this.input.flexibility, this.input.targets)
        //results passed to Recommendations.js through Link

        if (recs === null) {
            console.log("something wrong with backend, response is null")
            this.output.error = 'Sorry, something went wrong with getting your recommendations :('
        }
        else {
            this.output.success = recs.success
            this.output.error = recs.error
            this.output.results = recs.results
            console.log("success? " + this.output.success)
            console.log("error? " + this.output.error)
            console.log("results" + this.output.results)
        }

        this.submitted = true;

        //closes Questionnaire
        this.closeQuestionnaire()
    }*/

    //redirects to Recommendations once necessary, passes recs info
    /*renderRedirect() {
        if (this.submitted) {
            return <Redirect
                to={{
                    pathname: "/Recommendations",
                    state: {
                        error: this.output.error}
                }}/>
        }
    }*/


    //renders questionnaire
    renderQuestionnaire() {
        return (
            <div id="questionnaire" className="questionnaire-background">
                <div className="questionnaire">
                    <span className="close" onClick={this.closeQuestionnaire}>Close&nbsp;&times;</span>
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

                            {this.addTarget("abs")}
                            {this.addTarget("legs")}
                            {this.addTarget("back")}
                            {this.addTarget("arms")}
                            {this.addTarget("glutes")}
                            {this.addTarget("cardio")}
                        </div>


                        <div id="right">
                            <Link to={{
                                pathname: "/Recommendations",
                                state: {
                                    input: this.input
                                }
                            }}>
                                <button id='go'><span>Find Workouts!</span></button>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default Questionnaire;

/*
<Link to={{
   pathname: "/Recommendations",
   state: {
       success: this.output.success,
       error: this.output.error,
       results: this.output.results
   }
}}>
   <button id='go' onClick={this.onSubmit.bind(this)}>Go!</button>
</Link>
 */

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