import React, {Component} from 'react';
import ItinOptions from "./ItinOptions/ItinOptions.jsx"
import Pair from './Pair/Pair.jsx';

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            options: this.props.options,
            allPairs: this.props.allPairs,
            ps: []


        };

        this.changeOpts = (i) => {
            this.state.options[i] = !this.state.options[i];
            this.forceUpdate();
        }

    }



    render()
    {
        let pairs = this.state.allPairs;
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

        return <div className="home-container">

            <div className="inner">
                <h3>Check the boxes below for additional info!</h3>
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