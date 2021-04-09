import './Workout.css'
import {Link} from "react-router-dom";
import React from "react";

class WorkoutDiv {
    renderTargetTags() {
        let tagDivs = []
        this.targetTags.forEach((t) => {
            tagDivs.push(<div id="targetTag" className="Tag">{t}</div>)
        })
        return tagDivs
    }
    renderEquipTags() {
        let tagDivs = []
        this.equipTags.forEach((t) => {
            tagDivs.push(<div id="equipTag" className="Tag">{t}</div>)
        })
        return tagDivs
    }
    /*
    Renders a workout thumbnail with info
     */
    renderWorkout(props) {
        const name = props.name
        console.log("workoutName = " +  name)
        const time = props.time
        //console.log("workoutTime = " + time)
        const difficulty = props.difficulty
        //console.log("workoutDiff = " + difficulty)
        const targets = props.targets
        console.log("targets: " + targets)
        const equipment = props.equipment
        console.log("equipment: " + equipment)

        this.targetTags = []
        //const tagDiv = document.getElementById('tags')
        //if (tagDiv != null) {
            //if (!tagDiv.firstChild) {
            targets.forEach((target) => {
                    //console.log(target)
                    //const tag = document.createElement('div')
                    //tag.className = "Tag"
                    //tag.innerHTML = target
                    this.targetTags.push(target)
                    //tagDiv.appendChild(tag)
                }
            )
            //}
        //}

        this.equipTags = []
        equipment.forEach((equip) => {
            this.equipTags.push(equip)
        })


        return (
            <Link to={{
                pathname: "/Workout",
            }}>
                <div className="Workout">
                    <div id="wrapper">
                        <div id="left">
                            <h3>{name}</h3>
                            <h3>Total Time: {time} minutes</h3>
                            <h3>Difficulty: {difficulty} / 10</h3>
                        </div>
                        <div id="middle">
                            <h3>Exercises</h3>
                            <div id="tags">
                                <h3>Target Areas</h3>
                                {this.renderTargetTags()}
                            </div>
                        </div>
                        <div id="right">
                            <h3>Equip</h3>
                            {this.renderEquipTags()}
                        </div>
                    </div>
                </div>
            </Link>
        )
    }
}

export default WorkoutDiv