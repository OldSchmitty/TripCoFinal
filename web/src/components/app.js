import React from 'react';
import Home from './Home/Home.jsx';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import SearchButton from './SearchButton/SearchButton.jsx';
import PlanTable from './PlanTable/PlanTable.jsx';
import Map from "./Map.jsx";

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            options: {},
            serverReturned: null,
            bottomRow: [],
            locations: [],
            currentTrip: [],
            opt: "None"
        }
        this.returnList = (list) => {
            this.setState({locations : list});
        }
        this.updateCurrentTrip = (trip) => {
            this.setState({currentTrip: trip});
        }
        this.resetPage = this.resetPage.bind(this);
        this.handleInsertButtonClick = this.handleInsertButtonClick.bind(this);
        this.createCustomInsertButton = this.createCustomInsertButton.bind(this);
        this.getData = this.getData.bind(this);
        this.query = this.query.bind(this);
        this.updateOpt = this.updateOpt.bind(this);
    };



    render() {
        return (
            <div className="app-container">
                <h1>T15 - Wolf Pack</h1>
                <h3>TripCo Itinerary</h3>
                <SearchButton returnList = {this.returnList} query = {this.query}
                              serverReturned ={this.state.serverReturned}/>
                <div style={{width:"40%"}} >
                    <div className = "table">
                        <BootstrapTable data={this.state.locations}
                                        selectRow={{mode:'checkbox',bgColor: 'rgb(255, 255, 0)', selected: []}}
                                        height = "200px"
                                        striped={true}

                                        options={{insertBtn:this.createCustomInsertButton}}
                                        ref='queryTable'
                                        insertRow>
                            <TableHeaderColumn headerAlign= 'center' dataField='name'>
                                Search Results {this.state.locations.length}</TableHeaderColumn>
                            <TableHeaderColumn headerAlign = 'right' hidden= {true} dataField='code' isKey>
                                Codes</TableHeaderColumn>
                        </BootstrapTable>
                    </div>
                    <PlanTable currentTrip = {this.state.currentTrip}
                               updateTrip = {this.updateCurrentTrip}
                               getData = {this.getData}
                               resetPage = {this.resetPage}
                               serverReturned = {this.state.serverReturned}
                               query = {this.query}
                               updateOpt = {this.updateOpt}
                    />
                </div>
                {/* renders the map using google maps */}
                <Map
                    containerElement={<div style={{ height: `100%` }} />}
                    mapElement={<div style={{ height: `100%` }} />}
                    allPairs = {this.state.allPairs}
                />
                <div className = 'top'>
                <Home
                    allPairs = {this.state.allPairs}
                    bottomRow = {this.state.bottomRow}
                    options = {this.state.options}
                    serverReturned = {this.state.serverReturned}
                    query = {this.query}
                    opt = {this.state.opt}
                />
                </div>

            </div>
        )
    }

    getQueryTableData() {
        return(this.refs.queryTable.state.data);
    }

    getQueryTableKeys(){
        return(this.refs.queryTable.state.selectedRowKeys);
    }

    async query(newMap){
        try{

            let serverUrl = window.location.href.substring(0, window.location.href.length - 6) + ":4567/receive";
            let jsonReturned = await fetch(serverUrl,
                {
                    method: "POST",
                    body: JSON.stringify(newMap)
                });

            let ret = await jsonReturned.json();
            this.setState({
                serverReturned: JSON.parse(ret),
            });
            this.forceUpdate();
        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
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
                p["start " + key] = start['map'][key];
            }
            for (let key in end['map']) {
                p["end " + key] = end['map'][key];
            }
            p["distance"] = dist;
            p["cumulativeDistance"] = totalDist;

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
    }

    resetPage() {
        this.setState({currentTrip: [], bottomRow: [],
            pairs: [], options: [], allPairs: [], locations: [], opt: "None", serverReturned: []
        });
        console.log("app reset", this.state);
    }

    handleInsertButtonClick(onClick) {
        let locs=this.getQueryTableData();
        let keys=this.getQueryTableKeys();
        let dup;

        for (let i in keys){

            dup = false;
            for (let j in locs){
                dup = this.checkForInsertDup(locs, j, keys, i, dup);
            }
        }
        this.forceUpdate();
    };

  // Checks for duplicates before inserting a destination
  checkForInsertDup(locs, j, keys, i, dup) {
    if (locs[j]['code'] === keys[i]) {
      dup = this.checkIfDuplicate(keys, i, dup);

      if (!dup) this.state.currentTrip.push(
          {'name': locs[j]['name'], 'code': locs[j]['code']});

    }
    return dup;
  }

  // Is the current key is already in the table
  checkIfDuplicate(keys, i, dup) {
    for (let k in this.state.currentTrip) {
      if (this.state.currentTrip[k]['code'] === keys[i])
        dup = true;
    }
    return dup;
  }

  createCustomInsertButton(onClick) {
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

  updateOpt(newOpt){
      this.setState({opt: newOpt});
  }
}