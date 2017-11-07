import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';
import Dropzone from 'react-dropzone';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import ButtonToolbar from "react-bootstrap/es/ButtonToolbar";

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
            bottomRow: [],
            locations: [],
            currentTrip: [],
            units: "Miles",
            opt: "None",
            results: ""
        }
        this.handleInsertButtonClick = (onClick) => {
            let locs=this.getQueryTableData();
            let keys=this.getQueryTableKeys();
            for (let i in keys){
                let dup = false;
                for(let j in this.state.currentTrip){
                    if (this.state.currentTrip[j]['name']== locs[i]['name']){
                        dup = true;
                        break;
                    }
                }
                if(!dup) {
                    this.state.currentTrip.push({name: locs[i]['name'], code: locs[i]['code']});
                }
            }
            this.forceUpdate();
        };
        this.createCustomInsertButton = (onClick) => {
            return (
                <InsertButton
                    btnText='Add Selected to Trip'
                    btnContextual='btn-success'
                    className='my-custom-class'
                    btnGlyphicon='glyphicon-edit'
                    onClick={ () => this.handleInsertButtonClick(onClick) }
                />
            );
        };
        this.createTripButton = (onClick) => {
            return (
                <InsertButton
                    btnText='Create your Itinerary'
                    btnContextual='btn-success'
                    className='my-custom-class'
                    btnGlyphicon='glyphicon-edit'
                    onClick={ () => this.makeTrip(onClick) }
                />
            );
        };
        this.makeTrip = (onClick) => {
            this.getItinerary()
        };
        this.createUnitsButton = (onClick) => {
            return (
                    <button type='button'
                            className={'btn btn-primary'}
                            onClick={ () => this.changeUnits(onClick) }>
                        {this.state.units}
                    </button>
            );
        };
        this.buttons = props => {
            return(
                <ButtonGroup className='my-custom-class' sizeClass='btn-group-md'>
                    {this.createTripButton()}
                    {this.createUnitsButton()}
                    {this.createSelectButton()}
                    </ButtonGroup>
            )
        }
        this.changeUnits = (onClick) => {
            if(this.state.units.valueOf() === 'Kilometers') {
                this.setState({units: 'Miles'});
                console.log("Changing Units to Miles");
            }
            if(this.state.units.valueOf() === "Miles") {
                this.setState({units: 'Kilometers'});
                console.log("Changing Units to Kilometers");
            }
        }
        this.createSelectButton = (onClick) => {
            return(
                <select
                    onChange = {this.handleChange}
                >
                    <option value="None">None</option>
                    <option value="Nearest Neighbor">Nearest Neighbor</option>
                    <option value="2-Opt">2-Opt</option>
                    <option value="3-Opt">3-Opt</option>
                </select>
            );
        }

        this.handleChange = (e) => {
            console.log("Changing Opt to",e.target.value);
            this.setState({opt:e.target.value});
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
                <label className ="checky">
                    <input
                        type="checkbox"
                        value={i}
                        onChange={() => {
                            this.state.options[i] = !this.state.options[i];
                            this.forceUpdate()
                        }}
                    />
                    {i}
                </label>
            )
        }
        let svg;
        if (this.state.serverReturned) { // if this.state.serverReturned is not null
            //Get list of numbers

            /*Create an array of HTML list items. The Array.map function in Javascript passes each individual element
            * of an array (in this case serverLocations is the array and "location" is the name chosen for the
            individual element) through a function and returns a new array with the mapped elements.
            * In this case f: location -> <li>location.name</li>, so the array will look like:
            * [<li>[name1]</li>,<li>[name2]</li>...]
            */
            // set the local variable scg to this.state.serverReturned.svg
        }
        if(this.state.svg){
            svg = this.state.serverReturned.svg;
        }

        return (
            <div className="app-container">
                <h1>T15 - Wolf Pack</h1>
                <h3>TripCo Itinerary</h3>
                <input className="search-button" type="text" placeholder="Enter a search like denver"
                       onKeyUp={this.keyUp.bind(this)} autoFocus/>
                <br/>
                {/* Display the array of HTML list items created on line 18 */}

                <div>
                    <div className = "search-button" style={{width:"33%"}}>
                        <BootstrapTable data={this.state.locations}
                                        selectRow={{mode:'checkbox',bgColor: 'rgb(255, 255, 0)'}}
                                        height = "200"
                                        striped={true}

                                        options={{insertBtn:this.createCustomInsertButton}}
                                        ref='queryTable'
                                        insertRow>
                            <TableHeaderColumn headerAlign= 'center' dataField='name'>Search Results {this.state.results}</TableHeaderColumn>
                            <TableHeaderColumn dataField = 'index'  hidden = {true} isKey={true}>index</TableHeaderColumn>
                        </BootstrapTable>
                    </div>

                    <div className = "search-button" style={{width:"33%"}}>
                        <BootstrapTable data={this.state.currentTrip}
                                        selectRow={{mode:'checkbox',bgColor: 'rgb(255, 255, 0)'}}
                                        height = "200"
                                        striped={true}
                                        ref='tripTable'
                                        deleteRow

                                        options={{btnGroup:this.buttons}}
                                        insertRow>
                            <TableHeaderColumn headerAlign= 'center' dataField='name' isKey>Current Trip</TableHeaderColumn>
                        </BootstrapTable>
                    </div>
                </div>
                <ButtonToolbar className= "Save-Load">
                    <Dropzone className="dropzone-style" onDrop={this.uploadButtonClicked.bind(this)}>
                        <button type="button" >Upload Planned Trip</button>
                    </Dropzone>
                    <button type="button" onClick={this.saveButtonClicked.bind(this)}>Save Trip</button>
                </ButtonToolbar>
                <h1>
                    {/* In the constructor, this.state.serverReturned.svg is not assigned a value. This means the image
                    will only display once the serverReturned state variable is set to the received json in line 73*/}
                    <span dangerouslySetInnerHTML={{__html: svg}} />
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

  /**
   * Fills the trip plan with the selected destinations
   * @param input The server return info
   * TODO find way to send only the array of destination
   *
   */
  fillTripTable(input){
    for (let i in input) {
      let dup = false;
      for (let j in this.state.currentTrip) {
        if (this.state.currentTrip[j]['name'] == input[i]['map']['name']) {
          dup = true;
          break;
        }
      }
      if (!dup) {
        this.state.currentTrip.push({
          name: input[i]['map']['name'],
          code: input[i]['map']['code']
        });
      }
    }
    this.forceUpdate();
  }
  saveButtonClicked(event) {
    this.getFile();
  }

  // File reading is almost identical how you did it in Sprint 1
  uploadButtonClicked(acceptedFiles) {
    console.log("Accepting drop");
    acceptedFiles.forEach(file => {
      console.log("Filename:", file.name, "File:", file);
      console.log(JSON.stringify(file));
      let fr = new FileReader();
      fr.onload = (function () {
        return function (e) {
          let JsonObj = JSON.parse(e.target.result);
          console.log(JsonObj);

          // Do something with the file:
          this.browseFile(JsonObj);
        };
      })(file).bind(this);

      fr.readAsText(file);
    });
  }

  async getFile() {
    // assign all the airport codes of the displayed locations to an array
    let locs = this.getQueryTableData().map((location) => {
      return location.code;
    });

    // create an object in the format of the download file:
    let locationFile = {
      title: "selection",
      destinations: locs
    };

    // stringify the object
    let asJSONString = JSON.stringify(locationFile);

    // Javascript code to create an <a> element with a link to the file
    let pom = document.createElement('a');
    pom.setAttribute('href', 'data:text/plain;charset=utf-8,'
        + encodeURIComponent(asJSONString));
    // Download the file instead of opening it:
    pom.setAttribute('download', "download.json");

    // Javascript to click the hidden link we created, causing the file to download
    if (document.createEvent) {
      let event = document.createEvent('MouseEvents');
      event.initEvent('click', true, true);
      pom.dispatchEvent(event);
    } else {
      pom.click();
    }

    // remove hidden link from page
    pom.parentNode.removeChild(pom);
  }
  // Set the uploaded JSON file to a state variable and send it to fetch method
  async browseFile(file) {
    console.log("Got file:", file);
    this.setState({
      sysFile: file
    })
    this.fetch2("upload", this.state.sysFile.destinations);
  }



    getQueryTableData() {
        return(this.refs.queryTable.state.data);
    }
    getQueryTableKeys(){
        return(this.refs.queryTable.state.selectedRowKeys);
    }
    getTripTableData(){
        return(this.refs.tripTable.state.data);
    }

    getData(idFile, infoFile) {
        let pairs = [];
        let totalDist = 0;
        console.log("idFile: ", idFile);
        console.log("infoFile: ", infoFile);
        for (let i  in idFile) {
            let start;
            let end;
            let startArrayFound = false;
            let endArrayFound = false;
            let startIndex = idFile[i]['sourceID'];
            let endIndex = idFile[i]['destinationID'];
            let dist = idFile[i]['distance'];
            // loop through the second JSON file to find the information associated with idFile[i]:

            start = infoFile[startIndex];
            end = infoFile[endIndex];


            totalDist += dist;

            // add the extra info of the second JSON to p:
            let p = {};
            for (let key in start['map']) {
                p["start " + key] = "Start " + key + ": " + start['map'][key];
            }
            for (let key in end['map']) {
                p["end " + key] = "End " + key + ": " + end['map'][key];
            }
            p["distance"] = "Distance: " + dist;
            p["cumulativeDistance"] = "Cumulative: " + totalDist;

            pairs.push(p); //add object to pairs array
        }
        for(let k  in infoFile[0]['map']){
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
        if (event.which === 13) { // Waiting for enter to be pressed. Enter is key 13:
        //https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
            this.fetch(event.target.value); // Call fetch and pass whatever text is in the input box
        }
    }
    async getItinerary() {
        let trip = [];
        let queries = this.getTripTableData();

        for (let i in queries) {
            trip.push(queries[i]['code']);
        }

        let newMap = {
            queries : trip,
            doWhat: "plan",
            units: this.state.units,
            opt: this.state.opt,
        };
        try{

            let serverUrl = window.location.href.substring(0, window.location.href.length - 6) + ":4567/receive";
            let jsonReturned = await fetch(serverUrl,
                {
                    method: "POST",
                    body: JSON.stringify(newMap)
                });

            // Wait for server to return and convert it to json.
            let ret = await jsonReturned.json();
            // Log the received JSON to the browser console
            // set the serverReturned state variable to the received json.
            // this way, attributes of the json can be accessed via this.state.serverReturned.[field]

            this.setState({
                serverReturned: JSON.parse(ret),
                svg: true,
                currentTrip:[],
                locations: []
            });
            console.log("route is: ", this.state.serverReturned.items);
            this.getData(this.state.serverReturned.itinerary, this.state.serverReturned.items);

        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
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
            let counter = 0;
            for (let i in serverLocations){
                trip.push(serverLocations[i]["map"]["code"]);
                this.state.locations.push({name:serverLocations[i]["map"]["name"],code:serverLocations[i]["map"]["code"], index:counter});
                counter++;
            }
            this.setState({results: "- Found " + (serverLocations.length)})
            this.forceUpdate();
            /*
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
            */
        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
    }

  // This function sends `input` the server and updates the state with whatever is returned
  /**
   * New server request method, One method to control the logic for request
   * TODO add logic for query and plan
   * @param type Type of request
   * @param input what to look for
   * @returns {Promise.<void>}
   */
  async fetch2(type, input) {
    console.log("entered fetch");
    // Create object to send to server

    /*  IMPORTANT: This object must match the structure of whatever
        object the server is reading into (in this case ServerRequest).
        Notice how we give both requests the same format */
    let clientRequest;
    // if "enter" is pressed in the input box
    if (type === "query") {
      /* We now pass input as an element of an array
         because we changed the ServerRequest class to take an ArrayList
      */
      clientRequest = {
        request: "query",
        description: [input],
        units: this.state.units,
        opt: this.state.opt,
      };

      // if the button is clicked:
    } else if (type === "upload") {
      // Send entire destinations array
      clientRequest = {
        doWhat: "upload",
        queries: input,
        units: this.state.units,
        opt: this.state.opt,
      }
    } else {
      console.log("No valid type found")
    }
    try {
      // Attempt to send `clientRequest` via a POST request
      // Notice how the end of the url below matches what the server is listening on (found in java code)
      // By default, Spark uses port 4567
      let serverUrl = window.location.href.substring(0, window.location.href.length - 6) + ":4567/receive";
      console.log("Client Request: ", clientRequest);
      let jsonReturned = await fetch(serverUrl,
          {
            method: "POST",
            body: JSON.stringify(clientRequest)
          });
      // Wait for server to return and convert it to json.
      let ret = await jsonReturned.json();
      let returnedJson = JSON.parse(ret);
      // Log the received JSON to the browser console
      console.log("Got back ", returnedJson);
      if(type === "upload"){
        this.setState({
          serverReturned: returnedJson,
          currentTrip:[],})
        this.fillTripTable(this.state.serverReturned.items);
      }
    } catch (e) {
      console.error("Error talking to server");
      console.error(e);
    }
  }
}