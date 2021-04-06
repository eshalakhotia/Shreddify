import React from 'react';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";
import Backend from "./Backend";

class Recommendations extends React.Component {

    constructor(props) {
        document.getElementById("questionnaire").style.display = "none";
        super(props);
        this.questionnaire = new Questionnaire();

        //info passed in from Questionnaire

        //console.log("info passed from questionnaire: " + props.location.state.input.time)
        //this.error = props.location.state.error
        //this.success = props.location.state.success
        //this.results = props.location.state.results
        this.state = {
            input : {
                energy: props.location.state.input.energy,
                time: props.location.state.input.time,
                targets: props.location.state.input.targets,
                flexibility: props.location.state.input.flexibility
            },
            output : {
                error: '',
                success: '',
                recs: []
            }
        }

        this.getRecommendations();
    }

    //gets recommendations from backend and updates output state
    async getRecommendations() {
        const recs = await Backend.getRecs(this.state.input.energy, this.state.input.time,
            this.state.input.flexibility, this.state.input.targets)

        if (recs === null) {
            console.log("something wrong with backend, response is null")

            this.setState(() => {
                return {
                    output: {
                        error: 'Sorry, something went wrong with getting your recommendations :('
                    }
                }
            })
        }
        else {
            this.setState(() => {
                return {
                    output: {
                        error: recs.error,
                        success: recs.success,
                        results: recs.results
                    }
                }
            })
            console.log("success? " + this.state.output.success)
            console.log("error? " + this.state.output.error)
            console.log("results" + this.state.output.results)
        }
    }


    componentDidMount() {
        document.getElementById("questionnaire").style.display = "none";
    }

    //opens Questionnaire if needed
    openQuestionnaire() {
        document.getElementById("questionnaire").style.display = "block";
    }

    //renders Recommendations page after Questionnaire submitted
    render() {
        console.log("rendering Recommendations")
        const errorMessage = this.state.output.error === '' ? '' :
            `${this.state.output.error}`
        return (
            <div id="Recommendations" className="Recommendations">
                <Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire}/*closeNav={this.closeNav} openNav={this.openNav}*//>

                <div id="main">
                    <h1>Our Picks For You</h1>
                    <div id="inputs" className="inputs">
                        <h2>You searched for workouts with the following attributes:</h2>
                        <h3>Energy: {this.state.input.energy}</h3>
                        <h3>Time: {this.state.input.time}</h3>
                        <h3>Flexible: {this.state.input.flexibility}</h3>
                        <h3>Target Areas: {this.state.input.targets}</h3>
                    </div>
                    <hr/>
                    <div id="results" className="results">
                        <div className="error">
                            <h3>{errorMessage}</h3>
                        </div>
                    </div>
                </div>
                {this.questionnaire.renderQuestionnaire()};
            </div>
        )
    }
}

export default Recommendations;