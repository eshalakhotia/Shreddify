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
            currExercise: this.exercises[0],
            activeTimer: false
        }

       // console.log("curr exercise num = " + this.state.currExerciseNum)
        //console.log("curr exercise name = " + this.exercises[0].name)
        //this.currExerciseNum = 0;
        //this.currExercise = this.exercises[this.currExerciseNum]
        console.log("first exercise: " + this.state.currExercise[0])
        //this.activeTimer = false
    }

    componentDidMount() {
        this.state.activeTimer = false
        document.getElementById('pauseTimer').style.display = "none"
        document.getElementById('resetTimer').style.display = "none"
        document.getElementById('prevButton').style.display = "none"
        document.getElementById('finishButton').style.display = "none"

        if (this.state.currExerciseNum === this.exercises.length - 1) { //first exercise is the last
            document.getElementById('finishButton').style.display = "inline"
            document.getElementById('nextButton').style.display = "none"
        } else {
            document.getElementById('finishButton').style.display = "none"
        }
    }

    renderCurrentExercise() {
        let seconds = this.state.currExercise[1]
        let mins = 0
        if (seconds >= 60) {
            mins = seconds / 60
        }
        seconds = seconds % 60
        if (seconds < 10) {
            seconds = "0" + seconds
        }
        this.time = mins + ":" + seconds

        return (
            <div className='workout-screen'>
                <div id="wrapper">
                    <div id="left">
                        <div id="left-top">
                            <h3>Currently on Exercise #{this.state.currExerciseNum + 1}</h3>
                            <h1>{this.state.currExercise[0]}</h1> {/*exercise name*/}
                            <h2>{this.state.currExercise[1]} seconds</h2>   {/*exercise time*/}
                        </div>
                        <div id="left-bottom">
                            <button id='prevButton' onClick={this.prevExercise.bind(this)}>
                                &lt; Previous Exercise</button>
                        </div>
                    </div>
                    <div id="right">
                        <div id="right-top">
                            <div id="timerBack">
                                <h3 id="timer">{this.time}</h3>
                            </div>
                            <button id='startTimer' onClick={this.startTimer.bind(this)}>
                            Start</button>
                            <button id='pauseTimer' onClick={this.pauseTimer.bind(this)}>
                            Pause</button>
                            <button id='resetTimer' onClick={this.resetTimer.bind(this)}>
                            Reset</button>
                        </div>
                        <div id="right-bottom">
                            <button id='nextButton' onClick={this.nextExercise.bind(this)}>
                                Next Exercise ></button>
                            <button id='finishButton'>
                                Finish Workout ></button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    startTimer() {
        document.getElementById('startTimer').style.display = "none"
        document.getElementById('pauseTimer').style.display = "inline"
        document.getElementById('resetTimer').style.display = "inline"
        this.state.activeTimer = true
        console.log("just started, active timer: " + this.state.activeTimer)
        this.runTimer()
    }

    pauseTimer() {
        this.state.activeTimer = false
        console.log("just paused, active timer: " + this.state.activeTimer)
        document.getElementById('startTimer').style.display = "inline"
        document.getElementById('pauseTimer').style.display = "none"
    }

    resetTimer() {
        this.state.activeTimer = false
        document.getElementById('timer').innerHTML = this.time;
        document.getElementById('startTimer').style.display = "inline"
        document.getElementById('pauseTimer').style.display = "none"
        document.getElementById('resetTimer').style.display = "none"
    }

    runTimer() {
        if (this.state.activeTimer) {
            const presentTime = document.getElementById('timer').innerHTML;
            const timeArray = presentTime.split(':');
            let m = timeArray[0];
            //console.log("m: " + m)
            let s = this.checkSecond((timeArray[1] - 1));
            //console.log("s: " + s)
            if(s == 59) {//subtract 1 minute
                //console.log("s = 00")
                //document.getElementById('timer').innerHTML = "0:00";
                m=m-1
                console.log("subtracting a minute")
            }
            if (m<0) {
                alert('Timer Completed!')
                document.getElementById('pauseTimer').style.display = "none"
                document.getElementById('startTimer').style.display = "none"
                document.getElementById('resetTimer').style.display = "inline"
            } else {
                document.getElementById('timer').innerHTML =
                    m + ":" + s;
                setTimeout(this.runTimer.bind(this), 1000);
            }
        }
    }

    checkSecond(sec) {
        if (sec < 10 && sec >= 0) {sec = "0" + sec} // add zero in front of numbers < 10
        if (sec < 0) {sec = "59"}
        return sec;
    }

    nextExercise() {
        //this.activeTimer = false
        document.getElementById('startTimer').style.display = "inline"
        document.getElementById('pauseTimer').style.display = "none"
        document.getElementById('resetTimer').style.display = "none"
        document.getElementById('prevButton').style.display = "block"
        if (this.state.currExerciseNum + 1 === this.exercises.length - 1) {//going to last exercise
            document.getElementById('nextButton').style.display = "none"
            document.getElementById('finishButton').style.display = "inline"
        }
        this.setState((state) => {
            return {
                currExerciseNum: state.currExerciseNum + 1,
                currExercise: this.exercises[state.currExerciseNum + 1],
                activeTimer: false
            }
        })
    }

    prevExercise() {
        //this.activeTimer = false
        document.getElementById('startTimer').style.display = "inline"
        document.getElementById('pauseTimer').style.display = "none"
        document.getElementById('resetTimer').style.display = "none"
        if (this.state.currExerciseNum === 1) { //going back to first exercise
            document.getElementById('prevButton').style.display = "none"
        }
        document.getElementById('nextButton').style.display = "inline"
        document.getElementById('finishButton').style.display = "none"
        this.setState((state) => {
            return {
                currExerciseNum: state.currExerciseNum - 1,
                currExercise: this.exercises[state.currExerciseNum - 1],
                activeTimer: false
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