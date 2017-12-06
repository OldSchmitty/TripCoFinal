import React, {Component} from 'react';
import ItinOptions from "./ItinOptions/ItinOptions.jsx"
import Pair from './Pair/Pair.jsx';

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            options: this.props.options,
            ps: [],
            startLocation: null
        };

        this.changeOpts = (i) => {
            this.state.options[i] = !this.state.options[i];
            this.forceUpdate();
        }

    }

    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.options !== this.props.options) {
            this.setState({options: nextProps.options});
        }
        this.forceUpdate();
    }


    hide(){
        if((Object.values(this.state.options).length > 0)) {
            return <h3>Check the boxes below for additional info!</h3>
        } else {
            return <h3></h3>
        }
    }

    makeTable(){
        let cumulativeDistance = 0;
        this.state.ps = this.props.allPairs.map((pp) => {
            let finalData={};
            finalData["start name"] = pp["start name"];
            finalData["end name"] = pp["end name"];
            finalData["distance"] = pp["distance"];
            cumulativeDistance += parseInt(pp["distance"].substring(9))
            finalData["cumulativeDistance"] = "Cumulative Distance: " + cumulativeDistance;

            for (let i in this.state.options) {
                if (this.state.options[i]) {
                    finalData["start " + i] = pp["start " + i];
                    finalData["end " + i] = pp["end " + i];
                }
            }

            return <Pair {...finalData}/>;
        });
    }

   reorderItinerary(key, value){
        for (let i = 0; i < this.props.allPairs.length; i++){
            /*since no actual function exists in javascript to search for substrings
              we use indexOf function which returns -1 if item not found
            */
            if(this.props.allPairs[0][key].toLowerCase().indexOf(value.toLowerCase()) == -1){
                let temp = this.props.allPairs[0]
                this.props.allPairs.splice(this.props.allPairs[0], 1);
                this.props.allPairs.push(temp);
            }
        }
    }


    render()
    {
        if(!this.state.startLocation) {
            // forces fort collins municipalities as start locations if no location has been chosen
            this.reorderItinerary("start municipality", "fort collins")
        }
        else
            // if location chosen, forces it to be the start
            this.reorderItinerary("start name", this.state.startLocation)

        this.makeTable();
        return <div className="home-container">

            <div className="inner">
                {this.hide()}
                <ItinOptions options = {this.props.options} changeOpts = {this.changeOpts}/>
            </div>
            <div >
                <table className="pair-table">
                    {this.state.ps}
                    <tbody>
                    {this.props.bottomRow}
                    </tbody>
                </table>
            </div>
        </div>
    }
}
export default Home