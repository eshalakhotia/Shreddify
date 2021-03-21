import './App.css';
import './TextBox.js';
import TextBox from "./TextBox";
import React, {useState} from 'react';
import { AwesomeButton } from "react-awesome-button";
import "react-awesome-button/dist/styles.css";
import axios from 'axios';

function Route() {

    const [startLat, setStartLat] = useState(0);
    const [startLon, setStartLon] = useState(0);
    const [endLat, setEndLat] = useState(0);
    const [endLon, setEndLon] = useState(0);
    const [route, setRoute] = useState('N/A');

    function Button() {
        return (
            <AwesomeButton type="primary" onPress = {requestRoute}>Submit</AwesomeButton>
        );
    }

    const requestRoute = () => {
        //const reqObj
        const toSend = {
            srclat: startLat,
            srclong: startLon,
            destlat: endLat,
            destlong: endLon
        };
        //const config
        let config = {
            headers: {
                "Content-Type": "application/json",
                'Access-Control-Allow-Origin': '*',
            }
        };

        axios.post(
            'http://localhost:4567/route',
            toSend,
            config
        ).then(response => {
            console.log(response.data);
            setRoute(response.data['route']);
        }).catch(e => {
            console.error(e)
        });
    };

    return (
        <div className="Route">
            <h2>Route</h2>

            <TextBox label={"Start Latitude"} change={setStartLat}/>
            <TextBox label={"Start Longitude"} change={setStartLon}/>
            <TextBox label={"End Latitude"} change={setEndLat}/>
            <TextBox label={"End Longitude"} change={setEndLon}/>
            <Button/>

            <h3>{route[0]}</h3>
            <h3>{route[1]}</h3>
            <h3>{route[2]}</h3>
            <h3>{route[3]}</h3>
        </div>
    );
}

export default Route;
