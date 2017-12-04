import React, {Component} from 'react';

class PlanTripButton extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            serverReturned: this.props.serverReturned,
            units: this.props.units,
            opt: this.props.opt
        };
    }

    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.serverReturned !== this.props.serverReturned) {
            this.setState({serverReturned: nextProps.serverReturned});
        }
        if (nextProps.units !== this.props.units){
            this.setState({units: nextProps.units});
        }
        if (nextProps.opt !== this.props.opt){
            this.setState({opt: nextProps.opt});
        }

    }

    render() {
        return (
            <div className = 'button'>
                <button onClick={() => this.makeTrip()}>
                    Plan My Trip!
                </button>
            </div>
        )
    }

    makeTrip() {
        if (this.props.getTripTableData().length > 1) {
            this.getItinerary();
        }
    };

    async getItinerary() {
        let trip = [];
        let queries = this.props.getTripTableData();

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
        await this.props.query(newMap);
        await this.forceUpdate();
        this.props.getData(this.state.serverReturned.itinerary, this.state.serverReturned.items);
    }



}

export default PlanTripButton;