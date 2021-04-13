import React from "react";
import './WorkoutInProgress.css'

class WorkoutInProgress extends React.Component {
    constructor(props) {
        super(props)
        //console.log(props.location)
        if (props.location.state !== null) {
            this.exercises = props.location.state.exercises
        }
        //this.exercises = props.location.state.exercises //list of exercises
        this.state = {
            currExerciseNum: 0,
            currExercise: this.exercises[0]
        }

       // console.log("curr exercise num = " + this.state.currExerciseNum)
        //console.log("curr exercise name = " + this.exercises[0].name)
        //this.currExerciseNum = 0;
        //this.currExercise = this.exercises[this.currExerciseNum]
        console.log("first exercise: " + this.state.currExercise[0])
    }

    renderCurrentExercise() {
        return (
            <div className='workout-screen'>
                <h3>Currently on Exercise #{this.state.currExerciseNum + 1}</h3>
                <h1>{this.state.currExercise[0]}</h1> {/*exercise name*/}
                <h2>{this.state.currExercise[1]} seconds</h2>   {/*exercise time*/}
                <button id='go' onClick={this.nextExercise.bind(this)}>Next</button>
                <div>Time left = <span id="timer">0:{this.state.currExercise[1]}</span></div>
                <button id='timer' onClick={this.startTimer.bind(this)}>Start</button>
            </div>
        )
    }

    startTimer() {
        const presentTime = document.getElementById('timer').innerHTML;
        const timeArray = presentTime.split(':');
        let m = timeArray[0];
        let s = this.checkSecond((timeArray[1] - 1));
        if(s === 59) {m=m-1} //subtract 1 minute
        if (m<0) {
            alert('timer completed')
        } else {
            document.getElementById('timer').innerHTML =
                m + ":" + s;
            setTimeout(this.startTimer.bind(this), 1000);
        }
    }

    checkSecond(sec) {
        if (sec < 10 && sec >= 0) {sec = "0" + sec}; // add zero in front of numbers < 10
        if (sec < 0) {sec = "59"};
        return sec;
    }

    nextExercise() {
        this.setState((state) => {
            return {
                currExerciseNum: state.currExerciseNum + 1,
                currExercise: this.exercises[state.currExerciseNum + 1]
            }
        })
    }

    render () {
        return (
            <div>
                {this.renderCurrentExercise()}
            </div>
        )
    }
}


export default WorkoutInProgress;