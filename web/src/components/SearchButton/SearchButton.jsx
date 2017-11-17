import React, {Component} from 'react';

class SearchButton extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            locations: [],
            units: this.props.units,
            opt: this.props.opt,
            serverReturned: null,
            returnList : this.props.returnList
        };}

    keyUp(event) {
        if (event.which === 13) { // Waiting for enter to be pressed. Enter is key 13:
            //https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
            this.fetch(event.target.value); // Call fetch and pass whatever text is in the input box
        }
    }

    // This function sends `input` the server and updates the state with whatever is returned
    async fetch(input) {
        // Create object to send to server

        /*  IMPORTANT: This object must match the structure of whatever
            object the server is reading into (in this case DataClass) */
        this.setState({locations: []})

        let newMap = {
            queries : [input],
            doWhat: "query",
            units: this.state.units,
            opt: this.state.opt,
        };
        try {
            // Attempt to send `newMap` via a POST request
            // Notice how the end of the url below matches what the server is listening on (found in java code)
            // By default, Spark uses port 4567
            let serverUrl = window.location.href.substring(0, window.location.href.length - 6) + ":4567/receive";
            let jsonReturned = await fetch(serverUrl,
                {
                    method: "POST",
                    body: JSON.stringify(newMap)
                });
            // Wait for server to return and convert it to json.
            let ret = await jsonReturned.json();

            // set the serverReturned state variable to the received json.
            // this way, attributes of the json can be accessed via this.state.serverReturned.[field]
            this.setState({
                serverReturned: JSON.parse(ret)
            });
            console.log("search results are: ", this.state.serverReturned);
            this.makeResultsList();

        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
    }

    makeResultsList(){
        let serverLocations = this.state.serverReturned.items;
        let trip = [];
        let counter = 0;
        for (let i in serverLocations){
            trip.push(serverLocations[i]["map"]["code"]);
            this.state.locations.push({name:serverLocations[i]["map"]["name"],
                code:serverLocations[i]["map"]["code"], index:counter});
            counter++;
        }
        this.setState({results: "- Found " + (serverLocations.length)})
        this.forceUpdate();
        this.state.returnList(this.state.locations)
    }

    render() {

        return (
            <div>
                <input className="searchButton" type="text" placeholder="Enter a search like denver"
                    onKeyUp={this.keyUp.bind(this)} autoFocus/>
            </div>
        )
    }


    // define additional methods here
}

export default SearchButton;