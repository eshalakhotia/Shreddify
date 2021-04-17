/*
All network interactions with backend
 */

import axios from 'axios';

const backendURL = 'http://localhost:4567';

/*
    Resolves to JSON from backend, or null.
    Never throws
*/
async function makePOST(route, body) {
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
        }
    };
    try {
        const ret = await axios.post(
            route, body, config
        );
        return ret.data;
    } catch (e) {
        console.error(e);
        return null;
    }
};

//returns recs based on energy (0-100), time (5-60), targets (list of strings)
function getRecs(energy, time, flexibility, targets) {
    return makePOST(backendURL + '/recs', {
        energy: energy,
        time: time,
        flexibility: flexibility,
        targets: targets
    });
}

async function signUp(username, password, level) {
    return makePOST(backendURL + '/signup', {
        username: username,
        password: password,
        level: level,
    });
}

//returns info about login (success, error, specified user)
async function login(username, password) {
    console.log("in Backend post request")
    return makePOST(backendURL + '/login', {
        username: username,
        password: password
    });
}

async function explore() {
    return makePOST(backendURL + '/explore', {});
}

async function logOut() {
    return makePOST(backendURL + '/logout', {});
}

async function endWorkout(workoutID) {
    console.log("making backend post request")
    return makePOST(backendURL + '/finishworkout', {
        workoutID: workoutID
    });
}

export default{
    getRecs: getRecs,
    login: login,
    signUp: signUp,
    explore: explore,
    logOut: logOut,
    endWorkout: endWorkout
}