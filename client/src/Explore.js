import React from 'react';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";
import Backend from "./Backend";
import WorkoutDiv from "./WorkoutDiv"
import './Explore.css'

class Explore extends React.Component {

    // const requestExplore = () => {
    //     const toSend = {
    //     };
    //     let config = {
    //         headers: {
    //             "Content-Type": "application/json",
    //             'Access-Control-Allow-Origin': '*',
    //         }
    //     }
    //
    //     axios.post(
    //         'http://localhost:4567/explore',
    //         toSend,
    //         config
    //     )
    //         .then(response => {
    //             //clearing the current canvas to redraw ways
    //
    //         })
    //
    //         .catch(function (error) {
    //             console.log(error);
    //
    //         });
    // }

    render() {
        console.log("rendering wouts")
        // const errorMessage = this.state.output.error === '' ? '' :
        //     `${this.state.output.error}`
        return (
            <div id="exploring" className="exploring">
                {/*<Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire}/*closeNav={this.closeNav} openNav={this.openNav}*//>*/}

                <div id="main">
                    <h1>Searching</h1>
                    <div id="inputs" className="inputs">
                        <h3>Here are some workouts other users enjoyed:</h3>
                    </div>
                    <hr/>
                    <div id="results" className="results">
                        <div className="error">
                            <h3>{errorMessage}</h3>
                        </div>
                        {/*{this.renderWorkouts()}*/}
                    </div>
                </div>
                {/*{this.questionnaire.renderQuestionnaire()};*/}
            </div>
        )
    }
}

export default Explore;