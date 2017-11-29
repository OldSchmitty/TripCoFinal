import React, {Component} from 'react';

class SearchButton extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            locations: [],
            returnList : this.props.returnList,
            serverReturned : this.props.serverReturned
        };
    }
    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.serverReturned !== this.props.serverReturned) {
            this.setState({serverReturned: nextProps.serverReturned});
        }
    }

    keyUp(event) {
        if (event.which === 13) { // Waiting for enter to be pressed. Enter is key 13:
            //https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
            this.fetch(event.target.value); // Call fetch and pass whatever text is in the input box
        }
    }

    // This function sends `input` the server and updates the state with whatever is returned
    async fetch(input) {

        this.setState({locations: []})

        let newMap = {
            queries : [input],
            doWhat: "query",
            units: null,
            opt: null
        };

        await this.props.query(newMap);
        await this.forceUpdate();
        this.makeResultsList();
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
            <div className = "searchButton">
                <input className="searchButton" type="text" placeholder="Enter a search like denver"
                    onKeyUp={this.keyUp.bind(this)} autoFocus/>
            </div>
        )
    }


    // define additional methods here
}

export default SearchButton;