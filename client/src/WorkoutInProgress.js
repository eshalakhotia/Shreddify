import React from "react";
import './WorkoutInProgress.css'
import Backend from "./Backend";
import {Link} from "react-router-dom";

class WorkoutInProgress extends React.Component {
    constructor(props) {
        super(props)
        //console.log(props.location)
        if (props.location.state !== null) {
            this.exercises = props.location.state.exercises //table of tables: [name, time, reps, measurement]
            this.cycles = props.location.state.cycles
            this.id = props.location.state.id
            this.name = props.location.state.name
            console.log("workout name: " + this.name)
        }
        this.state = {
            currExerciseNum: 0,
            currExercise: this.exercises[0],
            activeTimer: false,
            currCycle: 1,
            user: '',
            error: ''
        }
    }

    componentDidMount() {
        this.state.activeTimer = false
        document.getElementById('pauseTimer').style.display = "none"
        document.getElementById('resetTimer').style.display = "none"
        document.getElementById('prevButton').style.display = "none"
        document.getElementById('finishButton').style.display = "none"
        document.getElementById('finish-screen').style.display = "none"

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
            mins = Math.round(Math.floor(seconds / 60))
        }
        seconds = seconds % 60
        if (seconds < 10) {
            seconds = "0" + seconds
        }
        this.time = mins + ":" + seconds

        let duration = ''
        if (this.state.currExercise[3] == 'reps') { //measure by reps
            duration = this.state.currExercise[2] + ' reps or '
        }
        duration += this.state.currExercise[1] + ' seconds'

        return (
            <div className='workout-screen'>
                <div id="wrapper">
                    <div id="left">
                        <div id="left-top">
                            <h3>Exercise #{this.state.currExerciseNum + 1} of Cycle #{this.state.currCycle}</h3>
                            <h1>{this.state.currExercise[0]}</h1> {/*exercise name*/}
                            <h2>{duration}</h2>   {/*exercise reps and time*/}
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
                            <div id="finish">
                                <button id='finishButton' onClick={this.finishWorkout.bind(this)}>
                                    Finish Workout ></button>
                            </div>
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

        //if the next exercise will be the last AND it is the last cycle
        if (this.state.currExerciseNum + 1 === this.exercises.length - 1 && this.state.currCycle === this.cycles) {
            document.getElementById('nextButton').style.display = "none"
            document.getElementById('finishButton').style.display = "inline"
        }

        console.log("cycle of exercise that was just finished: " + this.state.currCycle)
        console.log("index of exercise that was just finished: " + this.state.currExerciseNum)

        //if the current exercise is the last AND there are more cycles to do
        if (this.state.currExerciseNum === this.exercises.length - 1 && this.state.currCycle < this.cycles) {

            console.log("going to next cycle")
            //console.log("going back to exercise: " + this.exercises[0])
            //console.log("exercise time: " + this.exercises[0][1])
            console.log("going back to exercise: " + this.exercises[0][0])


            //start at first exercise, increment curr cycle
            this.setState((state) => {
                return {
                    currExerciseNum: 0,
                    currExercise: this.exercises[0],
                    activeTimer: false,
                    currCycle: state.currCycle + 1
                }
            })
        }
        //if the current exercise is not the last
        else {
            //increment exercise number, cycle remains
            this.setState((state) => {
                return {
                    currExerciseNum: state.currExerciseNum + 1,
                    currExercise: this.exercises[state.currExerciseNum + 1],
                    activeTimer: false,
                }
            })
        }



    }

    prevExercise() {
        //this.activeTimer = false
        document.getElementById('startTimer').style.display = "inline"
        document.getElementById('pauseTimer').style.display = "none"
        document.getElementById('resetTimer').style.display = "none"

        //going back to first exercise of first cycle
        if (this.state.currExerciseNum === 1 && this.state.currCycle === 1) {
            document.getElementById('prevButton').style.display = "none"
        }
        document.getElementById('nextButton').style.display = "inline"
        document.getElementById('finishButton').style.display = "none"

        //if currently on first exercise but there are still previous cycles
        if (this.state.currExerciseNum === 0 && this.state.currCycle > 1) {
            //go back to last exercise of previous cycle

            this.setState((state) => {
                return {
                    currExerciseNum: this.exercises.length - 1,
                    currExercise: this.exercises[this.exercises.length - 1],
                    activeTimer: false,
                    currCycle: state.currCycle - 1
                }
            })
        }
        else {
            //go back to previous exercise, cycle remains
            this.setState((state) => {
                return {
                    currExerciseNum: state.currExerciseNum - 1,
                    currExercise: this.exercises[state.currExerciseNum - 1],
                    activeTimer: false
                }
            })
        }

    }

    async finishWorkout() {
        console.log("workout id: " + this.id);
        console.log("clicked finish")
        const info = await Backend.endWorkout(this.id)
        if (info === null) {
            console.log("response is null, something wrong with backend handler")
        } else {
            if (info.success) {
                console.log("success, username: " + info.results.username)
                this.setState({user: info.results})
            } else {
                this.setState({error: info.error})
            }
            document.getElementById("finish-screen").style.display = "block";
        }
    }

    render () {
        return (
            <div>
                {this.renderCurrentExercise()}
                <div id="finish-screen" className="finish-background">
                    <div className = "finish-modal">
                        <h1>Congratulations!</h1>
                        <h3>You just finished:</h3>
                        <h2>{this.name}</h2>
                        <Link to={{
                                    pathname: "/Home",
                                    state: {username: this.state.user.username, user: this.state.user}
                                }}>
                            <div id="home-button-back">
                                <button id="home-button" >Go Back Home</button>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        )
    }
}


export default WorkoutInProgress;