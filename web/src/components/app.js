import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            totalDist: [],
            options: {},
            ps: [],
            addInfo: "",
            serverReturned: null,
            svg: false,
            bottomRow: []
        }
    };

    render() {
        let pairs = this.state.allPairs;
        let displayKeys = this.state.keys;
        this.state.ps = pairs.map((pp) => {
            let finalData={};
            finalData["start name"] = pp["start name"];
            finalData["end name"] = pp["end name"];
            finalData["distance"] = pp["distance"];
            finalData["cumulativeDistance"] = pp["cumulativeDistance"];

            for (let i in this.state.options){
                if(this.state.options[i]) {
                    finalData["start " + i] = pp["start " + i];
                    finalData["end " + i] = pp["end " + i];
                }
            }
            return <Pair {...finalData}/>;
        });
        let boxes = [];
        for (let i in this.state.options) {
            boxes.push(
                <label >
                    <input
                        type="checkbox"
                        value={i}
                        onChange={() => {
                            this.state.options[i] = !this.state.options[i];
                            console.log("options: ", this.state.options[i]);
                            this.forceUpdate()
                        }}
                    />
                    {i}
                </label>
            )
        }
        let serverLocations;
        let locs;
        let svg;
        if (this.state.serverReturned) { // if this.state.serverReturned is not null
            //Get list of numbers
            serverLocations = this.state.serverReturned.items;

            /*Create an array of HTML list items. The Array.map function in Javascript passes each individual element
            * of an array (in this case serverLocations is the array and "location" is the name chosen for the individual element)
            * through a function and returns a new array with the mapped elements.
            * In this case f: location -> <li>location.name</li>, so the array will look like:
            * [<li>[name1]</li>,<li>[name2]</li>...]
            */
            // set the local variable scg to this.state.serverReturned.svg
        }
        if(this.state.svg){
            svg = this.state.serverReturned.svg+"?"+ new Date().getTime();
        }

        return (
            <div className="app-container">
                <h1>T15 - Wolf Pack</h1>
                <h3>TripCo Itinerary</h3>
                <input className="search-button" type="text" placeholder="Enter a search like denver"
                       onKeyUp={this.keyUp.bind(this)} autoFocus/>
                <br/>
                {/* Display the array of HTML list items created on line 18 */}
                <h1>
                    {/* In the constructor, this.state.serverReturned.svg is not assigned a value. This means the image
                    will only display once the serverReturned state variable is set to the received json in line 73*/}
                    <img width="75%" src={svg}/>
                </h1>
                <Home
                    getData={this.getData.bind(this)}
                    pairs={this.state.ps}
                    totalDistance={this.state.totalDist}
                    keys={displayKeys}
                    checkBoxes={boxes}
                    info={this.state.addInfo}
                    bottomRow={this.state.bottomRow}
                />
            </div>
        )
    }



    getData(idFile, infoFile) {
        let pairs = [];
        let totalDist = 0;
        for (let i  in idFile) {
            let start;
            let end;
            let startArrayFound = false;
            let endArrayFound = false;
            let startIndex = idFile[i]['sourceID'];
            let endIndex = idFile[i]['destinationID'];
            let dist = idFile[i]['distance'];
            // loop through the second JSON file to find the information associated with idFile[i]:


            for (let j = 0; j < Object.values(infoFile).length; j++) {


                if (infoFile[j]['id'] == startIndex) {
                    start = infoFile[j];
                    startArrayFound = true;
                }
                if (infoFile[j]['id'] == endIndex) {
                    end = infoFile[j];
                    endArrayFound = true;
                }
                // stop looping if both start and end info has been found:
                if (startArrayFound && endArrayFound) {
                    break;
                }
            }

            totalDist += dist;

            // add the extra info of the second JSON to p:
            let p = {};
            for (let key in start) {
                p["start " + key] = "Start " + key + ": " + start[key];
            }
            for (let key in end) {
                p["end " + key] = "End " + key + ": " + end[key];
            }
            p["distance"] = "Distance: " + dist;
            p["cumulativeDistance"] = "Cumulative: " + totalDist;

            pairs.push(p); //add object to pairs array
        }
        for(let k  in infoFile[0]){
            if(k != "name") {
                this.state.options[k] = false;
            }
        }



        let totalRow =
                <tr>
                    <td colSpan="2">Total:</td>
                    <td>{totalDist}</td>
                </tr>;
        this.setState({
            allPairs: pairs,
            totalDist: totalDist,
            bottomRow: totalRow,
            addInfo: "Check boxes below to show/hide information about your trip!"
        });

    }
    // This function waits until enter is pressed on the event (input)
    // A better implementation would be to have a Javascript form with an onSubmit event that calls fetch
    keyUp(event) {
        if (event.which === 13) { // Waiting for enter to be pressed. Enter is key 13: https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
            this.fetch(event.target.value); // Call fetch and pass whatever text is in the input box
        }
    }

    // This function sends `input` the server and updates the state with whatever is returned
    async fetch(input) {
        // Create object to send to server

        /*  IMPORTANT: This object must match the structure of whatever
            object the server is reading into (in this case DataClass) */
        let newMap = {
            queries : [input],
            doWhat: "query",
        };
        try {
            // Attempt to send `newMap` via a POST request
            // Notice how the end of the url below matches what the server is listening on (found in java code)
            // By default, Spark uses port 4567
            let jsonReturned = await fetch(`http://localhost:4567/receive`,
                {
                    method: "POST",
                    body: JSON.stringify(newMap)
                });
            // Wait for server to return and convert it to json.
            let ret = await jsonReturned.json();
            // Log the received JSON to the browser console
            console.log("Got back ", JSON.parse(ret));
            // set the serverReturned state variable to the received json.
            // this way, attributes of the json can be accessed via this.state.serverReturned.[field]
            this.setState({
                serverReturned: JSON.parse(ret)
            });
            console.log("search results are: ", this.state.serverReturned);
            let serverLocations = this.state.serverReturned.items;
            let trip = [];
            for (let i in serverLocations){
                trip.push(serverLocations[i]["map"]["id"]);
            }
            newMap = {
                queries : trip,
                doWhat: "plan",
            };
            jsonReturned = await fetch(`http://localhost:4567/receive`,
                {
                    method: "POST",
                    body: JSON.stringify(newMap)
                });
            // Wait for server to return and convert it to json.
            ret = await jsonReturned.json();
            // Log the received JSON to the browser console
            console.log("Got back ", JSON.parse(ret));
            // set the serverReturned state variable to the received json.
            // this way, attributes of the json can be accessed via this.state.serverReturned.[field]

            this.setState({
                serverReturned: JSON.parse(ret),
                svg: true
            });
            console.log("route is: ", this.state.serverReturned);
            let items = [];
            for (let i in this.state.serverReturned.items){
                items.push(this.state.serverReturned.items[i]["map"]);
            }

            this.getData(this.state.serverReturned.itinerary, items);
        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
    }
}