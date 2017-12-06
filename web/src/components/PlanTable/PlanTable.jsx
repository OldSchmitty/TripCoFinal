import React, {Component} from 'react';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';
import SaveLoad from './SaveLoad/SaveLoad.jsx';
import PlanTripButton from "./PlanTripButton/PlanTripButton.jsx";
class PlanTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            units: "Miles",
            serverReturned: this.props.serverReturned,
            svg: null,
            currentTrip: this.props.currentTrip,
            opt: "None"
        };

        this.createUnitsButton = this.createUnitsButton.bind(this);
        this.buttons = this.buttons.bind(this);
        this.changeUnits = this.changeUnits.bind(this);
        this.handleDeleteButtonClick = this.handleDeleteButtonClick.bind(this);
        this.createCustomDeleteButton = this.createCustomDeleteButton.bind(this);
        this.upButton = this.upButton.bind(this);
        this.downButton = this.downButton.bind(this);
        this.createSelectButton = this.createSelectButton.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.getTripTableData = this.getTripTableData.bind(this);
        this.fillTripTable= this.fillTripTable.bind(this);
        this.resetPage = this.resetPage.bind(this);
        this.clearTrip = this.clearTrip.bind(this);
        this.swap = this.swap.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.currentTrip !== this.state.currentTrip) {
            this.setState({currentTrip: nextProps.currentTrip});
            console.log("on table ", this.state.currentTrip)
        }
        if (nextProps.serverReturned !== this.props.serverReturned) {
            this.setState({serverReturned: nextProps.serverReturned});
        }
        if (nextProps.serverReturned !== this.props.serverReturned) {
            this.setState({serverReturned: nextProps.serverReturned});
        }
    }

    render() {
        return (
                <div className = "table">
                    <BootstrapTable data={this.props.currentTrip}
                                    selectRow={{mode:'checkbox',bgColor: 'rgb(255, 255, 0)', selected: []}}
                                    height = "200px" striped={true} ref='tripTable' options={{btnGroup:this.buttons}}
                                    insertRow deleteRow>
                        <TableHeaderColumn width = '150' headerAlign= 'center' dataField='name' isKey>
                            Current Trip - {this.state.currentTrip.length} in Trip</TableHeaderColumn>
                        <TableHeaderColumn headerAlign= 'center' width = '75'
                                           dataFormat = {this.upButton.bind(this)}>Move Up
                        </TableHeaderColumn>
                        <TableHeaderColumn headerAlign= 'center' width = '75'
                                           dataFormat = {this.downButton.bind(this)}>Move Down
                        </TableHeaderColumn>
                    </BootstrapTable>
                    <PlanTripButton units = {this.state.units} opt = {this.state.opt} query = {this.props.query}
                            serverReturned = {this.state.serverReturned} getData = {this.props.getData}
                            getTripTableData = {this.getTripTableData} />
                    <SaveLoad resetPage = {this.resetPage} units = {this.state.units}
                              getTripTableData = {this.getTripTableData} opt = {this.state.opt}
                              fillTripTable={this.fillTripTable}    clearTrip={this.clearTrip}
                                query = {this.props.query} serverReturned = {this.state.serverReturned}/>
                    <h1> <span dangerouslySetInnerHTML={{__html: this.state.svg}} /> </h1>
                </div>)
    }

    getTripTableData(){
        return(this.refs.tripTable.state.data);
    }

    createSelectButton(onClick) {
        return(
            <select
                style={{height: 35}}
                onChange = {this.handleChange}
                value = {this.state.opt}>
                <option value="None">None</option>
                <option value="Nearest Neighbor">Nearest Neighbor</option>
                <option value="2-Opt">2-Opt</option>
                <option value="3-Opt">3-Opt</option>
            </select>
        );
    }


    createUnitsButton(onClick) {
        return (
            <button type='button'
                    className={'btn btn-primary'}
                    onClick={() => this.changeUnits(onClick)}
                    style={{height: 35, width: 92.14}}>
                {this.state.units}
            </button>
        );
    };

    buttons(props) {
        return (
            <ButtonGroup className='my-custom-class' sizeClass='btn-group-md' style={{width: "150%"}}>
                {this.createCustomDeleteButton()}
                {this.createUnitsButton()}
                {this.createSelectButton()}
            </ButtonGroup>
        )
    }

    changeUnits(onClick) {
        if (this.state.units.valueOf() === 'Kilometers') {
            this.setState({units: 'Miles'});
            console.log("Changing Units to Miles");
        }
        if (this.state.units.valueOf() === "Miles") {
            this.setState({units: 'Kilometers'});
            console.log("Changing Units to Kilometers");
        }
    }

    handleDeleteButtonClick(onClick) {
        let keys = this.refs.tripTable.state.selectedRowKeys;
        let trip = this.state.currentTrip;

        trip = this.deleteDups(keys, trip);

        let newTrip = [];
        for (let i in trip) {
            if (i) newTrip.push(trip[i]);
        }

        this.setState({currentTrip: newTrip});
        this.props.updateTrip(this.state.currentTrip);
    }

    deleteDups(keys, trip) {
        for (let i in keys) {
          this.checkIfToBeDeleted(trip, keys, i);
        }
        return trip;
    }

  // Looks through the plan table for an entry to delete
  checkIfToBeDeleted(trip, keys, i) {
    for (let j in trip) {
      if (trip[j]['name'] === keys[i]) {
        trip.splice(j, 1);
        break;
      }
    }
  }

  createCustomDeleteButton(onClick) {
    return (
            <DeleteButton
                btnText='Delete Selected'
                btnContextual='btn-danger'
                className='my-custom-class'
                btnGlyphicon='glyphicon-edit'
                onClick={e => this.handleDeleteButtonClick(onClick)}/>
        );
    }

    swap(index1, index2){
        let swap = this.state.currentTrip[index1];
        this.state.currentTrip[index1] = this.state.currentTrip[index2];
        this.state.currentTrip[index2] = swap;
        this.props.updateTrip(this.state.currentTrip);
    }

    upButton(cell, row, enumObject, rowIndex) {
        return <button
            type="button"
            onClick={() =>{
                if(rowIndex > 0){
                    this.swap(rowIndex, rowIndex-1);
                }
            }}>
            Up
        </button>;
    }

    downButton(cell, row, enumObject, rowIndex) {
        return <button
            type="button"
            onClick={() =>{
                if(rowIndex < this.state.currentTrip.length-1){
                  this.swap(rowIndex, rowIndex+1);
                }
            }}>
            Down
        </button>;
    }


    handleChange(e) {
        console.log("Changing Opt to",e.target.value);
        this.setState({opt:e.target.value});
        this.props.updateOpt(e.target.value);
    }

    /**
     * Fills the trip plan with the selected destinations
     * @param input The server return info
     */
    fillTripTable(input){
        for (let i in input) {
            let dup = false;
            dup = this.checkForDups(input, i, dup);
            if (!dup) {
                this.state.currentTrip.push({
                    name: input[i]['map']['name'],
                    code: input[i]['map']['code']
                });
            }
        }
        this.forceUpdate();
        this.props.updateTrip(this.state.currentTrip);
    }

    checkForDups(input, i, dup) {
        for (let j in this.state.currentTrip) {
            if (this.state.currentTrip[j]['code'] == input[i]['map']['code']) {
                dup = true;
                break;
            }
        }
        return dup;
    }

    resetPage(){
        this.setState({
            units: "Miles",
            opt: "None",
            svg: null,
            currentTrip: []
        });
        console.log("Plantable: ", this.state);
        this.props.resetPage()
        this.forceUpdate();
    }

    clearTrip(){
        this.state.currentTrip = [];
        this.forceUpdate();
        this.props.updateTrip(this.state.currentTrip);
    }

    setSVG(){
        this.setState({svg: this.state.serverReturned.svg});
    }

}
export default PlanTable;