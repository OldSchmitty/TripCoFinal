import React from 'react';
import Home from './Home/Home.jsx';
import Dropzone from 'react-dropzone';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import ButtonToolbar from "react-bootstrap/es/ButtonToolbar";
import SearchButton from './SearchButton/SearchButton.jsx';
import PlanTable from './PlanTable/PlanTable.jsx';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            options: {},
            serverReturned: null,
            svg: null,
            bottomRow: [],
            locations: [],
            currentTrip: [],
            units: "Miles",
            results: ""
        }

        this.returnList = (list) => {
            this.setState({locations : list});
        }

        this.updateCurrentTrip = (trip) => {
            this.setState({currentTrip: trip});
        }

        this.handleInsertButtonClick = (onClick) => {
            let locs=this.getQueryTableData();
            let keys=this.getQueryTableKeys();
            let dup;

            for (let i in keys){

                dup = false;
                for (let j in locs){
                    if(locs[j]['code'] === keys[i]){
                        for(let k in this.state.currentTrip){
                            if(this.state.currentTrip[k]['code'] === keys[i])
                                dup = true;
                        }

                        if(!dup) this.state.currentTrip.push({'name': locs[j]['name'], 'code': locs[j]['code']});

                    }
                }
            }
            this.forceUpdate();

            console.log(this.state.currentTrip);
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

        this.resetPage = (onClick) => {
            this.setState({currentTrip: [], units: "Miles", opt: "None", svg: null, addInfo: [], bottomRow: [],
                pairs: [], options: [], allPairs: [], results: "", locations: []});
        }
        this.getData = this.getData.bind(this);
    };



    render() {

        return (
            <div className="app-container">
                <h1>T15 - Wolf Pack</h1>
                <h3>TripCo Itinerary</h3>
                <SearchButton units = {"Miles"} opt = {"None"} returnList = {this.returnList}/>
                <br/>

                {/* Display the array of HTML list items created on line 18 */}

                <div style={{width:"40%"}}>
                    <div className = "search-button">
                        <BootstrapTable data={this.state.locations}
                                        selectRow={{mode:'checkbox',bgColor: 'rgb(255, 255, 0)', selected: []}}
                                        height = "200px"
                                        striped={true}

                                        options={{insertBtn:this.createCustomInsertButton}}
                                        ref='queryTable'
                                        insertRow>
                            <TableHeaderColumn headerAlign= 'center' dataField='name'>
                                Search Results {this.state.results}</TableHeaderColumn>
                            <TableHeaderColumn headerAlign = 'right' hidden= {true} dataField='code' isKey>
                                Codes</TableHeaderColumn>
                        </BootstrapTable>
                    </div>
                    <PlanTable currentTrip = {this.state.currentTrip}
                               updateTrip = {this.updateCurrentTrip}
                               getData = {this.getData}
                    />

                </div>
                <ButtonToolbar className= "Save-Load">
                    <Dropzone className="dropzone-style" onDrop={this.uploadButtonClicked.bind(this)}
                    style={{"width": "15%"}}>
                        <button type="button" onClick={this.uploadButtonClicked.bind(this)}>
                            Upload Planned Trip</button>
                    </Dropzone>

                    <button type="button" onClick={this.saveButtonClicked.bind(this)}>
                        Save Trip</button>
                    <button type="button" onClick={this.resetPage.bind(this)}>Reset</button>
                </ButtonToolbar>

                <Home
                    allPairs = {this.state.allPairs}
                    bottomRow = {this.state.bottomRow}
                    options = {this.state.options}
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
        if (this.state.currentTrip[j]['code'] == input[i]['map']['code']) {
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
    let locs = this.getTripTableData().map((location) => {
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

    console.log("Loaded: " + this.state.sysFile.destinations);
    this.fetch2("upload", this.state.sysFile.destinations);
  }


    getQueryTableData() {
        return(this.refs.queryTable.state.data);
    }
    getQueryTableKeys(){
        return(this.refs.queryTable.state.selectedRowKeys);
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
            bottomRow: totalRow
        });
        console.log("allpairs is: ",this.state.allPairs);

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
        queries: [input],
        doWhat: "query",
        units: this.state.units,
        opt: this.state.opt,
      };

      // if the button is clicked:
    } else if (type === "upload") {
      // Send entire destinations array
      clientRequest = {
        queries: input,
        doWhat: "upload",
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