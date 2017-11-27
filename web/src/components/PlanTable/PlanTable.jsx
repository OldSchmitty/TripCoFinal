import React, {Component} from 'react';
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

class PlanTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            units: "Miles",
            serverReturned: null,
            svg: null,
            currentTrip: this.props.currentTrip,
            opt: "None"
        };

        this.createTripButton = this.createTripButton.bind(this);
        this.makeTrip = this.makeTrip.bind(this);
        this.createUnitsButton = this.createUnitsButton.bind(this);
        this.buttons = this.buttons.bind(this);
        this.changeUnits = this.changeUnits.bind(this);
        this.handleDeleteButtonClick = this.handleDeleteButtonClick.bind(this);
        this.createCustomDeleteButton = this.createCustomDeleteButton.bind(this);
        this.upButton = this.upButton.bind(this);
        this.downButton = this.downButton.bind(this);
        this.createSelectButton = this.createSelectButton.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }


    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.currentTrip !== this.props.currentTrip) {
            this.setState({currentTrip: nextProps.currentTrip});
            console.log(this.state.currentTrip);
        }
    }


    render() {


        return (
                <div className = "table">
                    <BootstrapTable data={this.props.currentTrip}
                                    selectRow={{mode:'checkbox',bgColor: 'rgb(255, 255, 0)', selected: []}}
                                    height = "200px" striped={true}
                                    ref='tripTable' options={{btnGroup:this.buttons}}
                                    insertRow deleteRow>
                        <TableHeaderColumn width = '150' headerAlign= 'center' dataField='name' isKey>
                            Current Trip - {this.props.currentTrip.length} in Trip</TableHeaderColumn>
                        <TableHeaderColumn headerAlign= 'center' width = '75'
                                           dataFormat = {this.upButton.bind(this)}>
                            Move Up
                        </TableHeaderColumn>
                        <TableHeaderColumn headerAlign= 'center' width = '75'
                                           dataFormat = {this.downButton.bind(this)}>
                            Move Down
                        </TableHeaderColumn>
                    </BootstrapTable>
                    <h1> <span dangerouslySetInnerHTML={{__html: this.state.svg}} /> </h1>
                </div>)
    }

    async getItinerary() {
        let trip = [];
        let queries = this.getTripTableData();

        for (let i in queries) {
            if(i !== "") {
                trip.push(queries[i]['code']);
            }
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

            let ret = await jsonReturned.json();
            this.setState({
                serverReturned: JSON.parse(ret),
            });

            if(this.state.serverReturned.svg){
                this.setState({svg: this.state.serverReturned.svg});
            }

            this.props.getData(this.state.serverReturned.itinerary, this.state.serverReturned.items);

        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
    }

    getTripTableData(){
        return(this.refs.tripTable.state.data);
    }

    createSelectButton(onClick) {
        return(
            <select
                style={{height: 35}}
                onChange = {this.handleChange}
                defaultValue = "Choose an Algorithm"
            >
                <option value="None">None</option>
                <option value="Nearest Neighbor">Nearest Neighbor</option>
                <option value="2-Opt">2-Opt</option>
                <option value="3-Opt">3-Opt</option>
            </select>
        );
    }

    createTripButton(onClick) {
        return (
            <InsertButton
                btnText='Create your Itinerary'
                btnContextual='btn-success'
                className='my-custom-class'
                btnGlyphicon='glyphicon-edit'
                onClick={() => this.makeTrip(onClick)}
            />
        );
    };

    makeTrip(onClick) {
        if (this.getTripTableData().length > 1) {
            this.getItinerary();
        }
    };

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
                {this.createTripButton()}
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

        for (let i in keys) {
            for (let j in trip) {
                if (trip[j]['name'] === keys[i]) {
                    delete trip[j];
                    break;
                }
            }
        }

        let newTrip = [];

        for (let i in trip) {
            if (i) newTrip.push(trip[i]);
        }

        this.setState({currentTrip: newTrip});
        this.props.updateTrip(newTrip);
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

    upButton(cell, row, enumObject, rowIndex) {
        return <button
            type="button"
            onClick={() =>{
                if(rowIndex > 0){

                    let swap = this.state.currentTrip[rowIndex];
                    this.state.currentTrip[rowIndex] = this.state.currentTrip[rowIndex-1];
                    this.state.currentTrip[rowIndex-1] = swap;
                    this.props.updateTrip(this.state.currentTrip);                    }
            }}>
            Up
        </button>;
    }

    downButton(cell, row, enumObject, rowIndex) {
        return <button
            type="button"
            onClick={() =>{
                if(rowIndex < this.state.currentTrip.length-1){

                    let swap = this.state.currentTrip[rowIndex];
                    this.state.currentTrip[rowIndex] = this.state.currentTrip[rowIndex+1];
                    this.state.currentTrip[rowIndex+1] = swap;
                    this.props.updateTrip(this.state.currentTrip);
                }
            }}>
            Down
        </button>;
    }

    handleChange(e) {
        console.log("Changing Opt to",e.target.value);
        this.setState({opt:e.target.value});
    }
}

export default PlanTable;